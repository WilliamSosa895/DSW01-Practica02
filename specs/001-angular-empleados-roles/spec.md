# Feature Specification: Frontend Empleados con Roles

**Feature Branch**: `001-angular-empleados-roles`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "Implementar un FRONTEND en Angular con login por email y contrasena, CRUD de empleados para el usuario master y acceso solo de lectura para empleados estandar"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Inicio de sesion por email (Priority: P1)

Como empleado, quiero iniciar sesion con email y contrasena para acceder al sistema segun mis permisos.

**Why this priority**: Sin autenticacion no existe acceso seguro al resto de funcionalidades.

**Independent Test**: Puede probarse de forma aislada autenticando un usuario valido y verificando rechazo de credenciales invalidas.

**Acceptance Scenarios**:

1. **Given** un empleado con credenciales validas, **When** envia su email y contrasena en la pantalla de login, **Then** accede al sistema y visualiza el modulo de empleados.
2. **Given** un usuario con credenciales invalidas, **When** intenta iniciar sesion, **Then** se deniega el acceso y se muestra un mensaje claro sin exponer datos sensibles.
3. **Given** una sesion autenticada, **When** el usuario cierra sesion, **Then** el sistema finaliza el acceso y requiere nuevo login para volver a entrar.

---

### User Story 2 - Gestion completa para master (Priority: P2)

Como empleado master, quiero crear, editar y eliminar empleados para mantener el catalogo actualizado.

**Why this priority**: La administracion de empleados es el objetivo principal del flujo CRUD solicitado.

**Independent Test**: Puede validarse iniciando sesion como master y ejecutando altas, ediciones y bajas sobre empleados existentes.

**Acceptance Scenarios**:

1. **Given** un usuario master autenticado, **When** registra un nuevo empleado con datos validos, **Then** el empleado queda disponible en el listado.
2. **Given** un usuario master autenticado y un empleado existente, **When** actualiza los datos del empleado, **Then** el listado refleja la informacion modificada.
3. **Given** un usuario master autenticado y un empleado existente, **When** elimina ese empleado, **Then** el sistema lo retira del listado y confirma la accion.

---

### User Story 3 - Consulta para empleados estandar (Priority: P3)

Como empleado estandar, quiero ver el listado y detalle de empleados sin poder modificar datos.

**Why this priority**: Garantiza acceso a informacion operativa sin comprometer la integridad de los datos.

**Independent Test**: Puede probarse iniciando sesion como empleado estandar e intentando acciones de consulta y modificacion.

**Acceptance Scenarios**:

1. **Given** un empleado estandar autenticado, **When** abre el modulo de empleados, **Then** puede consultar listado y detalle.
2. **Given** un empleado estandar autenticado, **When** intenta crear, editar o eliminar un empleado, **Then** el sistema bloquea la accion y comunica falta de permisos.

### Edge Cases

- Intento de inicio de sesion con email existente y contrasena incorrecta en multiples intentos consecutivos.
- Sesion expirada o invalida durante una operacion de consulta o edicion desde la interfaz web.
- Usuario estandar intentando invocar acciones de escritura mediante navegacion directa o manipulacion de URL.
- Conflicto de actualizacion cuando dos usuarios master editan el mismo empleado casi al mismo tiempo.
- Validaciones de datos inconsistentes entre entrada de formulario y reglas del backend para campos obligatorios.
- Listados vacios, pagina fuera de rango o tamano de pagina distinto de la politica establecida por backend.
- Si dos usuarios MASTER actualizan el mismo empleado de forma concurrente, el primer guardado valido prevalece y el segundo intento recibe conflicto de concurrencia con instruccion de recargar datos antes de reintentar.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST permitir autenticacion de empleados mediante email y contrasena.
- **FR-002**: El sistema MUST negar acceso a usuarios no autenticados para cualquier pantalla de gestion de empleados.
- **FR-003**: El sistema MUST asignar permisos en funcion del rol del empleado autenticado.
- **FR-004**: El sistema MUST permitir al rol master crear nuevos empleados con los datos requeridos por el dominio.
- **FR-005**: El sistema MUST permitir al rol master editar la informacion de empleados existentes.
- **FR-006**: El sistema MUST permitir al rol master eliminar empleados existentes.
- **FR-007**: El sistema MUST permitir al rol estandar consultar listado y detalle de empleados.
- **FR-008**: El sistema MUST impedir al rol estandar ejecutar operaciones de creacion, edicion y eliminacion.
- **FR-009**: El sistema MUST devolver, para respuestas 401 y 403, un formato minimo de error con: codigo de error estable, mensaje legible para usuario, accion sugerida y timestamp opcional, y la interfaz MUST mostrar ese mensaje sin exponer detalles sensibles.
- **FR-010**: El sistema MUST mantener consistencia entre validaciones de entrada en la interfaz web y validaciones aplicadas por el backend.
- **FR-011**: El sistema MUST conservar navegacion y acciones diferenciadas por rol para evitar que usuarios sin permiso vean controles de escritura.
- **FR-012**: El sistema MUST reflejar en el listado los cambios de alta, edicion y baja inmediatamente tras una operacion exitosa.
- **FR-013**: El sistema MUST detectar conflicto de concurrencia cuando dos usuarios MASTER intenten actualizar el mismo empleado sobre versiones desfasadas y responder con rechazo de conflicto y mensaje de recarga antes de reintentar.

### Key Entities *(include if feature involves data)*

- **Empleado**: Persona registrada en el sistema con atributos de identificacion y contacto (incluye email unico), estado de acceso y rol funcional.
- **Rol de acceso**: Clasificacion de permisos del empleado autenticado que determina si puede solo consultar o tambien administrar empleados.
- **Sesion autenticada**: Estado de acceso activo asociado a un empleado validado, necesario para operar en la interfaz web.

## Assumptions

- Los roles de negocio MASTER y STANDARD se introducen en esta feature y quedan persistidos como parte del modelo de Empleado.
- La gestion de permisos se aplica de forma consistente en backend y en la interfaz web.
- El modulo de empleados conserva las reglas del dominio existentes sobre campos obligatorios y validaciones.
- El acceso al modulo se realiza dentro del mismo sistema autenticado, sin integracion con un proveedor externo de identidad.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: El 95% de usuarios con credenciales validas completa el inicio de sesion y llega al modulo de empleados en menos de 30 segundos.
- **SC-002**: El 100% de intentos de creacion, edicion y eliminacion hechos por usuarios estandar es bloqueado correctamente.
- **SC-003**: El 95% de operaciones CRUD ejecutadas por usuarios master refleja el resultado esperado en el listado en menos de 2 segundos.
- **SC-004**: Al menos el 90% de usuarios de negocio completa sin asistencia una tarea de consulta o administracion de empleados en su primer intento.
