# Data Model: Autenticacion Por Empleado

## Entidades

### Empleado
- **Proposito**: Representa al usuario funcional autenticable en el sistema.
- **Campos relevantes**:
  - `clave` (Long): PK autonumerica.
  - `nombre` (String): identificador de inicio de sesion, obligatorio, longitud 1..100, unico en alcance funcional.
  - `direccion` (String): obligatorio, longitud 1..100.
  - `telefono` (String): obligatorio, longitud 1..100.
  - `contraseña` (String): obligatorio para autenticacion, restringido a escritura/actualizacion.
  - `activo` (Boolean): indica si el empleado puede autenticarse.
- **Reglas**:
  - Lecturas de empleado no exponen `contraseña`.
  - Operaciones de autenticacion requieren `nombre` + `contraseña` validos y empleado activo.

### EventoAutenticacion
- **Proposito**: Registro de intentos de acceso para auditoria funcional basica.
- **Campos relevantes**:
  - `id` (Long): identificador del evento.
  - `nombreIntentado` (String): nombre usado en el intento.
  - `empleadoClave` (Long, nullable): referencia si se resolvio empleado.
  - `resultado` (Enum): `EXITO` o `FALLO`.
  - `fechaHora` (OffsetDateTime): momento del intento.

### EmpleadoPageResponse
- **Proposito**: Respuesta de listados paginados de empleados.
- **Campos**:
  - `content` (List<EmpleadoResponse>): sin `contraseña`.
  - `page` (Integer): pagina actual base 0.
  - `size` (Integer): tamano aplicado (fijo 5).
  - `totalElements` (Long): total de registros.
  - `totalPages` (Integer): total de paginas.

## Relaciones

- `EventoAutenticacion` se relaciona opcionalmente con `Empleado` por `empleadoClave`.

## Reglas de validacion funcional

- `nombre` y `contraseña` no aceptan valores vacios en autenticacion.
- Contraseñas invalidas o nombre inexistente producen rechazo de acceso.
- Listados de empleados mantienen `size = 5` por politica.
- Endpoints nuevos/modificados de esta feature se exponen en rutas `/api/v1/...`.

## Transiciones de estado

1. **Empleado creado/actualizado**: persiste `contraseña` y estado de activacion.
2. **Autenticacion exitosa**: acceso autorizado y evento `EXITO`.
3. **Autenticacion fallida**: acceso rechazado y evento `FALLO`.
