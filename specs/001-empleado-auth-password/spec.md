# Feature Specification: Autenticacion Por Empleado

**Feature Branch**: `001-empleado-auth-password`  
**Created**: 2026-03-03  
**Status**: Draft  
**Input**: User description: "necesito que ahora la autenticacion se realice con la entidad empleado, mediante nombre y contraseña, por lo que debes agregar el campo contraseña a la entidad."

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Iniciar Sesion Con Empleado (Priority: P1)

Como empleado, quiero autenticarme con mi nombre y contraseña para acceder a los endpoints protegidos.

**Why this priority**: Sin autenticación por empleado no se cumple el objetivo principal del cambio.

**Independent Test**: Realizar una solicitud autenticada con credenciales válidas de empleado y confirmar acceso a un endpoint protegido.

**Acceptance Scenarios**:

1. **Given** que existe un empleado activo con nombre y contraseña válidos, **When** intenta autenticarse, **Then** el sistema permite acceso a los endpoints protegidos.
2. **Given** que el nombre o la contraseña son incorrectos, **When** intenta autenticarse, **Then** el sistema rechaza el acceso y devuelve respuesta de autenticación fallida.

---

### User Story 2 - Gestionar Password De Empleado (Priority: P2)

Como administrador funcional, quiero que la entidad empleado incluya contraseña para gestionar altas y cambios de credenciales.

**Why this priority**: La autenticación por empleado requiere que el dato contraseña exista y esté administrado durante el ciclo de vida del empleado.

**Independent Test**: Crear o actualizar un empleado con contraseña válida y verificar que el registro persiste cumpliendo reglas de validación.

**Acceptance Scenarios**:

1. **Given** que se registra o actualiza un empleado, **When** se envía una contraseña válida, **Then** el sistema guarda el empleado con su credencial asociada.
2. **Given** que se registra o actualiza un empleado, **When** la contraseña es nula, vacía, solo espacios o incumple la política de complejidad y longitud, **Then** el sistema rechaza la operación con validación de datos.

---

### User Story 3 - Proteger Datos Sensibles De Credenciales (Priority: P3)

Como responsable de seguridad, quiero que la contraseña no sea expuesta en respuestas funcionales para evitar fuga de credenciales.

**Why this priority**: Reduce riesgo de exposición accidental de información sensible en API y herramientas de prueba.

**Independent Test**: Consultar empleados creados y confirmar que las respuestas no incluyen el campo contraseña.

**Acceptance Scenarios**:

1. **Given** que existe un empleado con contraseña registrada, **When** se consulta el empleado por cualquier endpoint de lectura, **Then** la respuesta no expone la contraseña.

---

### Edge Cases

- Intento de autenticación con nombre inexistente.
- Intento de autenticación con contraseña vacía o nula.
- Intento de crear/actualizar empleado sin contraseña cuando la operación lo requiere.
- Intento de establecer contraseña con longitud menor a 8 o mayor a 64 caracteres.
- Intento de establecer contraseña sin al menos una letra mayúscula, una letra minúscula, un dígito y un carácter especial permitido.
- Intento de establecer contraseña con caracteres fuera del conjunto permitido por la política (letras, dígitos y símbolos ASCII imprimibles).
- Verificación de que respuestas de lectura no incluyen contraseña aun cuando el registro existe.
- Consumo de ruta no versionada para esta nueva funcionalidad de autenticación.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST autenticar a usuarios de endpoints protegidos usando credenciales de la entidad empleado (`nombre` + `contraseña`).
- **FR-002**: La entidad empleado MUST incluir el campo `contraseña` como dato obligatorio para autenticación.
- **FR-003**: El sistema MUST validar credenciales de empleado y rechazar accesos con nombre o contraseña inválidos.
- **FR-004**: El sistema MUST mantener acceso público solo a endpoints de salud y documentación declarados.
- **FR-005**: El sistema MUST exponer esta nueva capacidad bajo rutas versionadas de API (por ejemplo `/api/v1/...` o estrategia equivalente explícita).
- **FR-006**: Las operaciones de lectura de empleado MUST no devolver el campo `contraseña` en sus respuestas.
- **FR-007**: El sistema MUST permitir actualización de contraseña de empleado solo si `contraseña` cumple la política funcional: no nula, no vacía, no compuesta solo por espacios en blanco, longitud entre 8 y 64 caracteres, al menos una letra mayúscula, al menos una letra minúscula, al menos un dígito y al menos un carácter especial de símbolos ASCII imprimibles; cualquier incumplimiento MUST rechazar la operación con error de validación.
- **FR-008**: El sistema MUST registrar intentos fallidos de autenticación como eventos de acceso rechazado.
- **FR-009**: Los endpoints de listado relacionados con empleados MUST conservar paginación por bloques de 5 registros, salvo excepción aprobada en la especificación.
- **FR-010**: El contrato de la API MUST documentar el esquema de autenticación por empleado y los cambios de entidad asociados.

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa al usuario funcional del sistema. Atributos relevantes para esta feature: `clave`, `nombre`, `direccion`, `telefono`, `contraseña`, estado de activación.
- **EventoAutenticacion**: Representa un intento de acceso. Atributos relevantes: identificador de empleado (si existe), resultado del intento (éxito/fallo), fecha/hora.

## Assumptions

- La autenticación existente migra a credenciales almacenadas en empleado, sin requerir doble mecanismo paralelo para esta feature.
- La contraseña se considera dato sensible y su presentación queda restringida en respuestas de negocio.
- No se incluye recuperación de contraseña ni segundo factor en esta iteración.
- El campo `nombre` se utiliza como identificador de inicio de sesión y debe ser único dentro del alcance funcional.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% de autenticaciones con credenciales válidas de empleado permiten acceso a endpoints protegidos.
- **SC-002**: 100% de autenticaciones con credenciales inválidas son rechazadas.
- **SC-003**: 100% de respuestas de lectura de empleado omiten el campo `contraseña`.
- **SC-004**: En ejecución de pruebas automatizadas en entorno local o CI, sobre una muestra mínima de 100 solicitudes de autenticación consecutivas (mezcla de casos válidos e inválidos), al menos 95 solicitudes MUST completar resultado (éxito o rechazo) en menos de 2 segundos, midiendo el tiempo transcurrido desde el envío de la solicitud hasta la recepción de la respuesta dentro del propio test.
- **SC-005**: 100% de endpoints de esta nueva funcionalidad están disponibles bajo rutas versionadas de API.
