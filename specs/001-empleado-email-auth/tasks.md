# Tasks: Email En Empleado Para Autenticacion

**Input**: Design documents from `/specs/001-empleado-email-auth/`
**Prerequisites**: `plan.md` (required), `spec.md` (required), `research.md`, `data-model.md`, `contracts/empleados-email-openapi.yaml`, `quickstart.md`

**Tests**: Se incluyen tareas de pruebas porque la especificacion exige cobertura unitaria e integracion para validaciones de email, conflictos y autenticacion por email.

**Organization**: Tareas agrupadas por historia de usuario para implementacion y validacion independiente.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Preparar artefactos base para evolucion de esquema, contrato y validaciones de email.

- [X] T001 Definir estrategia de transicion para endurecer email obligatorio y unicidad en `specs/001-empleado-email-auth/research.md`
- [X] T002 [P] Agregar contrato OpenAPI de email-auth en `specs/001-empleado-email-auth/contracts/empleados-email-openapi.yaml`
- [X] T003 [P] Definir guia de ejecucion de pruebas para email-auth en `specs/001-empleado-email-auth/quickstart.md`
- [X] T004 [P] Documentar estrategia de backfill legacy en `specs/001-empleado-email-auth/research.md`
- [X] T005 [P] Documentar reglas de datos de email en `specs/001-empleado-email-auth/data-model.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Completar cambios base de dominio, persistencia y seguridad que bloquean todas las historias.

**CRITICAL**: Ninguna historia comienza hasta terminar esta fase.

- [X] T006 Agregar campo `email` obligatorio y restricciones de longitud en `src/main/java/com/dsw01/practica02/model/Empleado.java`
- [X] T007 [P] Agregar restriccion unica de email en entidad y tabla de empleado en `src/main/java/com/dsw01/practica02/model/Empleado.java`
- [X] T008 [P] Extender DTO de alta con `email` y validaciones de formato en `src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java`
- [X] T009 [P] Extender DTO de actualizacion con `email` y validaciones de formato en `src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java`
- [X] T010 [P] Extender DTO de lectura con `email` en `src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java`
- [X] T011 [P] Agregar consultas por email normalizado en `src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java`
- [X] T012 Implementar normalizacion de email (`trim`, lowercase) en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [X] T013 [P] Estandarizar error de validacion de email (`400` + `ErrorResponse`) en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`
- [X] T014 [P] Actualizar carga de usuario por email en `src/main/java/com/dsw01/practica02/config/EmpleadoUserDetailsService.java`
- [X] T015 [P] Verificar reglas Basic Auth para endpoints publicos y privados en `src/main/java/com/dsw01/practica02/config/SecurityConfig.java`
- [X] T016 Implementar estrategia de backfill para empleados legacy sin email en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`

**Checkpoint**: Fundacion lista para implementar historias por prioridad.

---

## Phase 3: User Story 1 - Registrar y actualizar empleado con email valido (Priority: P1) 🎯 MVP

**Goal**: Alta y actualizacion de empleado con email obligatorio y formato valido.

**Independent Test**: Crear y actualizar empleado con email valido, y validar rechazos `400` por email ausente o invalido sin depender de otras historias.

### Tests for User Story 1

- [ ] T017 [P] [US1] Crear prueba de integracion de alta con email valido en `src/test/java/com/dsw01/practica02/integration/EmpleadoEmailCreateIntegrationTest.java`
- [ ] T018 [P] [US1] Crear prueba de integracion de actualizacion con email valido en `src/test/java/com/dsw01/practica02/integration/EmpleadoEmailUpdateIntegrationTest.java`
- [ ] T019 [P] [US1] Crear prueba de integracion para email ausente/invalido (`400`) en `src/test/java/com/dsw01/practica02/integration/EmpleadoEmailValidationIntegrationTest.java`
- [ ] T020 [P] [US1] Crear prueba unitaria de normalizacion y validacion de email en `src/test/java/com/dsw01/practica02/unit/EmpleadoEmailValidationServiceTest.java`

### Implementation for User Story 1

- [ ] T021 [US1] Implementar persistencia de email en alta de empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T022 [US1] Implementar persistencia de email en actualizacion de empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T023 [US1] Ajustar mapping de respuesta para incluir email en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T024 [US1] Actualizar endpoint `POST /api/v1/empleados` para contrato con email en `src/main/java/com/dsw01/practica02/controller/EmpleadoController.java`
- [ ] T025 [US1] Actualizar endpoint `PUT /api/v1/empleados/{clave}` para contrato con email en `src/main/java/com/dsw01/practica02/controller/EmpleadoController.java`
- [ ] T026 [US1] Verificar respuesta de validacion estructurada para email invalido en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`

