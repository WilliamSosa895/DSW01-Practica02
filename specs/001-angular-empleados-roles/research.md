# Phase 0 Research - Frontend Empleados con Roles

## Decision 1: Angular consumira Basic Auth mediante interceptor HTTP
- Decision: El frontend enviara la cabecera Authorization Basic en cada request usando un interceptor central.
- Rationale: Evita duplicacion de logica en servicios y garantiza consistencia de autenticacion para todos los endpoints protegidos.
- Alternatives considered: Persistir credenciales codificadas en localStorage. Rechazado por mayor superficie de riesgo ante XSS.

## Decision 2: Manejo centralizado de errores de autenticacion/autorizacion
- Decision: Se implementara un interceptor de errores para 401/403 con cierre de sesion local y redireccion a login.
- Rationale: La API usa Basic Auth y no expone token renovable; centralizar evita comportamientos inconsistentes por pantalla.
- Alternatives considered: Manejar 401/403 en cada componente. Rechazado por duplicacion y alto riesgo de omisiones.

## Decision 3: El rol funcional se modelara en el dominio de Empleado
- Decision: Se agregara un atributo de rol en Empleado (MASTER, STANDARD) para soportar autorizacion diferenciada.
- Rationale: El backend actual autentica todo como EMPLOYEE; para cumplir la feature se necesita una fuente de verdad de permisos.
- Alternatives considered: Resolver permisos solo en UI por email hardcodeado. Rechazado por inseguro y no auditable.

## Decision 4: El backend aplicara autorizacion por rol en operaciones de escritura
- Decision: Crear/editar/eliminar empleados requerira rol MASTER; lectura permitida para MASTER y STANDARD.
- Rationale: La restriccion de permisos debe validarse en servidor, no solo en interfaz.
- Alternatives considered: Ocultar botones en UI sin control servidor. Rechazado porque puede bypass con llamadas directas.

## Decision 5: Paginacion estricta a 5 registros por pagina en UI y API
- Decision: El frontend fijara size=5 y no mostrara selector de tamano de pagina.
- Rationale: El backend valida size exacto 5; exponer otro valor produce errores evitables.
- Alternatives considered: Permitir cambio de size y manejar error 400. Rechazado por mala UX.

## Decision 6: Validaciones alineadas entre Angular y backend
- Decision: Formularios Angular replicaran validaciones de campos requeridos/formato y mostraran errores legibles.
- Rationale: Reduce roundtrips fallidos y mantiene consistencia con reglas de servidor.
- Alternatives considered: Validar unicamente en backend. Rechazado por experiencia de usuario deficiente.

## Decision 7: Contrato de integracion UI-API como artefacto explicito
- Decision: Se documentaran endpoints, codigos HTTP, reglas por rol y payloads en contracts/.
- Rationale: Facilita desarrollo paralelo frontend/backend y pruebas de aceptacion.
- Alternatives considered: Depender solo de lectura del codigo backend. Rechazado por baja trazabilidad.
