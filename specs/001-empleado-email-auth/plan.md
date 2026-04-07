# Implementation Plan: Email En Empleado Para Autenticacion

**Branch**: `001-empleado-email-auth` | **Date**: 2026-03-11 | **Spec**: `specs/001-empleado-email-auth/spec.md`
**Input**: Feature specification from `/specs/001-empleado-email-auth/spec.md`

## Summary

Agregar `email` obligatorio, unico y con validacion de formato en `Empleado`,
actualizando flujo de autenticacion para usar email + contrasena en Basic Auth,
sin romper versionado `/api/v1`, politica de seguridad publica existente ni
documentacion OpenAPI.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot, Spring Security (Basic Auth), Spring Data JPA, springdoc-openapi  
**Storage**: PostgreSQL (docker-compose local)  
**Testing**: JUnit 5, Spring Boot Test, MockMvc, Testcontainers (integracion)  
**Target Platform**: Backend JVM (Linux container para dev/CI)  
**Project Type**: Servicio REST monolitico (Spring Boot)  
**Performance Goals**: p95 autenticacion <= 700 ms; p95 CRUD empleado <= 700 ms en entorno de prueba  
**Constraints**: email obligatorio y unico (case-insensitive); autenticacion por email para empleados activos; compatibilidad transitoria con credencial constitucional `admin/admin123`; rutas `/api/v1`; no exposicion de credenciales sensibles en lecturas  
**Scale/Scope**: 5-20 usuarios concurrentes; 1k-50k empleados; impacto principal en modulo de empleados y seguridad

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
specs/001-empleado-email-auth/
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
    ├── integration/
    └── unit/

docker-compose.yml
pom.xml
```

**Structure Decision**: Mantener arquitectura por capas actual; el cambio se
implementa sobre `Empleado` y servicios/repositorios de seguridad existentes,
sin introducir nuevos modulos o servicios externos.

## Complexity Tracking

Sin violaciones constitucionales activas para esta feature.