**Checkpoint**: US1 completa y demostrable como MVP.

---

## Phase 4: User Story 2 - Evitar duplicidad de email en el sistema (Priority: P2)

**Goal**: Garantizar unicidad case-insensitive de email en create/update con conflicto verificable.

**Independent Test**: Intentar crear y actualizar empleados con email duplicado en diferentes combinaciones de mayusculas/minusculas y validar `409`.

### Tests for User Story 2

- [ ] T027 [P] [US2] Crear prueba de integracion de duplicado en alta (`409`) en `src/test/java/com/dsw01/practica02/integration/EmpleadoEmailDuplicateCreateIntegrationTest.java`
- [ ] T028 [P] [US2] Crear prueba de integracion de duplicado en actualizacion (`409`) en `src/test/java/com/dsw01/practica02/integration/EmpleadoEmailDuplicateUpdateIntegrationTest.java`
- [ ] T029 [P] [US2] Crear prueba de integracion de duplicado case-insensitive en `src/test/java/com/dsw01/practica02/integration/EmpleadoEmailCaseInsensitiveConflictIntegrationTest.java`
- [ ] T030 [P] [US2] Crear prueba unitaria de repositorio por email normalizado en `src/test/java/com/dsw01/practica02/unit/EmpleadoRepositoryEmailLookupTest.java`

### Implementation for User Story 2

- [ ] T031 [US2] Implementar regla de unicidad case-insensitive en create de empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T032 [US2] Implementar regla de unicidad case-insensitive en update de empleado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [ ] T033 [US2] Implementar consulta de existencia por email normalizado en `src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java`
- [ ] T034 [US2] Estandarizar conflicto `409` de email duplicado con `ErrorResponse` en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`
- [ ] T035 [US2] Actualizar respuesta OpenAPI para conflictos de email en `src/test/java/com/dsw01/practica02/integration/OpenApiContractIntegrationTest.java`

**Checkpoint**: US1 y US2 funcionales de forma independiente.

---

## Phase 5: User Story 3 - Autenticar empleados con email y contrasena (Priority: P3)

**Goal**: Permitir autenticacion Basic Auth con email + contrasena para empleados activos.

**Independent Test**: Acceder a endpoint protegido con email valido (`200`) y validar rechazos (`401`) con email inexistente, contrasena incorrecta o empleado inactivo.

### Tests for User Story 3

- [ ] T036 [P] [US3] Crear prueba de autenticacion exitosa por email en `src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationByEmailIntegrationTest.java`
- [ ] T037 [P] [US3] Crear prueba de fallo por email inexistente o password incorrecta en `src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationByEmailFailureIntegrationTest.java`
- [ ] T038 [P] [US3] Crear prueba de rechazo para empleado inactivo por email en `src/test/java/com/dsw01/practica02/integration/EmpleadoInactiveEmailAuthenticationIntegrationTest.java`
- [ ] T039 [P] [US3] Crear prueba unitaria de `UserDetailsService` por email en `src/test/java/com/dsw01/practica02/unit/EmpleadoUserDetailsByEmailTest.java`
- [ ] T040 [P] [US3] Crear prueba de compatibilidad transitoria para credencial `admin/admin123` en `src/test/java/com/dsw01/practica02/integration/EmpleadoLegacyAdminCredentialIntegrationTest.java`

### Implementation for User Story 3

- [ ] T041 [US3] Implementar lookup de autenticacion por email de empleado activo en `src/main/java/com/dsw01/practica02/config/EmpleadoUserDetailsService.java`
- [ ] T042 [US3] Implementar compatibilidad transitoria de credencial `admin/admin123` en `src/main/java/com/dsw01/practica02/config/EmpleadoUserDetailsService.java`
- [ ] T043 [US3] Ajustar auditoria de autenticacion para intentos por email en `src/main/java/com/dsw01/practica02/service/EmpleadoAuthService.java`
- [ ] T044 [US3] Mantener seguridad publica solo para health/docs y proteger resto con Basic Auth en `src/main/java/com/dsw01/practica02/config/SecurityConfig.java`

**Checkpoint**: Todas las historias completas y validables de forma independiente.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre integral de contrato, regresion y documentacion operativa.

- [X] T045 [P] Actualizar cobertura de contrato OpenAPI para email en `src/test/java/com/dsw01/practica02/integration/OpenApiContractIntegrationTest.java`
- [X] T046 [P] Agregar prueba de endpoints publicos sin auth y protegidos con auth en `src/test/java/com/dsw01/practica02/integration/PublicEndpointsAccessIntegrationTest.java`
- [X] T047 [P] Agregar prueba de paginacion fija `size=5` para empleados en `src/test/java/com/dsw01/practica02/integration/EmpleadoPaginationIntegrationTest.java`
- [ ] T048 [P] Actualizar escenarios operativos de email-auth en `specs/001-empleado-email-auth/quickstart.md`
- [X] T049 Ejecutar suite de regresion unitaria e integracion en `src/test/java/com/dsw01/practica02/unit/` y `src/test/java/com/dsw01/practica02/integration/`
- [ ] T050 Ajustar trazabilidad final en `specs/001-empleado-email-auth/plan.md` y `specs/001-empleado-email-auth/research.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: sin dependencias.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea historias.
- **Phase 3-5 (User Stories)**: dependen de Phase 2.
- **Phase 6 (Polish)**: depende de historias completadas.

