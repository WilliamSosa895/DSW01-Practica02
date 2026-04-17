# Data Model: Email En Empleado Para Autenticacion

## Entidades

### Empleado
- **Proposito**: Representa usuario autenticable del sistema.
- **Campos relevantes**:
  - `clave` (Long): PK autonumerica.
  - `nombre` (String): obligatorio para identificacion operativa.
  - `email` (String): obligatorio, unico case-insensitive, longitud 5..254, formato valido.
  - `direccion` (String): obligatorio.
  - `telefono` (String): obligatorio.
  - `contrasena` (String): obligatoria, solo escritura en API.
  - `activo` (Boolean): habilita o bloquea autenticacion.
  - `departamento` (Departamento): relacion obligatoria ya existente.
- **Reglas**:
  - Email no admite nulo, vacio ni formato invalido.
  - Email se normaliza (`trim`, lowercase) antes de guardar.
  - No se permiten emails duplicados (insensible a mayusculas/minusculas).
  - Solo empleados activos pueden autenticarse.

### CredencialesEmpleado (vista funcional)
- **Proposito**: Modelo funcional para proceso de autenticacion.
- **Campos**:
  - `email` (String normalizado)
  - `contrasena` (String)
  - `activo` (Boolean)
- **Reglas**:
  - Lookup por email normalizado.
  - Rechazo de autenticacion para empleado inexistente o inactivo.

## Relaciones

- `Departamento` 1:N `Empleado` se mantiene sin cambios.
- `Empleado` usa `email` como identificador de autenticacion en lugar de `nombre`.

## Validaciones funcionales

- Create/Update empleado requiere `email` valido.
- Duplicados de email devuelven conflicto funcional (`409`).
- Email invalido o ausente devuelve error de validacion (`400`).
- Respuestas de lectura de empleado incluyen `email` y excluyen `contrasena`.

## Migracion y transicion de estado

1. **Estado previo**: empleados historicos pueden no tener `email`.
2. **Backfill transitorio**: asignar email tecnico unico para registros legacy.
3. **Endurecimiento de esquema**: aplicar `NOT NULL` y unicidad sobre email normalizado.
4. **Estado objetivo**: todo empleado persistido tiene email valido y unico.
