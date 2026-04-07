# Tasks: CRUD De Departamentos Relacionados A Empleados

**Input**: Design documents from `/specs/001-crud-departamentos-empleados/`
**Prerequisites**: `plan.md` (required), `spec.md` (required), `research.md`, `data-model.md`, `contracts/departamentos-openapi.yaml`, `quickstart.md`

**Tests**: Se incluyen tareas de pruebas porque la especificacion define escenarios de prueba y criterios de exito medibles.

**Organization**: Tareas agrupadas por historia de usuario para implementacion y validacion independiente.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Crear artefactos base y contratos DTO para habilitar la implementacion.

- [X] T001 Crear entidad `Departamento` con clave autonumerica y nombre unico en `src/main/java/com/dsw01/practica02/model/Departamento.java`
- [X] T002 [P] Crear repositorio de departamentos con busqueda por nombre en `src/main/java/com/dsw01/practica02/repository/DepartamentoRepository.java`
- [X] T003 [P] Crear DTOs de entrada de departamento en `src/main/java/com/dsw01/practica02/dto/DepartamentoCreateRequest.java` y `src/main/java/com/dsw01/practica02/dto/DepartamentoUpdateRequest.java`
- [X] T004 [P] Crear DTOs de salida de departamento en `src/main/java/com/dsw01/practica02/dto/DepartamentoResponse.java`, `src/main/java/com/dsw01/practica02/dto/DepartamentoDetailResponse.java` y `src/main/java/com/dsw01/practica02/dto/DepartamentoPageResponse.java`
- [X] T005 [P] Crear DTO de resumen de empleado para detalle de departamento en `src/main/java/com/dsw01/practica02/dto/EmpleadoResumenResponse.java`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Completar infraestructura compartida que bloquea todas las historias.

**CRITICAL**: Ninguna historia comienza hasta terminar esta fase.

- [X] T006 Actualizar relacion obligatoria N:1 de empleado a departamento en `src/main/java/com/dsw01/practica02/model/Empleado.java`
- [X] T007 [P] Agregar consultas por departamento en `src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java`
- [X] T008 [P] Crear excepciones de dominio de departamentos en `src/main/java/com/dsw01/practica02/exception/DepartmentHasEmployeesException.java` y `src/main/java/com/dsw01/practica02/exception/DepartmentNotFoundException.java`
- [X] T009 [P] Extender manejo de errores para conflictos y no-encontrado de departamentos en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`
- [X] T010 [P] Extender requests de empleado con `departamentoClave` en `src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java` y `src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java`
- [X] T011 [P] Extender respuesta de empleado con datos de departamento en `src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java`
- [X] T012 Implementar base de `DepartamentoService` con validacion de nombre unico en `src/main/java/com/dsw01/practica02/service/DepartamentoService.java`
- [X] T013 [P] Crear controlador versionado `DepartamentoController` en `src/main/java/com/dsw01/practica02/controller/DepartamentoController.java`
- [X] T014 [P] Verificar y ajustar reglas de acceso Basic Auth para endpoints nuevos en `src/main/java/com/dsw01/practica02/config/SecurityConfig.java`
- [X] T015 [P] Actualizar contrato OpenAPI de la feature en `specs/001-crud-departamentos-empleados/contracts/departamentos-openapi.yaml`

**Checkpoint**: Fundacion lista para implementar historias por prioridad.

---

## Phase 3: User Story 1 - Gestionar Departamentos (Priority: P1) 🎯 MVP

**Goal**: Entregar CRUD completo de departamentos con nombre unico y paginacion fija de 5.

**Independent Test**: Probar `POST/GET/PUT/DELETE /api/v1/departamentos` con casos validos, duplicado `409`, y listado con `size=5`.

### Tests for User Story 1

- [X] T016 [P] [US1] Crear prueba de integracion CRUD de departamentos en `src/test/java/com/dsw01/practica02/integration/DepartamentoCrudIntegrationTest.java`
- [X] T017 [P] [US1] Crear prueba de integracion de duplicado de nombre en `src/test/java/com/dsw01/practica02/integration/DepartamentoDuplicateNameIntegrationTest.java`
- [X] T018 [P] [US1] Crear prueba de paginacion fija de departamentos en `src/test/java/com/dsw01/practica02/integration/DepartamentoPaginationIntegrationTest.java`
- [X] T019 [P] [US1] Crear prueba unitaria de reglas de negocio de departamentos en `src/test/java/com/dsw01/practica02/unit/DepartamentoServiceTest.java`
- [X] T020 [P] [US1] Crear prueba de seguridad para departamentos (401 sin auth, 200 con auth) en `src/test/java/com/dsw01/practica02/integration/DepartamentoSecurityIntegrationTest.java`
- [X] T021 [P] [US1] Crear prueba de acceso publico sin autenticacion para health/docs (`/actuator/health`, `/api-docs`, `/swagger-ui.html`) en `src/test/java/com/dsw01/practica02/integration/PublicEndpointsAccessIntegrationTest.java`

### Implementation for User Story 1

- [X] T022 [US1] Implementar operaciones `create`, `getByClave`, `list`, `update` y `delete` base en `src/main/java/com/dsw01/practica02/service/DepartamentoService.java`
- [X] T023 [US1] Implementar endpoints `POST`, `GET`, `PUT` y `DELETE` base de `/api/v1/departamentos` en `src/main/java/com/dsw01/practica02/controller/DepartamentoController.java`
- [X] T024 [US1] Implementar validacion estricta de paginacion `size=5` en `src/main/java/com/dsw01/practica02/service/DepartamentoService.java`
- [X] T025 [US1] Estandarizar conflicto por nombre duplicado con estado `409` en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`

