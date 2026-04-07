# Research: Autenticacion Por Empleado

## Phase 0 Decisions

### Estrategia de autenticacion
- **Decision**: Usar credenciales de la entidad empleado (`nombre` + `contraseña`) como fuente de autenticación para endpoints protegidos.
- **Rationale**: Cumple la necesidad funcional del usuario y reduce dependencia de credenciales estáticas del runtime.
- **Alternatives considered**:
  - Mantener usuario fijo en `application.properties`: descartado por no cumplir el requerimiento.
  - Agregar proveedor externo de identidad: descartado por fuera de alcance.

### Manejo de contraseña
- **Decision**: Tratar `contraseña` como dato sensible: obligatoria para autenticación y excluida de respuestas de lectura.
- **Rationale**: Minimiza exposición accidental de credenciales y alinea la feature con principios de seguridad.
- **Alternatives considered**:
  - Retornar contraseña en DTO de lectura: descartado por riesgo de seguridad.
  - Campo opcional de contraseña: descartado porque invalida autenticación por empleado.

### Versionado de API
- **Decision**: Publicar endpoints modificados/nuevos de esta feature bajo prefijo versionado `/api/v1`.
- **Rationale**: Cumple constitución 1.2.0 y facilita evolución de contrato sin romper consumidores.
- **Alternatives considered**:
  - Reutilizar rutas sin versión: descartado por incumplir regla constitucional vigente.

### Paginación de listados
- **Decision**: Mantener política de paginación de 5 registros por bloque en listados de empleados.
- **Rationale**: Conserva comportamiento actual y cumple regla constitucional para listados.
- **Alternatives considered**:
  - Tamaño configurable libre por cliente: descartado por inconsistencia con norma vigente.

### Pruebas y validación
- **Decision**: Cubrir con pruebas unitarias de autenticación/validación y pruebas de integración para acceso autorizado/no autorizado y no exposición de contraseña.
- **Rationale**: Proporciona evidencia ejecutable del cambio de seguridad y contrato.
- **Alternatives considered**:
  - Solo pruebas manuales: descartado por baja trazabilidad.

## Clarifications Resolution

No quedan elementos `NEEDS CLARIFICATION` en contexto técnico para esta fase.
