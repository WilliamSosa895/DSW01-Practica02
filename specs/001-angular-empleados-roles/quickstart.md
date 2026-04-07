# Quickstart - Frontend Empleados con Roles

## Prerrequisitos
- Java 17
- Maven
- Docker y Docker Compose
- Node.js 20+ y Angular CLI (para fase de implementacion frontend)

## 1. Levantar stack completo (Postgres + Backend + Frontend)
1. Ejecutar:
   - docker compose up -d
2. Verificar contenedores:
   - `dsw01_postgres` en `localhost:55432`
   - `dsw01_backend` en `localhost:8080`
   - `dsw01_frontend` en `localhost:4200`
3. Verificar salud backend:
   - GET `http://localhost:8080/actuator/health`
4. Verificar frontend:
   - GET `http://localhost:4200`
5. Verificar proxy frontend->backend:
   - GET `http://localhost:4200/api/v1/empleados/contexto` retorna 401 si no hay credenciales.

## 2. Verificar autenticacion Basic
1. Probar acceso sin credenciales a /api/v1/empleados:
   - Debe responder 401.
2. Probar acceso con credenciales validas:
   - Debe responder 200 en listado.

## 3. Validar flujo UI US1 (Login)
1. Abrir `http://localhost:4200/login`.
2. Iniciar sesion con usuario valido (email + contrasena).
3. Verificar redireccion a `/empleados`.
4. Cerrar sesion desde topbar y validar retorno a login.

## 4. Validar flujo UI US2 (MASTER)
1. Iniciar sesion con usuario `MASTER`.
2. Verificar boton de alta en `/empleados`.
3. Crear empleado desde `/empleados/nuevo`.
4. Editar empleado desde `/empleados/{clave}/editar`.
5. Eliminar empleado y verificar refresco del listado.

## 5. Validar flujo UI US3 (STANDARD)
1. Iniciar sesion con usuario `STANDARD`.
2. Verificar acceso a listado y detalle.
3. Confirmar que no se muestran botones de crear/editar/eliminar.
4. Si se fuerza una escritura, validar mensaje de permisos insuficientes (403).

## 6. Concurrencia y errores
1. Simular edicion concurrente del mismo empleado con version desfasada.
2. Verificar respuesta 409 con `CONCURRENCY_CONFLICT`.
3. Verificar que la UI muestra aviso y recarga para reintento.

## 7. Validaciones de build
1. Backend:
   - `mvn -DskipTests compile`
2. Frontend:
   - `cd frontend && npm run build`
