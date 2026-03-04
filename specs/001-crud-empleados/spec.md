# Feature Specification: CRUD de Empleados

**Feature Branch**: `001-crud-empleados`  
**Created**: 2026-02-25  
**Status**: Ready for Implementation  
**Input**: User description: "crear un crud de empleados con campos clave, nombre, direccion y telefono donde clave sera el pk, y los demas campos sean de 100 espacios"

## Clarifications

### Session 2026-02-25

- Q: ¿Qué endpoints deben requerir autenticación básica? → A: Todo CRUD requiere autenticación básica; solo `/actuator/health`, `/api-docs` y `/swagger-ui.html` son públicos.
- Q: ¿Qué tipo/formato tendrá `clave`? → A: `clave` será PK autonumérica.
- Q: ¿Qué tipo de eliminación aplica al borrar empleados? → A: Eliminación física (hard delete), sin conservación histórica en esta feature.
- Q: ¿Se requiere paginación en el listado? → A: Sí, el listado de empleados debe ser paginado.
- Q: ¿Cuál debe ser el tamaño de página del listado? → A: La paginación debe ser de 5 registros por página.

### Session 2026-03-03

- Q: ¿Cómo manejar la validación de `clave` en creación con clave autonumérica? → A: Mantener `clave` autonumérica y eliminar reglas de duplicidad de `clave` en creación; cualquier `clave` enviada por cliente se ignora.
- Q: ¿Qué rutas de documentación/salud serán públicas de forma explícita? → A: Rutas públicas: `/actuator/health`, `/api-docs` y `/swagger-ui.html`.
- Q: ¿Qué campos debe aceptar la creación de empleado respecto a `clave`? → A: La creación acepta `nombre`, `direccion` y `telefono`; `clave` la asigna el sistema.
- Q: ¿Cómo debe concretarse FR-010 para evitar ambigüedad? → A: Debe invalidar altas/actualizaciones con campos vacíos o >100 caracteres y consultas con paginación inválida, devolviendo `400`.

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Registrar empleado (Priority: P1)

Como usuario de gestión, quiero registrar un empleado con nombre, dirección y teléfono
para comenzar a administrar su información.

**Why this priority**: Sin creación de registros no existe base de datos funcional para el resto del flujo.

**Independent Test**: Puede probarse de forma independiente creando un empleado válido y verificando
que queda disponible para consulta posterior.

**Acceptance Scenarios**:

1. **Given** que el usuario envía `nombre`, `direccion` y `telefono` válidos, **When** registra un
  empleado, **Then** el sistema guarda el registro, asigna `clave` autonumérica y confirma la creación.
2. **Given** que el cliente envía manualmente un valor de `clave` en la creación, **When** el usuario
  registra un empleado con los demás campos válidos, **Then** el sistema ignora ese valor y asigna
  una `clave` autonumérica propia.

---

### User Story 2 - Consultar empleados (Priority: P2)

Como usuario de gestión, quiero consultar uno o todos los empleados para revisar su información.

**Why this priority**: La consulta permite verificar datos capturados y soporta decisiones operativas.

**Independent Test**: Puede probarse consultando por clave existente y listando registros en bloque.

**Acceptance Scenarios**:

1. **Given** que existen empleados registrados, **When** el usuario consulta por clave o solicita el
  listado completo, **Then** el sistema devuelve la información solicitada con los datos guardados.

---

### User Story 3 - Actualizar y eliminar empleado (Priority: P3)

Como usuario de gestión, quiero actualizar o eliminar empleados existentes para mantener la
información vigente.

**Why this priority**: Completa el ciclo CRUD y asegura calidad de datos en el tiempo.

**Independent Test**: Puede probarse actualizando nombre/dirección/teléfono y luego eliminando un
empleado, validando ambos resultados.

**Acceptance Scenarios**:

1. **Given** que el empleado existe, **When** el usuario modifica uno o más campos permitidos,
  **Then** el sistema persiste el cambio y devuelve la versión actualizada.
