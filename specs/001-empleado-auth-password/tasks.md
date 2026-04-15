# Tasks: Autenticacion Por Empleado

**Input**: Design documents from `/specs/001-empleado-auth-password/`
**Prerequisites**: `plan.md`, `spec.md`, `research.md`, `data-model.md`, `contracts/employees-auth-openapi.yaml`, `quickstart.md`

**Tests**: Se incluyen pruebas unitarias, de integracion y de contrato porque la especificacion exige validacion automatizada explicita para FR-007 y SC-004.

**Organization**: Tareas agrupadas por historia de usuario para implementacion y validacion independiente.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Puede ejecutarse en paralelo (archivos diferentes, sin dependencia directa)
- **[Story]**: Etiqueta de historia (`[US1]`, `[US2]`, `[US3]`) solo en fases de historias
- Todas las tareas incluyen ruta de archivo exacta

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Alinear artefactos de especificacion y cobertura base para rutas versionadas y pruebas.

- [X] T001 Actualizar matriz de cobertura FR/SC y pasos de ejecucion en `specs/001-empleado-auth-password/quickstart.md`
- [X] T002 Alinear metadatos de API versionada y seguridad base en `specs/001-empleado-auth-password/contracts/employees-auth-openapi.yaml`
- [X] T003 [P] Configurar propiedades base para auth por empleado y timeouts de pruebas en `src/main/resources/application.properties`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura comun obligatoria antes de implementar historias.

**CRITICAL**: Ninguna historia puede iniciar antes de completar esta fase.

- [X] T004 Agregar campo obligatorio `contrasena` en entidad y restricciones de persistencia en `src/main/java/com/dsw01/practica02/model/Empleado.java`
- [X] T005 [P] Incorporar busqueda por `nombre` y estado activo para auth en `src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java`
- [X] T006 [P] Crear entidad de auditoria de autenticacion en `src/main/java/com/dsw01/practica02/model/EventoAutenticacion.java`
- [X] T007 [P] Crear repositorio de eventos de autenticacion en `src/main/java/com/dsw01/practica02/repository/EventoAutenticacionRepository.java`
- [X] T008 Definir mapeo centralizado de errores de validacion/autenticacion en `src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java`
- [X] T009 Establecer rutas versionadas `/api/v1/empleados` en controlador principal en `src/main/java/com/dsw01/practica02/controller/EmpleadoController.java`
- [X] T010 Forzar politica de paginacion `size=5` en consultas de listado en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [X] T011 Actualizar configuracion OpenAPI para esquema Basic Auth y rutas v1 en `src/main/java/com/dsw01/practica02/config/OpenApiConfig.java`

**Checkpoint**: Base lista para desarrollar historias de usuario.

---

## Phase 3: User Story 1 - Iniciar Sesion Con Empleado (Priority: P1) 🎯 MVP

**Goal**: Autenticar endpoints protegidos con `nombre + contrasena` de empleado y registrar intentos fallidos.

**Independent Test**: Credenciales validas acceden a rutas protegidas; credenciales invalidas reciben `401` y generan evento `FALLO`.

### Tests for User Story 1

- [X] T012 [P] [US1] Crear pruebas unitarias del flujo de autenticacion por empleado en `src/test/java/com/dsw01/practica02/unit/EmpleadoAuthServiceTest.java`
- [X] T013 [P] [US1] Crear pruebas de integracion de acceso permitido/rechazado en rutas `/api/v1/empleados` en `src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationIntegrationTest.java`
- [X] T014 [P] [US1] Crear prueba de integracion para auditoria de intentos fallidos en `src/test/java/com/dsw01/practica02/integration/EmpleadoAuthFailureAuditIntegrationTest.java`
- [X] T015 [P] [US1] Crear prueba automatizada de rendimiento SC-004 con >=100 solicitudes consecutivas mixtas (validas/invalidas) y asercion de al menos 95 respuestas <2s medidas dentro del test en `src/test/java/com/dsw01/practica02/integration/EmpleadoAuthenticationPerformanceIntegrationTest.java`

### Implementation for User Story 1

