# Implementation Plan: CRUD de Empleados

**Branch**: `001-crud-empleados` | **Date**: 2026-03-03 | **Spec**: `specs/001-crud-empleados/spec.md`
**Input**: Feature specification from `/specs/001-crud-empleados/spec.md`

## Summary

Implementar un backend Spring Boot (Java 17) para CRUD de empleados con
persistencia PostgreSQL, autenticación básica en endpoints de negocio, contrato
OpenAPI y paginación fija de 5 registros. `clave` será PK autonumérica generada
por el sistema y se ignorará cualquier `clave` enviada por el cliente en creación.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot, Spring Security (Basic Auth), Spring Data JPA, springdoc-openapi  
**Storage**: PostgreSQL (docker-compose para desarrollo local)  
**Testing**: JUnit 5, Spring Boot Test, pruebas de integración con Testcontainers PostgreSQL, validación de contrato OpenAPI  
**Target Platform**: Servicio backend JVM en contenedor Linux  
**Project Type**: API REST monolítica (single backend)  
**Performance Goals**: p95 lectura ≤ 300 ms; p95 escritura ≤ 500 ms; 30-50 req/s por instancia  
**Constraints**: timeout 2 s por request; payload máximo 1 MB; `size` fijo en 5 para listados; memoria 512 MB (dev) / 1 GB (demo)  
**Scale/Scope**: 5-20 usuarios concurrentes; 1k-50k empleados; instancia única; sin multi-tenant

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Stack uses Spring Boot on Java 17 only.
- [x] HTTP access enforces Basic Auth for CRUD; public routes documented in spec and security config tasks.
- [x] Persistence uses PostgreSQL and includes Docker-based local provisioning.
- [x] New or modified endpoints include OpenAPI/Swagger updates.
- [x] Plan includes executable quality checks (unit/integration tests and run steps).

**Post-Design Re-check**: PASS.

## Project Structure

### Documentation (this feature)

```text
specs/001-crud-empleados/
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

**Structure Decision**: Arquitectura por capas en un único servicio Spring Boot para mantener bajo acoplamiento, despliegue simple y pruebas directas de API y persistencia.

## Complexity Tracking

Sin violaciones constitucionales activas para esta feature.
