# Research: CRUD de Empleados

## Phase 0 Decisions

### Rendimiento y capacidad
- **Decision**: Definir objetivos de rendimiento para el MVP: `p95` de lectura ≤ 300 ms, `p95` de escritura ≤ 500 ms y capacidad objetivo de 30-50 req/s por instancia.
- **Rationale**: Provee umbrales verificables para una práctica académica sin sobredimensionar complejidad operativa.
- **Alternatives considered**:
  - Umbrales más exigentes (`p95 < 100 ms`, >200 req/s): descartado por no aportar valor al alcance actual.
  - Sin objetivos de rendimiento: descartado por impedir criterios objetivos de aceptación.

### Restricciones operativas
- **Decision**: Establecer timeout máximo de 2 s por request, payload máximo de 1 MB y memoria objetivo 512 MB (dev) / 1 GB (demo).
- **Rationale**: Mantiene comportamiento predecible y evita consumo excesivo de recursos en ejecuciones locales.
- **Alternatives considered**:
  - Sin límites de timeout/payload: descartado por riesgo de degradación y abuso.
  - Límites más estrictos (<250 KB payload): descartado por innecesario para CRUD textual.

### Regla de paginación
- **Decision**: Aplicar paginación de empleados con `page >= 0` y `size` fijo en 5.
- **Rationale**: Cumple la clarificación funcional vigente y simplifica interoperabilidad cliente-servidor.
- **Alternatives considered**:
  - `size` configurable por cliente: descartado por contradecir requerimiento explícito.
  - Sin paginación: descartado por afectar escalabilidad del listado.

### Seguridad de endpoints
- **Decision**: Exigir Basic Auth para CRUD (`/api/**`) y tratar como públicas `/actuator/health`, `/api-docs` y `/swagger-ui.html` conforme a la especificación de la feature.
- **Rationale**: Respeta la clarificación funcional aprobada para facilitar observabilidad y documentación durante validación manual.
- **Alternatives considered**:
  - Público solo health: descartado por no cumplir la necesidad de documentación accesible en esta feature.
  - Proteger también documentación: descartado en esta iteración por impacto en el flujo de pruebas manuales.

### Estrategia de pruebas
- **Decision**: Implementar pruebas unitarias para reglas de dominio/validación, pruebas de integración con PostgreSQL (Testcontainers) y chequeo de sincronía de contrato OpenAPI.
- **Rationale**: Cubre lógica, persistencia y contrato HTTP, cumpliendo el principio de calidad ejecutable.
- **Alternatives considered**:
  - Solo pruebas unitarias: descartado por no validar seguridad ni integración real.
  - Solo pruebas de integración: descartado por feedback más lento y menor aislamiento de fallas.

## Clarifications Resolution

No quedan elementos `NEEDS CLARIFICATION` en contexto técnico para esta fase.
