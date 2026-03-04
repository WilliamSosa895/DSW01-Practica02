# Tasks: CRUD de Empleados

**Input**: Design documents from `/specs/001-crud-empleados/`  
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Se incluyen tareas de pruebas unitarias e integración como validación ejecutable obligatoria; no se fuerza enfoque TDD.

## Phase 1: Setup (Project Initialization)

**Purpose**: Inicializar estructura y dependencias base del backend.

- [X] T001 Crear configuración de proyecto Maven con dependencias requeridas en pom.xml
- [X] T002 Configurar servicio PostgreSQL para desarrollo local en docker-compose.yml
- [X] T003 [P] Definir variables de entorno de ejemplo para base de datos en .env.example
- [X] T004 [P] Crear clase de arranque Spring Boot en src/main/java/com/dsw01/practica02/Dsw01Practica02Application.java

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura transversal que bloquea todas las historias hasta completarse.

- [X] T005 Implementar entidad JPA Empleado con `clave` autonumérica y longitudes máximas en src/main/java/com/dsw01/practica02/model/Empleado.java
- [X] T006 [P] Implementar repositorio de persistencia de Empleado en src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java
- [X] T007 [P] Implementar configuración Basic Auth y permitir solo `/actuator/health`, `/api-docs` y `/swagger-ui.html` sin autenticación en src/main/java/com/dsw01/practica02/config/SecurityConfig.java
- [X] T008 [P] Implementar configuración OpenAPI con esquema `basicAuth` en src/main/java/com/dsw01/practica02/config/OpenApiConfig.java
- [X] T009 Implementar DTO de error estándar para respuestas de falla en src/main/java/com/dsw01/practica02/dto/ErrorResponse.java
- [X] T010 Implementar manejo global de excepciones y mapeo HTTP en src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java
- [X] T011 Configurar datasource, seguridad y springdoc para entorno local en src/main/resources/application.properties

**Checkpoint**: Base técnica lista para implementar historias en orden de prioridad.

---

## Phase 3: User Story 1 - Registrar empleado (Priority: P1) 🎯 MVP

**Goal**: Crear empleados válidos usando `nombre`, `direccion` y `telefono`, con `clave` asignada por el sistema.

**Independent Test**: `POST /api/empleados` con datos válidos devuelve `201` y una `clave` autonumérica; si el payload incluye `clave`, el valor se ignora.

- [X] T012 [P] [US1] Implementar DTO de entrada de creación con validaciones en src/main/java/com/dsw01/practica02/dto/EmpleadoCreateRequest.java
- [X] T013 [P] [US1] Implementar DTO de salida de empleado creado en src/main/java/com/dsw01/practica02/dto/EmpleadoResponse.java
- [X] T014 [US1] Implementar lógica de alta que ignora `clave` del cliente en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [X] T015 [US1] Implementar endpoint POST `/api/empleados` en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [X] T016 [US1] Ajustar manejo de validaciones de alta (`400`) y formato de error estándar en src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java
- [X] T017 [US1] Alinear contrato OpenAPI de creación con comportamiento final en specs/001-crud-empleados/contracts/employees-openapi.yaml
- [X] T018 [P] [US1] Implementar pruebas unitarias de creación e ignorar `clave` del payload en src/test/java/com/dsw01/practica02/unit/EmpleadoServiceCreateTest.java
- [X] T019 [US1] Implementar prueba de integración de POST `/api/empleados` con Basic Auth en src/test/java/com/dsw01/practica02/integration/EmpleadoCreateIntegrationTest.java

**Checkpoint**: US1 funcional e independientemente demostrable.

---

## Phase 4: User Story 2 - Consultar empleados (Priority: P2)

**Goal**: Consultar por `clave` y listar empleados con paginación fija de 5 registros por página.

**Independent Test**: `GET /api/empleados/{clave}` y `GET /api/empleados?page=0&size=5` retornan datos correctos con metadatos de paginación.