- [X] T016 [P] [US1] Implementar servicio de autenticacion de empleado y validacion de activo en `src/main/java/com/dsw01/practica02/service/EmpleadoAuthService.java`
- [X] T017 [US1] Implementar `UserDetailsService` basado en repositorio de empleado en `src/main/java/com/dsw01/practica02/config/EmpleadoUserDetailsService.java`
- [X] T018 [US1] Configurar Spring Security Basic Auth para usar empleados en `src/main/java/com/dsw01/practica02/config/SecurityConfig.java`
- [X] T019 [US1] Integrar registro de eventos `EXITO/FALLO` en servicio de autenticacion en `src/main/java/com/dsw01/practica02/service/EmpleadoAuthService.java`
- [X] T020 [US1] Documentar respuestas `401` y esquema de autenticacion en contrato v1 en `specs/001-empleado-auth-password/contracts/employees-auth-openapi.yaml`

**Checkpoint**: US1 funcional y demostrable de forma independiente.

---

## Phase 4: User Story 2 - Gestionar Password De Empleado (Priority: P2)

**Goal**: Gestionar alta/actualizacion de contrasena cumpliendo la politica explicita de FR-007.

**Independent Test**: Operaciones create/update aceptan solo contrasenas validas y rechazan nula, vacia, espacios, longitudes fuera de 8..64 o sin complejidad requerida con error de validacion.

### Tests for User Story 2

- [X] T021 [P] [US2] Crear pruebas unitarias de politica FR-007 (null/blank/whitespace, longitud 8..64, mayuscula, minuscula, digito, especial) en `src/test/java/com/dsw01/practica02/unit/PasswordPolicyValidatorTest.java`
- [X] T022 [P] [US2] Crear pruebas unitarias de servicio para create/update con enforcement de FR-007 en `src/test/java/com/dsw01/practica02/unit/EmpleadoServicePasswordValidationTest.java`
- [X] T023 [P] [US2] Crear pruebas de integracion de create/update exitoso con contrasena valida en `src/test/java/com/dsw01/practica02/integration/EmpleadoPasswordWriteIntegrationTest.java`
- [X] T024 [P] [US2] Crear pruebas de integracion de rechazo `400` para payloads invalidos FR-007 en `src/test/java/com/dsw01/practica02/integration/EmpleadoPasswordValidationIntegrationTest.java`

### Implementation for User Story 2

- [X] T025 [P] [US2] Implementar validador de politica de contrasena FR-007 en `src/main/java/com/dsw01/practica02/service/validation/PasswordPolicyValidator.java`
- [X] T026 [P] [US2] Aplicar validaciones de contrasena en DTO de alta en `src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java`
- [X] T027 [P] [US2] Aplicar validaciones de contrasena en DTO de actualizacion en `src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java`
- [X] T028 [US2] Integrar validador FR-007 en logica de create/update del servicio en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [X] T029 [US2] Asegurar respuesta de error de validacion funcional en altas/actualizaciones invalidas en `src/main/java/com/dsw01/practica02/controller/EmpleadoController.java`
- [X] T030 [US2] Actualizar contrato OpenAPI con restricciones de contrasena 8..64 y reglas de validacion en `specs/001-empleado-auth-password/contracts/employees-auth-openapi.yaml`

**Checkpoint**: US2 funcional e independiente con FR-007 cubierto de forma explicita.

---

## Phase 5: User Story 3 - Proteger Datos Sensibles De Credenciales (Priority: P3)

**Goal**: Evitar exposicion de `contrasena` en respuestas de lectura y mantener contrato consistente.

**Independent Test**: Endpoints de lectura individual y paginada nunca devuelven `contrasena` y mantienen `size=5` en listados.

### Tests for User Story 3

- [X] T031 [P] [US3] Crear pruebas unitarias de mapeo de respuesta sin campo sensible en `src/test/java/com/dsw01/practica02/unit/EmpleadoResponseMappingTest.java`
- [X] T032 [P] [US3] Crear pruebas de integracion para verificar omision de `contrasena` en GET por id y listado v1 en `src/test/java/com/dsw01/practica02/integration/EmpleadoSensitiveDataReadIntegrationTest.java`
- [X] T033 [P] [US3] Extender prueba de contrato OpenAPI para `writeOnly` y ausencia en respuestas en `src/test/java/com/dsw01/practica02/integration/OpenApiContractIntegrationTest.java`
- [X] T034 [P] [US3] Crear prueba de integracion para enforcement de paginacion fija `size=5` en v1 en `src/test/java/com/dsw01/practica02/integration/EmpleadoPaginationIntegrationTest.java`

### Implementation for User Story 3

