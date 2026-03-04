<!--
Sync Impact Report
- Version change: N/A (template) → 1.0.0
- Modified principles:
  - Template Principle 1 → I. Spring Boot + Java 17 Obligatorio
  - Template Principle 2 → II. Seguridad con Autenticación Básica
  - Template Principle 3 → III. Persistencia PostgreSQL con Docker
  - Template Principle 4 → IV. API-First con OpenAPI/Swagger
  - Template Principle 5 → V. Calidad Ejecutable y Entrega Trazable
- Added sections:
  - Restricciones Técnicas Obligatorias
  - Flujo de Desarrollo y Puertas de Calidad
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

### IV. API-First con OpenAPI/Swagger
Toda capacidad expuesta por HTTP MUST quedar documentada en OpenAPI y ser
navegable mediante Swagger UI durante desarrollo. Ningún endpoint nuevo se
considera completo sin contrato y documentación accesible. Rationale: reduce
ambigüedad entre desarrollo, pruebas e integración.

### V. Calidad Ejecutable y Entrega Trazable
Cada cambio funcional MUST incluir validación ejecutable proporcional al riesgo
(mínimo pruebas unitarias de lógica y pruebas de integración de endpoints críticos),
y MUST actualizar su documentación técnica asociada (contratos, configuración,
pasos de ejecución). Rationale: evita regresiones y mantiene trazabilidad técnica.

## Restricciones Técnicas Obligatorias

- Runtime MUST usar Java 17.
- Framework backend MUST usar Spring Boot.
- Base de datos MUST usar PostgreSQL.
- Orquestación local de base de datos MUST usar Docker.
- Documentación API MUST publicarse vía OpenAPI/Swagger.
- Configuración sensible SHOULD venir de variables de entorno en despliegues reales.

## Flujo de Desarrollo y Puertas de Calidad

1. Toda especificación nueva MUST declarar impacto en seguridad, datos y contrato API.
2. Todo plan MUST pasar un Constitution Check antes de implementación.
3. Todo conjunto de tareas MUST incluir, cuando aplique: seguridad básica,
   conexión a PostgreSQL, configuración Docker y actualización Swagger.
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

**Version**: 1.1.0 | **Ratified**: 2026-02-25 | **Last Amended**: 2026-03-03