- [X] T020 [P] [US2] Implementar DTO de respuesta paginada con metadatos en src/main/java/com/dsw01/practica02/dto/EmpleadoPageResponse.java
- [X] T021 [P] [US2] Implementar DTO de parámetros de consulta paginada en src/main/java/com/dsw01/practica02/dto/PaginationQuery.java
- [X] T022 [US2] Implementar lógica de consulta por clave con `404` cuando no exista en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [X] T023 [US2] Implementar lógica de listado paginado con validación `page >= 0` y `size = 5` en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [X] T024 [US2] Implementar endpoints GET de consulta por clave y listado en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [X] T025 [US2] Ajustar errores de paginación inválida (`400`) y mantener `200` con contenido vacío cuando `page` esté fuera de rango en src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java
- [X] T026 [US2] Alinear contrato OpenAPI de listado paginado y consulta por clave en specs/001-crud-empleados/contracts/employees-openapi.yaml
- [X] T027 [P] [US2] Implementar pruebas unitarias de paginación, consulta por clave y página fuera de rango en src/test/java/com/dsw01/practica02/unit/EmpleadoServiceReadTest.java
- [X] T028 [US2] Implementar prueba de integración de GET paginado y GET por clave en src/test/java/com/dsw01/practica02/integration/EmpleadoReadIntegrationTest.java

**Checkpoint**: US2 funcional e independientemente demostrable.

---

## Phase 5: User Story 3 - Actualizar y eliminar empleado (Priority: P3)

**Goal**: Actualizar campos editables y eliminar físicamente empleados existentes.

**Independent Test**: `PUT /api/empleados/{clave}` y `DELETE /api/empleados/{clave}` funcionan sobre existentes y devuelven `404` si no existen.

- [X] T029 [P] [US3] Implementar DTO de actualización con validaciones de campos en src/main/java/com/dsw01/practica02/dto/EmpleadoUpdateRequest.java
- [X] T030 [US3] Implementar lógica de actualización bloqueando cambios de `clave` en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [X] T031 [US3] Implementar lógica de hard delete por `clave` en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [X] T032 [US3] Implementar endpoints PUT y DELETE en src/main/java/com/dsw01/practica02/controller/EmpleadoController.java
- [X] T033 [US3] Ajustar manejo de `404` en actualización/eliminación y formato de error estándar en src/main/java/com/dsw01/practica02/exception/GlobalExceptionHandler.java
- [X] T034 [US3] Alinear contrato OpenAPI de actualización y eliminación en specs/001-crud-empleados/contracts/employees-openapi.yaml
- [X] T035 [P] [US3] Implementar pruebas unitarias de actualización y eliminación en src/test/java/com/dsw01/practica02/unit/EmpleadoServiceWriteTest.java
- [X] T036 [US3] Implementar prueba de integración de PUT y DELETE de empleados en src/test/java/com/dsw01/practica02/integration/EmpleadoWriteIntegrationTest.java

**Checkpoint**: US3 funcional e independientemente demostrable.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de consistencia, validación ejecutable y documentación final.

- [X] T037 [P] Actualizar quickstart con rutas públicas y flujo final en specs/001-crud-empleados/quickstart.md
- [X] T038 Verificar coherencia final entre spec y plan en specs/001-crud-empleados/plan.md
- [X] T039 [P] Ajustar decisiones operativas finales en specs/001-crud-empleados/research.md
- [X] T040 Ejecutar validación de contrato OpenAPI contra implementación en src/test/java/com/dsw01/practica02/integration/OpenApiContractIntegrationTest.java
- [X] T041 Ejecutar validación completa con `mvn test` y `mvn -Dtest=*IntegrationTest verify` en pom.xml

---

## Dependencies

### Story Order

- US1 → US2 → US3

### Phase Dependencies

- Phase 1 debe completarse antes de Phase 2.
- Phase 2 bloquea el inicio de historias de usuario.
- Phase 3 entrega MVP independiente.
- Phase 4 y Phase 5 se ejecutan tras fundaciones y preferentemente después de US1 para entrega incremental.
- Phase 6 requiere historias implementadas para consolidación.

---

## Parallel Execution Examples

### US1

- Ejecutar T012 y T013 en paralelo.
- Continuar T014 → T015 → T016 → T017.

### US2

- Ejecutar T020 y T021 en paralelo.
- Continuar T022/T023 → T024 → T025 → T026.
- Ejecutar T027 y luego T028 para validar comportamiento.

### US3

- Ejecutar T029 en paralelo con trabajo documental no bloqueante.
- Continuar T030 → T031 → T032 → T033 → T034.
- Ejecutar T035 y luego T036 para validar comportamiento.

---

## Implementation Strategy

### MVP First

1. Completar Phase 1 y Phase 2.
2. Entregar US1 como primer incremento funcional.
3. Validar alta y seguridad base.

### Incremental Delivery

1. Implementar US2 para lectura y paginación.
2. Implementar US3 para completar CRUD.
3. Cerrar con Phase 6 y validación ejecutable.
