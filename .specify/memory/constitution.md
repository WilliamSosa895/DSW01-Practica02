<!--
Sync Impact Report
- Version change: 1.2.0 → 1.3.0
- Modified principles:
  - I. Spring Boot + Java 17 Obligatorio → I. Spring Boot + Java 17 Obligatorio
  - V. Calidad Ejecutable, Entrega Trazable y Paginación Consistente → V. Calidad Ejecutable, Entrega Trazable y Paginación Consistente
- Added sections:
  - VI. Frontend Angular para Flujos CRUD con UI
- Removed sections: Ninguna
- Templates requiring updates:
  - ✅ .specify/templates/plan-template.md
  - ✅ .specify/templates/spec-template.md
  - ✅ .specify/templates/tasks-template.md
  - ⚠ pending: .specify/templates/commands/*.md (ruta no existe en este repositorio)
  - ⚠ pending: README.md / docs/quickstart.md (archivos no existen en este repositorio)
- Deferred TODOs:
  - TODO(COMMAND_TEMPLATES_PATH): definir si el equipo usará .specify/templates/commands/ para comandos personalizados.
-->

# DSW01-Practica02 Constitution

## Core Principles

### I. Spring Boot + Java 17 Obligatorio
Todo servicio backend MUST implementarse con Spring Boot sobre Java 17. El uso de
otro framework principal de backend o de otra versión mayor de Java requiere una
enmienda explícita de esta constitución. Rationale: estandariza ejecución,
compatibilidad de herramientas y mantenimiento del equipo.

### II. Seguridad con Autenticación Básica
Todos los endpoints HTTP MUST estar protegidos con autenticación básica en entornos
de desarrollo y validación interna, excepto endpoints de salud y documentación
(OpenAPI/Swagger) que se declaren de forma explícita en cada especificación. Las
credenciales base del proyecto MUST ser admin y admin 123 hasta que una historia
de seguridad defina rotación y gestión de secretos. Rationale: asegura un baseline
mínimo de protección y consistencia sin bloquear observabilidad y documentación.

### III. Persistencia PostgreSQL con Docker
La persistencia de datos MUST usar PostgreSQL y su provisión local MUST ejecutarse
mediante Docker (por ejemplo, Docker Compose). La configuración de conexión MUST
externalizarse en propiedades o variables de entorno y no en código. Rationale:
garantiza paridad entre entornos y despliegues reproducibles.

### IV. API-First con OpenAPI/Swagger y Versionado
Toda capacidad expuesta por HTTP MUST quedar documentada en OpenAPI y ser
navegable mediante Swagger UI durante desarrollo. Ningún endpoint nuevo se
considera completo sin contrato y documentación accesible. Toda nueva
funcionalidad expuesta por API MUST publicarse en rutas versionadas (por ejemplo,
`/api/v1/...`) o en un esquema equivalente explícitamente documentado. Rationale:
reduce ambigüedad entre desarrollo, pruebas e integración y permite evolución
controlada del contrato.

### V. Calidad Ejecutable, Entrega Trazable y Paginación Consistente
Cada cambio funcional MUST incluir validación ejecutable proporcional al riesgo
(mínimo pruebas unitarias de lógica y pruebas de integración de endpoints críticos),
y MUST actualizar su documentación técnica asociada (contratos, configuración,
pasos de ejecución). Para endpoints de listado en nuevas funcionalidades, la API
MUST implementar paginación por bloques de 5 registros cuando no exista una regla
de dominio distinta aprobada en la especificación. Rationale: evita regresiones,
mantiene trazabilidad técnica y uniforma consumo de listados.

### VI. Frontend Angular para Flujos CRUD con UI
Cuando una feature incluya interfaz de usuario web para gestión CRUD, el frontend
MUST implementarse con Angular y consumir únicamente endpoints versionados del
backend. El frontend MUST validar campos requeridos en cliente sin reemplazar
validaciones del servidor y MUST cubrir flujos críticos con pruebas ejecutables
proporcionales (unitarias o integración de UI según alcance). Rationale: mantiene
uniformidad tecnológica en la capa web y reduce divergencia entre contratos de UI
y API.

## Restricciones Técnicas Obligatorias

- Runtime MUST usar Java 17.
- Framework backend MUST usar Spring Boot.
- Base de datos MUST usar PostgreSQL.
- Orquestación local de base de datos MUST usar Docker.
- Documentación API MUST publicarse vía OpenAPI/Swagger.
- Frontend web de features CRUD con UI MUST usar Angular.
- Nuevas rutas de API MUST incluir versión explícita (por ejemplo, `/api/v1`).
- Endpoints de listado MUST usar paginación de 5 por defecto, salvo excepción aprobada.
- Configuración sensible SHOULD venir de variables de entorno en despliegues reales.

## Flujo de Desarrollo y Puertas de Calidad

1. Toda especificación nueva MUST declarar impacto en seguridad, datos y contrato API.
2. Todo plan MUST pasar un Constitution Check antes de implementación.
3. Todo conjunto de tareas MUST incluir, cuando aplique: seguridad básica,
  conexión a PostgreSQL, configuración Docker, actualización Swagger,
  versionado de API, validación de paginación y trabajo de frontend Angular.
4. Ningún cambio se da por terminado sin evidencia de ejecución local o CI de pruebas.
5. Pull Requests MUST listar cómo cumplen cada principio constitucional afectado.

## Governance

- Esta constitución prevalece sobre guías operativas del repositorio cuando exista
  conflicto.
- Enmiendas MUST documentar: motivo, impacto, plan de migración y tipo de cambio de
  versión semántica.
- Política de versionado constitucional:
  - MAJOR: eliminación o redefinición incompatible de principios/gobernanza.
  - MINOR: adición de principios/secciones o ampliaciones normativas sustanciales.
  - PATCH: aclaraciones editoriales sin cambio de obligación material.
- Revisión de cumplimiento MUST realizarse en cada PR y en planificación de features.
- Si falta información crítica (fechas, rutas de comandos, etc.), se MUST registrar
  como TODO(...) en este documento hasta su cierre.

**Version**: 1.3.0 | **Ratified**: 2026-02-25 | **Last Amended**: 2026-03-11