**Checkpoint**: US1 completa y demostrable como MVP.

---

## Phase 4: User Story 2 - Relacionar Empleados Con Departamento (Priority: P2)

**Goal**: Asegurar asignacion obligatoria de departamento valido en altas y actualizaciones de empleados.

**Independent Test**: Crear y actualizar empleados con `departamentoClave` valido; rechazar referencias inexistentes con error funcional.

### Tests for User Story 2

- [X] T026 [P] [US2] Crear prueba de alta de empleado con departamento valido en `src/test/java/com/dsw01/practica02/integration/EmpleadoDepartamentoAssignmentIntegrationTest.java`
- [X] T027 [P] [US2] Crear prueba de rechazo por departamento inexistente en `src/test/java/com/dsw01/practica02/integration/EmpleadoDepartamentoNotFoundIntegrationTest.java`
- [X] T028 [P] [US2] Crear prueba unitaria de validacion de `departamentoClave` en `src/test/java/com/dsw01/practica02/unit/EmpleadoServiceDepartamentoValidationTest.java`
- [X] T029 [P] [US2] Crear prueba de paginacion de empleados con `size=5` en `src/test/java/com/dsw01/practica02/integration/EmpleadoPaginationIntegrationTest.java`

### Implementation for User Story 2

- [X] T030 [US2] Resolver `departamentoClave` en `create` y `update` de empleados en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [X] T031 [US2] Mapear datos de departamento en respuestas de empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [X] T032 [US2] Actualizar endpoints de empleados para nuevo contrato de request/response en `src/main/java/com/dsw01/practica02/controller/EmpleadoController.java`
- [X] T033 [US2] Estandarizar error de referencia inexistente de departamento en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`
- [X] T034 [US2] Validar no exposicion de datos sensibles de empleado en respuestas relacionadas en `src/test/java/com/dsw01/practica02/integration/EmpleadoSensitiveDataReadIntegrationTest.java`

**Checkpoint**: US1 y US2 funcionales de forma independiente.

---

## Phase 5: User Story 3 - Consultar Estructura Por Departamento (Priority: P3)

**Goal**: Exponer detalle de departamento con su lista de empleados asociados sin credenciales sensibles.

**Independent Test**: Consultar `GET /api/v1/departamentos/{clave}` con departamento poblado y vacio; validar contenido de lista, seguridad de campos y contrato de error `409` al eliminar con dependencias.

### Tests for User Story 3

- [X] T035 [P] [US3] Crear prueba de detalle de departamento con empleados asociados en `src/test/java/com/dsw01/practica02/integration/DepartamentoDetailIntegrationTest.java`
- [X] T036 [P] [US3] Crear prueba de detalle de departamento sin empleados en `src/test/java/com/dsw01/practica02/integration/DepartamentoDetailWithoutEmployeesIntegrationTest.java`
- [X] T037 [P] [US3] Crear prueba de conflicto `409` con estructura `ErrorResponse` al eliminar departamento con empleados en `src/test/java/com/dsw01/practica02/integration/DepartamentoDeleteConflictIntegrationTest.java`

### Implementation for User Story 3

- [X] T038 [US3] Implementar mapeo de lista de empleados en `DepartamentoDetailResponse` en `src/main/java/com/dsw01/practica02/service/DepartamentoService.java`
- [X] T039 [US3] Implementar endpoint de detalle enriquecido en `src/main/java/com/dsw01/practica02/controller/DepartamentoController.java`
- [X] T040 [US3] Implementar regla de negocio de bloqueo de eliminacion con dependencias en `src/main/java/com/dsw01/practica02/service/DepartamentoService.java`
- [X] T041 [US3] Implementar enriquecimiento estructural del conflicto de eliminacion (`409` + `ErrorResponse`) en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`

