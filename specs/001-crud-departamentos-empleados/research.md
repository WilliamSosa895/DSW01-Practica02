# Research: CRUD De Departamentos Relacionados A Empleados

## Phase 0 Decisions

### Estrategia de modelado de relacion
- **Decision**: Modelar relacion 1:N con `Departamento` como entidad padre y `Empleado` con referencia obligatoria a un unico `Departamento`.
- **Rationale**: Refleja de forma directa la regla funcional solicitada (un departamento tiene muchos empleados y un empleado pertenece a uno solo).
- **Alternatives considered**:
  - Tabla intermedia N:M: descartado por sobre-modelado y no alinear con dominio solicitado.
  - Campo textual de departamento en empleado: descartado por falta de integridad referencial.

### Politica de eliminacion de departamento
- **Decision**: Rechazar eliminacion de departamento con empleados asociados mediante error funcional de conflicto.
- **Rationale**: Evita empleados huerfanos y mantiene consistencia de la relacion obligatoria.
- **Alternatives considered**:
  - Eliminacion en cascada de empleados: descartado por riesgo de perdida de datos.
  - Reasignacion automatica a departamento por defecto: descartado por ambiguedad de negocio no solicitada.

### Versionado de API para nueva funcionalidad
- **Decision**: Exponer CRUD de departamentos y operaciones relacionadas bajo `/api/v1`.
- **Rationale**: Cumple constitucion 1.2.0 y mantiene coherencia con la estrategia versionada ya adoptada.
- **Alternatives considered**:
  - Reusar rutas sin version: descartado por incumplimiento constitucional.

### Paginacion en listados
- **Decision**: Mantener `size=5` como politica fija en listados de departamentos y consultas relacionadas.
- **Rationale**: Cumple politica constitucional y homogeneiza el consumo de listados.
- **Alternatives considered**:
  - Paginacion libre por cliente: descartado por inconsistencia con la norma vigente.

### Pruebas de relacion y contrato
- **Decision**: Cubrir con pruebas unitarias e integracion los casos de asignacion, rechazo de referencia inexistente, y rechazo de eliminacion con dependencias.
- **Rationale**: Provee evidencia ejecutable de integridad referencial y comportamiento funcional esperado.
- **Alternatives considered**:
  - Solo pruebas manuales: descartado por baja trazabilidad y mayor riesgo de regresion.

## Clarifications Resolution

No quedan elementos `NEEDS CLARIFICATION` en el contexto tecnico de esta feature.

## Validation Notes (Implementation)

- Se completo compilacion sin tests con `mvn -DskipTests compile` (BUILD SUCCESS).
- Se ejecutaron pruebas unitarias clave para servicios y mapeos con resultado exitoso (12 ejecutadas, 0 fallas).
- La regresion completa de integracion queda condicionada a disponibilidad de Docker/Testcontainers en el entorno de ejecucion.