### User Story Dependencies

- **US1 (P1)**: inicia al cerrar Foundational; no depende de otras historias.
- **US2 (P2)**: inicia al cerrar Foundational; depende de reglas base de email en US1.
- **US3 (P3)**: inicia al cerrar Foundational; depende de email persistido y unico de US1/US2.

### Story Completion Order

- US1 -> US2 -> US3 -> Polish.

---

## Parallel Opportunities

- Setup en paralelo: T002, T003, T004, T005 despues de T001.
- Foundational en paralelo: T007, T008, T009, T010, T011, T013, T014, T015 despues de T006.
- US1 en paralelo: T017, T018, T019, T020.
- US2 en paralelo: T027, T028, T029, T030.
- US3 en paralelo: T036, T037, T038, T039.
- Polish en paralelo: T045, T046, T047, T048.

---

## Parallel Example: User Story 1

```bash
# Ejecutar pruebas US1 en paralelo
Task: "T017 [US1] Alta con email valido en src/test/java/com/dsw01/practica02/integration/EmpleadoEmailCreateIntegrationTest.java"
Task: "T018 [US1] Actualizacion con email valido en src/test/java/com/dsw01/practica02/integration/EmpleadoEmailUpdateIntegrationTest.java"
Task: "T019 [US1] Validaciones email 400 en src/test/java/com/dsw01/practica02/integration/EmpleadoEmailValidationIntegrationTest.java"
```

## Parallel Example: User Story 2

```bash
# Ejecutar pruebas US2 en paralelo
Task: "T027 [US2] Duplicado en alta en src/test/java/com/dsw01/practica02/integration/EmpleadoEmailDuplicateCreateIntegrationTest.java"
Task: "T028 [US2] Duplicado en actualizacion en src/test/java/com/dsw01/practica02/integration/EmpleadoEmailDuplicateUpdateIntegrationTest.java"
Task: "T029 [US2] Duplicado case-insensitive en src/test/java/com/dsw01/practica02/integration/EmpleadoEmailCaseInsensitiveConflictIntegrationTest.java"
```

## Parallel Example: User Story 3

```bash
# Ejecutar pruebas US3 en paralelo
Task: "T036 [US3] Autenticacion por email exitosa en src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationByEmailIntegrationTest.java"
Task: "T037 [US3] Fallo por credenciales invalidas en src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationByEmailFailureIntegrationTest.java"
Task: "T038 [US3] Rechazo usuario inactivo en src/test/java/com/dsw01/practica02/integration/EmpleadoInactiveEmailAuthenticationIntegrationTest.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 (T001-T005).
2. Completar Phase 2 (T006-T016).
3. Completar US1 (T017-T026).
4. Validar criterio independiente de US1 y publicar MVP.

### Incremental Delivery

1. Entregar MVP con US1.
2. Agregar US2 y validar unicidad de email.
3. Agregar US3 y validar autenticacion por email.
4. Cerrar con contrato, regresion y trazabilidad en Phase 6.

### Validation Rule

Cada historia debe aprobar su prueba independiente antes de continuar con la siguiente prioridad.