**Checkpoint**: Todas las historias completas y validables de forma independiente.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de contrato, rendimiento, regresion y documentacion.

- [X] T042 [P] Actualizar prueba de contrato OpenAPI para nuevos endpoints y esquemas en `src/test/java/com/dsw01/practica02/integration/OpenApiContractIntegrationTest.java`
- [X] T043 [P] Crear prueba de rendimiento para p95 de CRUD de departamentos en `src/test/java/com/dsw01/practica02/integration/DepartamentoCrudPerformanceIntegrationTest.java`
- [X] T044 [P] Actualizar escenarios operativos y validaciones en `specs/001-crud-departamentos-empleados/quickstart.md`
- [X] T045 Ejecutar suite de regresion unitaria e integracion en `src/test/java/com/dsw01/practica02/unit/` y `src/test/java/com/dsw01/practica02/integration/`
- [X] T046 Ajustar trazabilidad final en `specs/001-crud-departamentos-empleados/plan.md` y `specs/001-crud-departamentos-empleados/research.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: sin dependencias.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea historias.
- **Phase 3-5 (User Stories)**: dependen de Phase 2.
- **Phase 6 (Polish)**: depende de historias completadas.

### User Story Dependencies

- **US1 (P1)**: inicia al cerrar Foundational; sin dependencias de otras historias.
- **US2 (P2)**: inicia al cerrar Foundational; consume base de departamentos entregada en US1.
- **US3 (P3)**: inicia al cerrar Foundational; depende funcionalmente de entidades relacionadas de US1 y US2.

### Story Completion Order

- US1 -> US2 -> US3 -> Polish.

---

## Parallel Opportunities

- Setup en paralelo: T002, T003, T004, T005 despues de T001.
- Foundational en paralelo: T007, T008, T009, T010, T011, T013, T014, T015 despues de T006.
- US1 en paralelo: T016, T017, T018, T019, T020, T021.
- US2 en paralelo: T026, T027, T028, T029.
- US3 en paralelo: T035, T036, T037.
- Polish en paralelo: T042, T043, T044.

---

## Parallel Example: User Story 1

```bash
# Ejecutar pruebas US1 en paralelo
Task: "T016 [US1] Prueba CRUD en src/test/java/com/dsw01/practica02/integration/DepartamentoCrudIntegrationTest.java"
Task: "T017 [US1] Prueba duplicado en src/test/java/com/dsw01/practica02/integration/DepartamentoDuplicateNameIntegrationTest.java"
Task: "T018 [US1] Prueba paginacion en src/test/java/com/dsw01/practica02/integration/DepartamentoPaginationIntegrationTest.java"
Task: "T020 [US1] Prueba de seguridad en src/test/java/com/dsw01/practica02/integration/DepartamentoSecurityIntegrationTest.java"
Task: "T021 [US1] Prueba de acceso publico en src/test/java/com/dsw01/practica02/integration/PublicEndpointsAccessIntegrationTest.java"
```

## Parallel Example: User Story 2

```bash
# Ejecutar pruebas US2 en paralelo
Task: "T026 [US2] Asignacion valida en src/test/java/com/dsw01/practica02/integration/EmpleadoDepartamentoAssignmentIntegrationTest.java"
Task: "T027 [US2] Rechazo referencia inexistente en src/test/java/com/dsw01/practica02/integration/EmpleadoDepartamentoNotFoundIntegrationTest.java"
Task: "T029 [US2] Paginacion empleados en src/test/java/com/dsw01/practica02/integration/EmpleadoPaginationIntegrationTest.java"
```

## Parallel Example: User Story 3

```bash
# Ejecutar pruebas US3 en paralelo
Task: "T035 [US3] Detalle con empleados en src/test/java/com/dsw01/practica02/integration/DepartamentoDetailIntegrationTest.java"
Task: "T036 [US3] Detalle sin empleados en src/test/java/com/dsw01/practica02/integration/DepartamentoDetailWithoutEmployeesIntegrationTest.java"
Task: "T037 [US3] Conflicto de eliminacion (409 + ErrorResponse) en src/test/java/com/dsw01/practica02/integration/DepartamentoDeleteConflictIntegrationTest.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 (T001-T005).
2. Completar Phase 2 (T006-T015).
3. Completar US1 (T016-T025).
4. Validar criterio independiente de US1 y publicar MVP.

### Incremental Delivery

1. Entregar MVP con US1.
2. Agregar US2 y validar asignacion obligatoria empleado-departamento.
3. Agregar US3 y validar vista estructural por departamento.
4. Cerrar con contrato, performance y regresion en Phase 6.

### Validation Rule

Cada historia debe aprobar su prueba independiente antes de continuar con la siguiente prioridad.
