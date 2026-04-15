# Implementation Plan: Autenticacion Por Empleado

**Branch**: `001-empleado-auth-password` | **Date**: 2026-03-03 | **Spec**: `specs/001-empleado-auth-password/spec.md`
**Input**: Feature specification from `/specs/001-empleado-auth-password/spec.md`

## Summary

Implementar autenticación para endpoints protegidos usando credenciales de la
entidad empleado (`nombre` + `contraseña`), incorporando el campo `contraseña`
en el modelo de empleado, protegiendo su exposición en respuestas, y migrando la
funcionalidad nueva a rutas versionadas (`/api/v1/...`). Se conserva paginación
de 5 registros en listados de empleados.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot, Spring Security (Basic Auth), Spring Data JPA, springdoc-openapi  
**Storage**: PostgreSQL (docker-compose local)  
**Testing**: JUnit 5, Spring Boot Test, pruebas unitarias de servicio/validación, integración con MockMvc y Testcontainers  
**Target Platform**: Backend JVM (contenedor Linux para ejecución local/CI)  
**Project Type**: Servicio REST monolítico  
**Performance Goals**: p95 autenticación <= 300 ms; p95 CRUD <= 500 ms; >= 30 req/s por instancia  
**Constraints**: rutas versionadas para nueva funcionalidad (`/api/v1`); paginación de 5; no exponer contraseña en DTOs de lectura; timeout 2 s por request  
**Scale/Scope**: 5-20 usuarios concurrentes; 1k-50k empleados; sin recuperación de contraseña ni MFA en esta iteración

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Stack uses Spring Boot on Java 17 only.
- [x] HTTP access enforces Basic Auth (except explicitly documented health/documentation endpoints).
- [x] Persistence uses PostgreSQL and includes Docker-based local provisioning.
- [x] New or modified endpoints include OpenAPI/Swagger updates.
- [x] New or modified API routes include explicit versioning strategy (e.g., `/api/v1`).
- [x] List endpoints define pagination policy (default block size 5 unless approved exception).
- [x] Plan includes executable quality checks (unit/integration tests and run steps).

**Post-Design Re-check**: PASS.

## Project Structure

### Documentation (this feature)

```text
specs/001-empleado-auth-password/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
└── tasks.md
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/com/dsw01/practica02/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/dsw01/practica02/
        ├── unit/
        └── integration/

docker-compose.yml
pom.xml
```

**Structure Decision**: Mantener arquitectura por capas en un único backend Spring Boot, extendiendo modelo, seguridad y DTOs existentes para incorporar autenticación por empleado y rutas versionadas.

## Complexity Tracking

Sin violaciones constitucionales activas para esta feature.