- [X] T035 [P] [US3] Remover `contrasena` de DTOs de lectura en `src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java`
- [X] T036 [US3] Ajustar mapeos de lectura/listado para sanitizar credenciales en `src/main/java/com/dsw01/practica02/service/EmpleadoService.java`
- [X] T037 [US3] Verificar que endpoints GET v1 usen solo DTO saneado en `src/main/java/com/dsw01/practica02/controller/EmpleadoController.java`
- [X] T038 [US3] Marcar campos de contrasena como `writeOnly` y no retornables en contrato v1 en `specs/001-empleado-auth-password/contracts/employees-auth-openapi.yaml`

**Checkpoint**: US3 funcional e independiente con proteccion de datos sensibles y paginacion validada.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre integral, regresion y trazabilidad final de requisitos.

- [X] T039 [P] Actualizar `quickstart` con escenarios finales de FR-007, SC-004, rutas `/api/v1` y paginacion de 5 en `specs/001-empleado-auth-password/quickstart.md`
- [X] T040 [P] Agregar ejemplos de requests validos/invalidos de contrasena y auth en `specs/001-empleado-auth-password/contracts/employees-auth-openapi.yaml`
- [X] T041 Ejecutar suite de pruebas (`unit`, `integration`, `contract`, rendimiento SC-004) y registrar resultados en `specs/001-empleado-auth-password/quickstart.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- Setup (Phase 1): inicia de inmediato.
- Foundational (Phase 2): depende de Setup y bloquea todas las historias.
- User Stories (Phases 3-5): dependen de Foundational.
- Polish (Phase 6): depende de historias seleccionadas completadas.

### User Story Dependencies

- US1 (P1): solo depende de Foundational.
- US2 (P2): solo depende de Foundational y puede avanzar en paralelo con US1.
- US3 (P3): depende de Foundational y de que DTO/mapeos de US2 esten estabilizados.

### Story Completion Order

- Orden incremental recomendado: US1 -> US2 -> US3.
- Orden paralelo posible tras Foundational: US1 y US2 en paralelo; US3 inicia cuando finalicen cambios de DTO/mapeo de US2.

---

## Parallel Execution Examples

## Parallel Example: User Story 1

```bash
# Tests US1 en paralelo
T012 EmpleadoAuthServiceTest
T013 EmpleadoAuthenticationIntegrationTest
T014 EmpleadoAuthFailureAuditIntegrationTest
T015 EmpleadoAuthenticationPerformanceIntegrationTest

# Implementacion US1 en paralelo
T016 EmpleadoAuthService
T017 EmpleadoUserDetailsService
```

## Parallel Example: User Story 2

```bash
# Tests US2 en paralelo
T021 PasswordPolicyValidatorTest
T022 EmpleadoServicePasswordValidationTest
T023 EmpleadoPasswordWriteIntegrationTest
T024 EmpleadoPasswordValidationIntegrationTest

# Validaciones DTO en paralelo
T026 EmpleadoCreateRequest
T027 EmpleadoUpdateRequest
```

## Parallel Example: User Story 3

```bash
# Tests US3 en paralelo
T031 EmpleadoResponseMappingTest
T032 EmpleadoSensitiveDataReadIntegrationTest
T033 OpenApiContractIntegrationTest
T034 EmpleadoPaginationIntegrationTest

# Implementacion US3 paralela
T035 EmpleadoResponse
T038 employees-auth-openapi.yaml
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Completar tareas US1 (T012-T020).
3. Validar US1 de forma independiente, incluyendo SC-004.
4. Demo/deploy MVP.

### Incremental Delivery

1. Foundation lista (T001-T011).
2. Entregar US1 (autenticacion por empleado).
3. Entregar US2 (politica de contrasena FR-007).
4. Entregar US3 (no exposicion de credenciales + paginacion 5).
5. Ejecutar polish y regresion completa.

### Parallel Team Strategy

1. Equipo completa Setup + Foundational.
2. Luego de foundation:
   - Developer A: US1 (T012-T020)
   - Developer B: US2 (T021-T030)
   - Developer C: US3 pruebas iniciales (T031-T034) y luego implementacion (T035-T038)
3. Cierre compartido con Phase 6.

---

## Notes

- Las tareas [P] estan delimitadas para minimizar conflictos de archivo.
- Cada historia tiene criterio de prueba independiente y verificable.
- FR-007 queda cubierto por T021-T030.
- SC-004 queda cubierto por T015 y cierre de ejecucion T041.
- Todas las rutas nuevas/modificadas deben mantenerse bajo `/api/v1`.
- El listado de empleados debe conservar `size=5`.