2. **Given** que el empleado existe, **When** el usuario solicita eliminarlo, **Then** el sistema
  elimina el registro y deja de retornarlo en consultas posteriores.

---

### Edge Cases

- Intento de crear o actualizar con algún campo requerido vacío.
- Intento de registrar `nombre`, `direccion` o `telefono` con más de 100 caracteres.
- Intento de actualizar o eliminar una clave inexistente.
- Intento de consultar por clave inexistente.
- Intento de enviar `clave` manual en creación; el valor no debe aplicarse.
- Intento de acceder al CRUD sin autenticación válida.
- Verificación de consulta posterior a borrado físico: el registro no debe existir.
- Solicitar `page` con valor negativo o `size` distinto de 5.
- Solicitar una página mayor al total disponible.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST permitir crear empleados con los campos `nombre`, `direccion` y `telefono`.
- **FR-002**: `clave` MUST ser autonumérica, única y actuar como identificador principal del empleado.
- **FR-003**: `nombre`, `direccion` y `telefono` MUST ser obligatorios y aceptar hasta 100 caracteres cada uno.
- **FR-004**: El sistema MUST permitir consultar un empleado por su `clave`.
- **FR-005**: El sistema MUST permitir consultar el listado de empleados de forma paginada.
- **FR-006**: El sistema MUST permitir actualizar `nombre`, `direccion` y `telefono` de un empleado existente.
- **FR-007**: El sistema MUST impedir el cambio de `clave` una vez creado el empleado.
- **FR-008**: El sistema MUST permitir eliminar un empleado existente por `clave`.
- **FR-009**: El sistema MUST devolver mensajes claros cuando una operación falle por validación o por inexistencia del registro.
- **FR-010**: El sistema MUST rechazar con `400` las altas/actualizaciones con campos vacíos o con longitudes mayores a 100 caracteres, y MUST rechazar con `400` consultas con parámetros de paginación inválidos.
- **FR-011**: El sistema MUST requerir autenticación básica para todas las operaciones CRUD de empleados.
- **FR-012**: El sistema MUST permitir acceso público únicamente a `/actuator/health`, `/api-docs` y `/swagger-ui.html`.
- **FR-013**: La eliminación de empleados MUST ser física (hard delete), removiendo el registro de forma definitiva.
- **FR-014**: El sistema MUST asignar automáticamente `clave` al crear un empleado y MUST ignorar cualquier valor de `clave` enviado por el cliente en la alta.
- **FR-015**: El endpoint de listado MUST aceptar `page` y MUST aplicar paginación de 5 registros por página (`size` fijo en 5).
- **FR-016**: La respuesta del listado paginado MUST incluir metadatos de paginación (página actual, tamaño, total de elementos y total de páginas).

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa a una persona gestionada en el sistema. Atributos clave:
  `clave` (identificador autonumérico), `nombre`, `direccion`, `telefono`.

## Assumptions

- `clave` se genera de forma autonumérica y no se captura manualmente.
- El campo `telefono` se maneja como texto para permitir diferentes formatos.
- El alcance de esta feature no incluye búsqueda avanzada ni auditoría histórica.
- No se requiere recuperación de registros eliminados en esta versión.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% de altas válidas de empleados completan el proceso sin errores de validación.
- **SC-002**: 100% de intentos con `nombre`, `direccion` o `telefono` mayores a 100 caracteres son rechazados.
- **SC-003**: 100% de altas que incluyan `clave` en el payload deben ignorar ese valor y devolver una `clave` autonumérica asignada por el sistema.
- **SC-004**: Al menos 95% de operaciones CRUD estándar (crear, consultar, actualizar, eliminar)
  se completan en menos de 2 segundos en entorno de prueba.
- **SC-005**: 100% de consultas de listado deben respetar los parámetros de paginación y retornar metadatos consistentes.
