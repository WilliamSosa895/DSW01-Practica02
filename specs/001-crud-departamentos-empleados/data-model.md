# Data Model: CRUD De Departamentos Relacionados A Empleados

## Entidades

### Departamento
- **Proposito**: Representa una unidad organizacional que agrupa empleados.
- **Campos relevantes**:
  - `clave` (Long): PK autonumerica.
  - `nombre` (String): obligatorio, longitud 1..100, unico.
  - `empleados` (List<Empleado>): coleccion de empleados pertenecientes al departamento.
- **Reglas**:
  - No se permite nombre duplicado.
  - No se permite eliminar departamento con empleados asociados.

### Empleado
- **Proposito**: Representa personal autenticable del sistema.
- **Campos relevantes**:
  - `clave` (Long): PK autonumerica.
  - `nombre` (String): obligatorio, unico funcional.
  - `direccion` (String): obligatorio.
  - `telefono` (String): obligatorio.
  - `contrasena` (String): obligatoria para autenticacion, solo escritura.
  - `activo` (Boolean): habilita autenticacion.
  - `departamento` (Departamento): referencia obligatoria a un unico departamento.
- **Reglas**:
  - Debe existir el departamento referenciado para crear/actualizar empleado.
  - Lecturas no exponen credenciales sensibles.

### DepartamentoPageResponse
- **Proposito**: Respuesta paginada de listados de departamentos.
- **Campos**:
  - `content` (List<DepartamentoResponse>)
  - `page` (Integer)
  - `size` (Integer, fijo en 5)
  - `totalElements` (Long)
  - `totalPages` (Integer)

## Relaciones

- `Departamento` 1:N `Empleado`.
- `Empleado` N:1 `Departamento` (obligatoria).

## Reglas de validacion funcional

- `Departamento.nombre` no acepta nulo, vacio ni duplicado.
- `Empleado.departamento` es obligatorio para operaciones de alta y actualizacion.
- Eliminar un departamento con empleados asociados retorna error funcional.
- Listados de departamentos y consultas relacionadas mantienen `size = 5`.
- Endpoints nuevos/modificados de esta feature se exponen en rutas `/api/v1/...`.

## Transiciones de estado

1. **Departamento creado**: queda disponible para asignacion de empleados.
2. **Empleado asignado/reasignado**: cambia su pertenencia a departamento valido.
3. **Intento de eliminacion bloqueado**: departamento con empleados no se elimina.
4. **Departamento eliminado**: solo permitido cuando no tiene empleados asociados.
