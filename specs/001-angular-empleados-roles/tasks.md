# Tasks: Frontend Empleados con Roles

**Input**: Design documents from `/specs/001-angular-empleados-roles/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Se incluyen tareas de pruebas ejecutables por historia (backend + frontend) y validaciones medibles para SC-001, SC-003 y SC-004.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Inicializar frontend Angular, configuracion base y estructura de pruebas.

- [X] T001 Inicializar workspace Angular en frontend/package.json y frontend/angular.json
- [X] T002 Crear configuraciones de entorno en frontend/src/environments/environment.ts y frontend/src/environments/environment.development.ts
- [X] T003 [P] Configurar enrutamiento base en frontend/src/app/app.routes.ts
- [X] T004 [P] Crear shell inicial en frontend/src/app/app.component.ts y frontend/src/app/app.component.html
- [X] T005 [P] Crear estilos base responsive en frontend/src/styles.css
- [ ] T006 Configurar test runner frontend en frontend/karma.conf.js y frontend/tsconfig.spec.json

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establecer modelo de rol, seguridad por permisos, contrato UI-API e infraestructura compartida.

**⚠️ CRITICAL**: Ninguna historia inicia antes de completar esta fase.

- [X] T007 Crear enum de rol de negocio en src/main/java/com/dsw01/practica02/model/RolEmpleado.java
- [X] T008 Incorporar atributo rol en src/main/java/com/dsw01/practica02/model/Empleado.java
- [X] T009 Actualizar request DTOs para rol en src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java y src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java
- [X] T010 Actualizar response DTOs para rol en src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java y src/main/java/com/dsw01/practica02/dto/EmpleadoResumenResponse.java
- [X] T011 Actualizar mapeos de rol en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [X] T012 [P] Exponer authorities por rol en src/main/java/com/dsw01/practica02/config/EmpleadoUserDetailsService.java
- [X] T013 Implementar autorizacion por rol para escrituras en src/main/java/com/dsw01/practica02/config/SecurityConfig.java
- [ ] T014 [P] Actualizar documentacion OpenAPI base de roles en src/main/java/com/dsw01/practica02/config/OpenApiConfig.java
- [X] T015 [P] Crear modelo de sesion autenticada en frontend/src/app/core/auth/session.model.ts
- [X] T016 [P] Crear servicio base de autenticacion en frontend/src/app/core/auth/auth.service.ts
- [X] T017 [P] Implementar interceptor Basic Auth en frontend/src/app/core/interceptors/basic-auth.interceptor.ts
- [X] T018 [P] Implementar interceptor de errores 401/403 en frontend/src/app/core/interceptors/http-error.interceptor.ts
- [X] T019 Crear guard de autenticacion en frontend/src/app/core/guards/auth.guard.ts
- [X] T020 Crear guard de rol para escrituras en frontend/src/app/core/guards/role.guard.ts
- [X] T021 Registrar interceptores y guards en frontend/src/app/app.config.ts
- [X] T022 Alinear contrato funcional base en specs/001-angular-empleados-roles/contracts/ui-backend-contract.md

**Checkpoint**: Infraestructura lista para implementar historias en orden de prioridad.

---

## Phase 3: User Story 1 - Inicio de sesion por email (Priority: P1) 🎯 MVP

**Goal**: Habilitar login/logout por email y control de acceso inicial al modulo de empleados.

**Independent Test**: Un usuario valido inicia sesion, uno invalido recibe rechazo y el logout invalida sesion.

### Tests for User Story 1

- [ ] T023 [P] [US1] Crear prueba backend de login valido/invalido en src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationIntegrationTest.java
- [ ] T024 [P] [US1] Crear prueba backend de logout/401 subsecuente en src/test/java/com/dsw01/practica02/integration/PublicEndpointsAccessIntegrationTest.java
- [ ] T025 [P] [US1] Crear prueba frontend de formulario login en frontend/src/app/features/auth/login/login.component.spec.ts
- [ ] T026 [P] [US1] Crear prueba frontend de interceptor 401 con redireccion en frontend/src/app/core/interceptors/http-error.interceptor.spec.ts

### Implementation for User Story 1

- [X] T027 [P] [US1] Crear pantalla login en frontend/src/app/features/auth/login/login.component.ts y frontend/src/app/features/auth/login/login.component.html
- [X] T028 [P] [US1] Implementar validadores de login en frontend/src/app/features/auth/login/login.form.ts
- [X] T029 [US1] Implementar flujo login/logout en frontend/src/app/core/auth/auth.service.ts
- [X] T030 [US1] Configurar rutas publicas/protegidas en frontend/src/app/app.routes.ts
- [X] T031 [US1] Implementar barra de sesion con logout en frontend/src/app/shared/components/topbar/topbar.component.ts
- [X] T032 [US1] Integrar redireccion en 401 en frontend/src/app/core/interceptors/http-error.interceptor.ts
- [X] T033 [US1] Actualizar guia de verificacion US1 en specs/001-angular-empleados-roles/quickstart.md

**Checkpoint**: US1 queda completamente demostrable como MVP.

---

## Phase 4: User Story 2 - Gestion completa para master (Priority: P2)

**Goal**: Permitir CRUD completo de empleados para rol MASTER.

**Independent Test**: Usuario MASTER puede crear/editar/eliminar y ver cambios reflejados en el listado.

### Tests for User Story 2

- [ ] T034 [P] [US2] Crear prueba backend de autorizacion MASTER en escrituras en src/test/java/com/dsw01/practica02/integration/EmpleadoWriteIntegrationTest.java
- [ ] T035 [P] [US2] Crear prueba backend de validaciones de alta/edicion en src/test/java/com/dsw01/practica02/integration/EmpleadoPasswordValidationIntegrationTest.java
- [ ] T036 [P] [US2] Crear prueba frontend de formulario CRUD master en frontend/src/app/features/empleados/components/empleado-form/empleado-form.component.spec.ts
- [ ] T037 [P] [US2] Crear prueba frontend de acciones CRUD en listado master en frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.spec.ts

### Implementation for User Story 2

- [X] T038 [P] [US2] Crear servicio Angular de empleados en frontend/src/app/features/empleados/services/empleados-api.service.ts
- [X] T039 [P] [US2] Crear pagina listado master en frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.ts y frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.html
- [ ] T040 [P] [US2] Crear formulario reutilizable de alta/edicion en frontend/src/app/features/empleados/components/empleado-form/empleado-form.component.ts
- [X] T041 [US2] Implementar creacion/edicion con validaciones en frontend/src/app/features/empleados/pages/empleados-edit/empleados-edit.component.ts
- [X] T042 [US2] Implementar eliminacion con confirmacion y refresco en frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.ts
- [X] T043 [US2] Restringir controles CRUD al rol MASTER en frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.html
- [X] T044 [US2] Ajustar controlador y servicio para escrituras por rol en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java y src/main/java/com/dsw01/practica02/service/EmpleadoService.java

**Checkpoint**: US2 habilita administracion completa para MASTER sin romper US1.

---

## Phase 5: User Story 3 - Consulta para empleados estandar (Priority: P3)

**Goal**: Permitir lectura a STANDARD y bloquear escrituras con mensajes claros.

**Independent Test**: Usuario STANDARD consulta listado/detalle, no puede crear/editar/eliminar y recibe 403 claro.

### Tests for User Story 3

- [ ] T045 [P] [US3] Crear prueba backend de bloqueo de escrituras para STANDARD en src/test/java/com/dsw01/practica02/integration/EmpleadoSecurityIntegrationTest.java
- [ ] T046 [P] [US3] Crear prueba backend de endpoint de contexto de usuario en src/test/java/com/dsw01/practica02/integration/EmpleadoReadIntegrationTest.java
- [ ] T047 [P] [US3] Crear prueba frontend de ocultamiento de controles para STANDARD en frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.spec.ts
- [ ] T048 [P] [US3] Crear prueba frontend de mensaje por 403 en frontend/src/app/core/interceptors/http-error.interceptor.spec.ts

### Implementation for User Story 3

- [X] T049 [US3] Implementar endpoint de contexto autenticado en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [X] T050 [US3] Crear DTO de sesion autenticada en src/main/java/com/dsw01/practica02/dto/SesionUsuarioResponse.java
- [X] T051 [US3] Actualizar contrato para endpoint de contexto en specs/001-angular-empleados-roles/contracts/ui-backend-contract.md
- [X] T052 [US3] Consumir contexto de usuario en frontend/src/app/core/auth/auth.service.ts
- [X] T053 [P] [US3] Crear pagina de detalle solo lectura en frontend/src/app/features/empleados/pages/empleado-detail/empleado-detail.component.ts
- [X] T054 [US3] Configurar rutas de lectura para STANDARD en frontend/src/app/app.routes.ts
- [X] T055 [US3] Mostrar mensaje de permisos insuficientes en frontend/src/app/core/interceptors/http-error.interceptor.ts
- [X] T056 [US3] Actualizar guia de validacion STANDARD en specs/001-angular-empleados-roles/quickstart.md

**Checkpoint**: US3 completa lectura segura para STANDARD y bloqueo verificable de escrituras.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Medir criterios de exito, consolidar calidad ejecutable, cubrir concurrencia y cerrar documentacion.

- [ ] T057 [P] Implementar prueba medible SC-001 (95% login <30s) en src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationPerformanceIntegrationTest.java
- [ ] T058 [P] Implementar prueba medible SC-003 (95% CRUD reflejado <2s) en src/test/java/com/dsw01/practica02/integration/EmpleadoCrudPerformanceIntegrationTest.java
- [ ] T059 Ejecutar suite backend de la feature en src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationPerformanceIntegrationTest.java
- [ ] T060 Ejecutar suite frontend de la feature en frontend/src/app/features/empleados/pages/empleados-list/empleados-list.component.spec.ts
- [ ] T061 Registrar evidencia de cumplimiento SC-001 y SC-003 en specs/001-angular-empleados-roles/quickstart.md
- [ ] T062 Actualizar OpenAPI final con endpoint de contexto y permisos por rol en src/main/java/com/dsw01/practica02/config/OpenApiConfig.java
- [X] T063 Revisar mensajes de error transversales en src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java y frontend/src/app/core/interceptors/http-error.interceptor.ts
- [ ] T064 [P] Implementar prueba backend de conflicto de concurrencia en actualizacion de empleados en src/test/java/com/dsw01/practica02/integration/EmpleadoConcurrencyConflictIntegrationTest.java
- [X] T065 Implementar deteccion de conflicto de concurrencia en backend de actualizacion de empleados en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [ ] T066 [P] Implementar prueba frontend de manejo UX ante conflicto de concurrencia en frontend/src/app/features/empleados/pages/empleados-edit/empleados-edit.component.spec.ts
- [X] T067 Implementar UX de conflicto de concurrencia con recarga y reintento en frontend/src/app/features/empleados/pages/empleados-edit/empleados-edit.component.ts
- [ ] T068 [P] Definir protocolo de medicion de SC-004 en specs/001-angular-empleados-roles/quickstart.md
- [ ] T069 Ejecutar validacion de SC-004 y registrar evidencia medible en specs/001-angular-empleados-roles/quickstart.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: inicia inmediatamente.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea las historias.
- **Phase 3 (US1)**: depende de Phase 2.
- **Phase 4 (US2)**: depende de Phase 2 y del flujo de autenticacion establecido en US1.
- **Phase 5 (US3)**: depende de Phase 2 y del flujo de autenticacion establecido en US1.
- **Phase 6 (Polish)**: depende de US1, US2 y US3 completas.

### User Story Dependencies

- **US1 (P1)**: primera entrega de valor (MVP), independiente tras fundacional.
- **US2 (P2)**: requiere autenticacion operativa y rol de negocio funcional.
- **US3 (P3)**: requiere autenticacion operativa y rol de negocio funcional; puede ejecutarse en paralelo con US2.

### Suggested Story Completion Order

1. US1 (MVP)
2. US2
3. US3

### Parallel Opportunities

- Setup: T003, T004, T005.
- Foundational: T012, T014, T015, T016, T017, T018.
- US1: T023, T024, T025, T026, T027, T028.
- US2: T034, T035, T036, T037, T038, T039, T040.
- US3: T045, T046, T047, T048, T053.
- Polish: T057, T058, T064, T066 y T068.

---

## Parallel Example: User Story 1

```bash
Task T023 [US1]: Prueba backend login valido/invalido en src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationIntegrationTest.java
Task T025 [US1]: Prueba frontend formulario login en frontend/src/app/features/auth/login/login.component.spec.ts
Task T027 [US1]: Pantalla login en frontend/src/app/features/auth/login/login.component.ts
```

## Parallel Example: User Story 2

```bash
Task T034 [US2]: Prueba backend autorizacion MASTER en src/test/java/com/dsw01/practica02/integration/EmpleadoWriteIntegrationTest.java
Task T036 [US2]: Prueba frontend formulario CRUD en frontend/src/app/features/empleados/components/empleado-form/empleado-form.component.spec.ts
Task T038 [US2]: Servicio Angular empleados en frontend/src/app/features/empleados/services/empleados-api.service.ts
```

## Parallel Example: User Story 3

```bash
Task T046 [US3]: Prueba backend endpoint de contexto en src/test/java/com/dsw01/practica02/integration/EmpleadoReadIntegrationTest.java
Task T049 [US3]: Endpoint de contexto autenticado en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
Task T053 [US3]: Detalle solo lectura en frontend/src/app/features/empleados/pages/empleado-detail/empleado-detail.component.ts
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Completar US1 (T023-T033).
3. Ejecutar pruebas US1 backend/frontend y validar quickstart.
4. Demostrar MVP de autenticacion.

### Incremental Delivery

1. Foundation completa (Setup + Foundational).
2. Entrega US1 con pruebas pasando.
3. Entrega US2 con pruebas pasando.
4. Entrega US3 con pruebas pasando.
5. Medicion final SC-001, SC-003 y SC-004 en Phase 6.

### Parallel Team Strategy

1. Equipo completo en Phase 1 y 2.
2. Tras fundacional:
   - Dev A: backend de roles y endpoint de contexto.
   - Dev B: frontend auth + guards.
   - Dev C: frontend CRUD + pruebas UI.
3. Integrar por checkpoints de historia con pruebas ejecutables.
