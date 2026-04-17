# Implementation Plan: Frontend Empleados con Roles

**Branch**: `001-angular-empleados-roles` | **Date**: 2026-03-12 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/001-angular-empleados-roles/spec.md`

## Summary

Se implementara una interfaz web en Angular para autenticacion por email y gestion de empleados con permisos diferenciados: MASTER puede crear/editar/eliminar, STANDARD solo consulta. El backend Spring Boot existente se ampliara para autorizacion por rol de negocio y contrato de integracion UI-API consistente con Basic Auth, rutas versionadas y paginacion fija de 5.

## Technical Context

**Language/Version**: Java 17 (backend), TypeScript 5+ (frontend Angular)  
**Primary Dependencies**: Spring Boot, Spring Security (Basic Auth), Spring Data JPA, springdoc-openapi, Angular, RxJS  
**Storage**: PostgreSQL (local via Docker Compose)  
**Testing**: JUnit 5 + Spring Boot Test (backend), Angular unit tests + integration/e2e checks para flujos criticos UI  
**Target Platform**: JVM server + navegador moderno (desktop y mobile responsive)  
**Project Type**: Web application full-stack (backend API + frontend Angular)  
**Performance Goals**:
- Login exitoso en <30s para 95% de casos (SC-001).
- Refresco de operaciones CRUD exitosas reflejado en UI en <2s para 95% de casos (SC-003).  
- Al menos 90% de usuarios de negocio completa una tarea de consulta o administracion en el primer intento (SC-004).  
**Constraints**:
- Basic Auth obligatorio en endpoints protegidos.
- Endpoints versionados en `/api/v1/...`.
- Listados con `size=5` obligatorio.
- UI CRUD web debe implementarse con Angular por constitucion.
- Validaciones UI alineadas con backend; backend permanece como fuente de verdad.  
**Scale/Scope**:
- 1 modulo de login.
- 1 modulo de empleados con listado, detalle y formularios CRUD.
- 2 perfiles funcionales: MASTER y STANDARD.

## Constitution Check (Pre-Research)

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- [x] Stack uses Spring Boot on Java 17 only.
- [x] HTTP access enforces Basic Auth (except explicitly documented health/documentation endpoints).
- [x] Persistence uses PostgreSQL and includes Docker-based local provisioning.
- [x] New or modified endpoints include OpenAPI/Swagger updates.
- [x] New or modified API routes include explicit versioning strategy (e.g., `/api/v1`).
- [x] List endpoints define pagination policy (default block size 5 unless approved exception).
- [x] If feature includes web CRUD UI, frontend uses Angular and is covered by executable checks.
- [x] Plan includes executable quality checks (unit/integration tests and run steps).

## Project Structure

### Documentation (this feature)

```text
specs/001-angular-empleados-roles/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── ui-backend-contract.md
└── tasks.md  # Se genera en /speckit.tasks
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/com/dsw01/practica02/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
└── test/java/com/dsw01/practica02/

frontend/               # Nuevo proyecto Angular planificado
├── src/
│   ├── app/
│   │   ├── core/
│   │   │   ├── auth/
│   │   │   ├── guards/
│   │   │   └── interceptors/
│   │   ├── features/
│   │   │   └── empleados/
│   │   └── shared/
│   └── assets/
├── angular.json
└── package.json
```

**Structure Decision**: Se adopta estructura full-stack en un solo repositorio: backend existente en `src/` y nuevo frontend Angular en `frontend/` para cumplir la constitucion y mantener separacion limpia de responsabilidades.

## Phase 0 Output Reference

- Research decisions: [research.md](./research.md)

## Phase 1 Output Reference

- Data model: [data-model.md](./data-model.md)
- Interface contracts: [contracts/ui-backend-contract.md](./contracts/ui-backend-contract.md)
- Runbook: [quickstart.md](./quickstart.md)

## Constitution Check (Post-Design)

- [x] Spring Boot + Java 17 se mantiene como stack backend.
- [x] Basic Auth se mantiene y se define estrategia de manejo 401/403 en UI.
- [x] PostgreSQL + Docker se preserva sin cambios.
- [x] Contrato documenta necesidad de sincronizar OpenAPI/Swagger al ajustar endpoints por rol.
- [x] Contrato mantiene rutas versionadas `/api/v1`.
- [x] Contrato y quickstart fijan paginacion de 5 para listados.
- [x] Frontend CRUD definido en Angular con guardias, interceptores y pruebas.
- [x] Se incluyen pasos ejecutables de validacion en quickstart.

## Complexity Tracking

No constitutional violations identified.
