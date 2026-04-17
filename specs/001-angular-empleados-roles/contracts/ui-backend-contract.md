# UI-Backend Contract - Empleados con Roles

## Alcance
Contrato funcional entre frontend Angular y API versionada para login, consulta y CRUD de empleados con control por rol.

## Autenticacion
- Mecanismo: HTTP Basic Auth en cada request protegida.
- Endpoint publico permitido por seguridad global:
  - /actuator/health
  - /api-docs, /api-docs/**
  - /swagger-ui.html, /swagger-ui/**
- Regla de UI:
  - Si respuesta es 401, invalidar sesion local y redirigir a login.

## Formato minimo de error (401 y 403)
- code: identificador estable del tipo de error.
- message: mensaje legible para usuario final.
- action: accion sugerida para resolver o continuar.
- timestamp: campo opcional en formato de fecha/hora del servidor.

### Codigos de error esperados
- AUTH_UNAUTHORIZED: credenciales invalidas o ausentes (401).
- AUTH_FORBIDDEN: usuario autenticado sin permisos para la accion (403).
- EMAIL_DUPLICATE: conflicto por email ya registrado (409).
- CONCURRENCY_CONFLICT: conflicto de concurrencia por version desfasada (409).

## Endpoint de contexto autenticado

### GET /api/v1/empleados/contexto
- Descripcion: Retorna contexto del usuario autenticado para habilitar permisos en UI.
- Respuestas esperadas:
  - 200: SesionUsuarioResponse (usuario autenticado y rol efectivo).
  - 401: no autenticado (con formato minimo de error).
- Permisos:
  - MASTER: permitido
  - STANDARD: permitido

## Endpoints de Empleados
Base path: /api/v1/empleados

### 1. Listar empleados
- Metodo: GET /
- Query params:
  - page (opcional, default 0)
  - size (opcional, debe ser 5)
- Respuestas esperadas:
  - 200: EmpleadoPageResponse
  - 400: parametro invalido (page < 0 o size != 5)
  - 401: no autenticado
- Permisos:
  - MASTER: permitido
  - STANDARD: permitido

### 2. Consultar empleado por clave
- Metodo: GET /{clave}
- Respuestas esperadas:
  - 200: EmpleadoResponse
  - 404: no encontrado
  - 401: no autenticado
- Permisos:
  - MASTER: permitido
  - STANDARD: permitido

### 3. Crear empleado
- Metodo: POST /
- Body: EmpleadoCreateRequest
- Respuestas esperadas:
  - 201: EmpleadoResponse
  - 400: validacion
  - 401: no autenticado
  - 403: sin permiso
  - 409 (code=EMAIL_DUPLICATE): email duplicado
- Permisos:
  - MASTER: permitido
  - STANDARD: prohibido

### 4. Actualizar empleado
- Metodo: PUT /{clave}
- Body: EmpleadoUpdateRequest
- Respuestas esperadas:
  - 200: EmpleadoResponse
  - 400: validacion
  - 401: no autenticado
  - 403: sin permiso
  - 404: no encontrado
  - 409 (code=EMAIL_DUPLICATE): email duplicado
  - 409 (code=CONCURRENCY_CONFLICT): conflicto de concurrencia (version desfasada; recargar y reintentar)
- Permisos:
  - MASTER: permitido
  - STANDARD: prohibido

### 5. Eliminar empleado
- Metodo: DELETE /{clave}
- Respuestas esperadas:
  - 204: eliminado
  - 401: no autenticado
  - 403: sin permiso
  - 404: no encontrado
- Permisos:
  - MASTER: permitido
  - STANDARD: prohibido

## Reglas de datos para UI
- Nunca mostrar ni transportar contrasena en respuestas de lectura.
- Formularios de alta/edicion deben validar requeridos y formato antes de enviar.
- El listado debe operar con bloque fijo de 5 elementos.

## Pendientes de implementacion backend para cumplir contrato
- Introducir rol de negocio persistente en Empleado (MASTER/STANDARD).
- Aplicar autorizacion por rol en endpoints de escritura (POST/PUT/DELETE).
- Exponer rol efectivo del usuario autenticado para habilitar UI por permisos.
- Estandarizar payload de error 401/403 con code, message, action y timestamp opcional.
- Detectar y responder conflicto de concurrencia en actualizacion de empleados.
