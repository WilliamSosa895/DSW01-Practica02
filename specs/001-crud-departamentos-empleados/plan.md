# Implementation Plan: CRUD De Departamentos Relacionados A Empleados

**Branch**: `001-crud-departamentos-empleados` | **Date**: 2026-03-06 | **Spec**: `specs/001-crud-departamentos-empleados/spec.md`
**Input**: Feature specification from `/specs/001-crud-departamentos-empleados/spec.md`

## Summary

Implementar CRUD de departamentos con clave autonumerica y nombre unico,
incorporando relacion 1:N entre departamento y empleados (un empleado pertenece
a un solo departamento), exponiendo rutas versionadas `/api/v1`, manteniendo
Basic Auth, documentacion OpenAPI y paginacion fija de 5 en listados.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot, Spring Security (Basic Auth), Spring Data JPA, springdoc-openapi  
**Storage**: PostgreSQL (docker-compose local)  
**Testing**: JUnit 5, Spring Boot Test, pruebas unitarias de validacion/servicio, integracion con MockMvc y Testcontainers  
**Target Platform**: Backend JVM (contenedor Linux para dev/CI)
**Project Type**: Servicio REST monolitico (Spring Boot)  
**Performance Goals**: p95 lecturas CRUD <= 500 ms; p95 escrituras CRUD <= 700 ms en entorno de prueba  
**Constraints**: rutas versionadas `/api/v1`; paginacion fija 5; relacion obligatoria empleado->departamento; no exponer credenciales de empleado en lecturas  
**Scale/Scope**: 5-20 usuarios concurrentes; 1k-50k empleados; 10-500 departamentos

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
specs/001-crud-departamentos-empleados/
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

**Structure Decision**: Mantener arquitectura monolitica por capas existente,
agregando entidad `Departamento` y actualizando `Empleado` para referencia
obligatoria al departamento, sin crear nuevos modulos fuera del arbol actual.

## Complexity Tracking

Sin violaciones constitucionales activas para esta feature.

## Implementation Traceability

- Estado de implementacion: se completaron tareas de Setup, Foundational y US1-US3.
- Cobertura de FR-006: conflicto `409` con estructura `ErrorResponse` implementado y probado.
- Cobertura de FR-007: endpoints publicos explicitamente verificados (`/actuator/health`, `/api-docs`, `/swagger-ui.html`).
- Pendiente operacional: ejecucion completa de regresion de integracion depende de disponibilidad de Docker/Testcontainers.
