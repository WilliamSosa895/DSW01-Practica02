# Data Model: CRUD de Empleados

## Entidades

### Empleado
- **Propósito**: Representa un empleado administrado por el sistema.
- **Campos**:
  - `clave` (Long): identificador primario autonumérico, generado por base de datos.
  - `nombre` (String): obligatorio, longitud 1..100.
  - `direccion` (String): obligatorio, longitud 1..100.
  - `telefono` (String): obligatorio, longitud 1..100.
- **Reglas**:
  - `clave` es inmutable después de la creación.
  - `nombre`, `direccion` y `telefono` no aceptan vacío ni longitudes >100.

### EmpleadoPageResponse
- **Propósito**: Estructura de salida para listados paginados.
- **Campos**:
  - `content` (List<EmpleadoResponse>): elementos de la página solicitada.
  - `page` (Integer): índice de página base 0.
  - `size` (Integer): tamaño aplicado (valor fijo 5).
  - `totalElements` (Long): total de registros.
  - `totalPages` (Integer): total de páginas calculadas.

### ErrorResponse
- **Propósito**: Contrato estándar para errores de validación y negocio.
- **Campos**:
  - `status` (Integer): código HTTP.
  - `error` (String): categoría del error.
  - `message` (String): detalle legible.
  - `path` (String): ruta solicitada.
  - `timestamp` (String): fecha-hora del error.

## Relaciones

- No hay relaciones entre entidades en este alcance.

## Reglas de validación funcional

- Alta (`POST`): solo acepta `nombre`, `direccion`, `telefono`; cualquier `clave` enviada por cliente se ignora.
- Consulta paginada (`GET /api/empleados`): `page >= 0`, `size = 5`; parámetros inválidos retornan `400`.
- Consulta fuera de rango de páginas: retorna `200` con `content` vacío y metadatos consistentes.
- Actualización (`PUT`): permite cambiar solo `nombre`, `direccion`, `telefono`.
- Eliminación (`DELETE`): eliminación física definitiva.

## Transiciones de estado

1. **Creado**: se persiste empleado con `clave` generada.
2. **Leído**: se recupera por `clave` o paginado.
3. **Actualizado**: cambian campos editables manteniendo `clave`.
4. **Eliminado**: registro borrado físicamente, deja de estar disponible.
