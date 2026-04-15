# Research: Email En Empleado Para Autenticacion

## Phase 0 Decisions

### Estrategia de identificador de autenticacion
- **Decision**: Autenticar empleados activos usando `email` como `username` en Basic Auth, manteniendo `contrasena` como secreto y compatibilidad transitoria para credencial constitucional `admin/admin123`.
- **Rationale**: Alinea el requerimiento funcional de login por email sin romper la regla constitucional vigente durante la transicion.
- **Alternatives considered**:
  - Mantener autenticacion por `nombre`: descartado por incumplimiento del requisito principal.
  - Soportar login dual permanente (`nombre` o `email`): descartado por complejidad adicional y reglas ambiguas de colision.

### Contrato de errores verificables
- **Decision**: Estandarizar errores de email invalido y duplicado con `ErrorResponse` incluyendo `status`, `error`, `message` y `path`.
- **Rationale**: Elimina ambiguedad de validacion y conflicto, y mantiene coherencia con features previas.
- **Alternatives considered**:
  - Mensajes de error no estructurados: descartado por baja trazabilidad en pruebas de integracion.

### Regla de unicidad de email
- **Decision**: Tratar `email` como unico case-insensitive mediante normalizacion (`trim` + `lowercase`) antes de persistir y validar duplicados.
- **Rationale**: Evita duplicados semanticamente equivalentes y simplifica consultas de autenticacion.
- **Alternatives considered**:
  - Unicidad case-sensitive: descartado por permitir duplicados confusos (`A@x.com` vs `a@x.com`).
  - Delegar unicidad solo al cliente: descartado por falta de garantia en servidor.

### Validacion de formato de email
- **Decision**: Aplicar validaciones en request DTO y servicio para rechazar email nulo/vacio/formato invalido con respuesta de validacion.
- **Rationale**: Provee retroalimentacion temprana y consistente al consumidor API.
- **Alternatives considered**:
  - Validar solo en base de datos: descartado por mensajes de error menos claros.
  - Validar solo en frontend: descartado porque no todos los clientes comparten UI.

### Migracion de datos existentes sin email
- **Decision**: Definir migracion de transicion para registros legacy: backfill de email tecnico unico (`legacy+{clave}@example.local`) previo a endurecer `NOT NULL` y `UNIQUE`.
- **Rationale**: Permite evolucionar esquema sin bloquear despliegues con datos historicos.
- **Alternatives considered**:
  - Borrar datos legacy: descartado por perdida de informacion.
  - Permitir `NULL` indefinidamente: descartado por incumplir email obligatorio.

### Actualizacion de contrato y pruebas
- **Decision**: Actualizar OpenAPI y ampliar cobertura unit/integration para create/update, conflictos por duplicado y autenticacion por email.
- **Rationale**: Mantiene trazabilidad API-first y reduce riesgo de regresion en seguridad.
- **Alternatives considered**:
  - Ajuste parcial sin contrato: descartado por incumplimiento constitucional.
  - Solo pruebas manuales: descartado por baja repetibilidad.

## Clarifications Resolution

No quedan elementos `NEEDS CLARIFICATION` en el contexto tecnico de esta feature.
