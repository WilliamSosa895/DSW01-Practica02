# Feature Specification: Email En Empleado Para Autenticacion

**Feature Branch**: `001-empleado-email-auth`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "necesito extender la entidad empleado para incluir el campo email. el campo debe ser obligatorio ya que la autenticacion sera mediante email y contraseña, el email debe ser unico a nivel sistema, se debe validar el formato de correo. Actualizar modelo Empleado y restricciones en base de datos. Actualizar DTOs de request/response de empleados. Actualizar repositorio/servicio/controlador según corresponda. Actualizar contrato OpenAPI. Agregar o actualizar pruebas unitarias e integración. Mantener rutas versionadas /api/v1 y reglas de seguridad actuales."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Registrar y actualizar empleado con email valido (Priority: P1)

Como administrador funcional, quiero registrar y actualizar empleados con email obligatorio para garantizar credenciales consistentes y contacto identificable.

**Why this priority**: Sin email obligatorio y validado no se puede cumplir el nuevo metodo de autenticacion ni la integridad de datos de empleado.

**Independent Test**: Crear y actualizar empleados con email valido, verificando persistencia y rechazo de correos nulos, vacios o con formato invalido.

**Acceptance Scenarios**:

1. **Given** que el sistema recibe datos validos de empleado con email valido, **When** se crea el empleado, **Then** el empleado queda registrado con email persistido.
2. **Given** que existe un empleado, **When** se actualiza con un email valido, **Then** el sistema guarda el nuevo email y lo refleja en consultas posteriores.
3. **Given** que el email es nulo, vacio o invalido, **When** se intenta crear o actualizar empleado, **Then** el sistema rechaza la operacion con error de validacion.

---

### User Story 2 - Evitar duplicidad de email en el sistema (Priority: P2)

Como administrador funcional, quiero que el email de empleado sea unico para evitar colisiones de identidad y ambiguedad de credenciales.

**Why this priority**: La unicidad evita conflictos operativos y soporta autenticacion confiable por email.

**Independent Test**: Intentar crear o actualizar un empleado con email ya existente y validar rechazo de conflicto.

**Acceptance Scenarios**:

1. **Given** que ya existe un empleado con cierto email, **When** se intenta registrar otro empleado con ese mismo email, **Then** el sistema rechaza la operacion con conflicto.
2. **Given** que existe un empleado con email registrado, **When** otro empleado intenta adoptar ese email en una actualizacion, **Then** el sistema rechaza la operacion con conflicto.
3. **Given** que existe un email equivalente variando mayusculas/minusculas, **When** se intenta reutilizarlo, **Then** el sistema lo considera duplicado y rechaza la operacion.

---

### User Story 3 - Autenticar empleados con email y contrasena (Priority: P3)

Como usuario autenticado, quiero iniciar sesion usando email y contrasena para usar un identificador de acceso estandar.

**Why this priority**: Completa el valor funcional del cambio al trasladar el inicio de sesion desde nombre hacia email.

**Independent Test**: Consumir endpoints protegidos usando Basic Auth con email y contrasena validos, y verificar rechazo con credenciales invalidas.

**Acceptance Scenarios**:

1. **Given** que un empleado activo existe con email y contrasena validos, **When** autentica contra un endpoint protegido, **Then** el sistema permite acceso.
2. **Given** que el email no existe o la contrasena es incorrecta, **When** autentica contra un endpoint protegido, **Then** el sistema rechaza con no autorizado.
3. **Given** que el empleado esta inactivo, **When** intenta autenticarse con email y contrasena correctos, **Then** el sistema rechaza el acceso.

---

### Edge Cases

- Solicitud de alta o actualizacion sin campo email en payload.
- Email con espacios al inicio o al final.
- Email con caracteres invalidos o dominio incompleto.
- Intento de duplicado con variaciones de mayusculas/minusculas.
- Intento de autenticacion con nombre de empleado en lugar de email.
- Solicitud sin credenciales o con credenciales invalidas en endpoints protegidos.
- Consumo de rutas no versionadas para endpoints modificados por esta feature.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST extender la entidad empleado para incluir el campo email obligatorio.
- **FR-002**: El sistema MUST validar que el email cumpla formato de correo electronico valido en altas y actualizaciones.
- **FR-003**: El sistema MUST rechazar altas y actualizaciones cuando el email este ausente, vacio o invalido.
- **FR-004**: El sistema MUST garantizar que el email sea unico a nivel sistema para empleados.
- **FR-005**: El sistema MUST considerar duplicidad de email sin distinguir mayusculas y minusculas.
- **FR-006**: El sistema MUST devolver `409 Conflict` verificable cuando exista duplicidad de email, usando `ErrorResponse` con al menos `status`, `error`, `message` y `path`.
- **FR-007**: El sistema MUST devolver `400 Bad Request` verificable cuando el formato de email sea invalido o ausente, usando `ErrorResponse` con al menos `status`, `error`, `message` y `path`.
- **FR-008**: El sistema MUST permitir autenticacion de endpoints protegidos usando email y contrasena de empleado activo.
- **FR-009**: El sistema MUST mantener protegidos con Basic Auth los endpoints privados y conservar como publicos solo `GET /actuator/health`, `GET /api-docs` y `GET /swagger-ui.html`.
- **FR-010**: El sistema MUST exponer endpoints nuevos o modificados bajo rutas versionadas `/api/v1/...`.
- **FR-011**: El sistema MUST actualizar contratos de request y response de empleados para incluir email en operaciones de escritura y lectura.
- **FR-012**: El sistema MUST actualizar la documentacion OpenAPI para reflejar campo email, reglas de validacion y errores funcionales.
- **FR-013**: El sistema MUST incluir pruebas unitarias e integracion para escenarios validos, invalidos y de conflicto relacionados con email y autenticacion por email.
- **FR-014**: El sistema MUST conservar compatibilidad transitoria con la credencial base constitucional `admin` / `admin123` durante la migracion a autenticacion por email, sin relajar la regla de email obligatorio para empleados nuevos/actualizados.

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa al usuario autenticable del sistema. Incorpora email como identificador unico de acceso, junto con contrasena y estado activo.
- **Credenciales de Empleado**: Representan la combinacion funcional de email, contrasena y estado activo utilizada para autorizar acceso a endpoints protegidos.

## Assumptions

- El email se almacena normalizado para comparaciones consistentes de unicidad.
- La contrasena permanece como credencial obligatoria y su politica actual no cambia por esta feature.
- La migracion de datos existentes sin email se atiende con una estrategia definida en plan y tareas antes de exigir email obligatorio en produccion.
- La estructura de respuesta de errores mantiene el formato estandar del proyecto.
- La compatibilidad transitoria de `admin` / `admin123` aplica unicamente como puente de migracion y debe retirarse en una historia de endurecimiento de seguridad.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% de altas de empleado con email valido y unico finalizan exitosamente.
- **SC-002**: 100% de solicitudes de alta o actualizacion con email ausente o invalido son rechazadas con error de validacion.
- **SC-003**: 100% de intentos de registrar o actualizar email duplicado son rechazados con conflicto.
- **SC-004**: 100% de accesos autenticados a endpoints protegidos se completan usando email y contrasena validos de empleado activo.
- **SC-005**: 100% de endpoints de empleados impactados quedan documentados en OpenAPI con el campo email y sus reglas de validacion.
