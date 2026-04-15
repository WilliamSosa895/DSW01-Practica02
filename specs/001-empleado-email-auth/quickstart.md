# Quickstart: Email En Empleado Para Autenticacion

## Prerrequisitos
- Docker y Docker Compose
- Java 17
- Maven 3.9+

## 1) Levantar PostgreSQL
```bash
docker compose up -d postgres
```

## 2) Ejecutar API
```bash
mvn spring-boot:run
```

## 3) Verificar endpoints publicos
- `GET http://localhost:8080/actuator/health`
- `GET http://localhost:8080/api-docs`
- `GET http://localhost:8080/swagger-ui.html`

## 4) Semilla minima para autenticacion por email (si aplica)
Crear un empleado activo con email valido para pruebas de Basic Auth.

Ejemplo de credenciales de prueba:
- Email: `admin@example.com`
- Contrasena: `admin123`

## 5) Probar alta de empleado con email obligatorio
- `POST /api/v1/empleados` con `email` valido y `departamentoClave` existente.
- Validar exito y presencia de `email` en respuesta.

## 6) Probar validaciones de email
- Alta/actualizacion sin `email` -> `400`.
- Alta/actualizacion con formato invalido (`"correo-invalido"`) -> `400`.
- Alta/actualizacion con email duplicado (ignorando mayusculas/minusculas) -> `409` con `ErrorResponse`.

## 7) Probar autenticacion por email
- En Postman, usar Basic Auth con `username = email` y `password = contrasena`.
- `GET /api/v1/departamentos?page=0&size=5` con credenciales validas -> `200`.
- Mismas pruebas con email inexistente o contrasena incorrecta -> `401`.
- Usuario inactivo con credenciales correctas -> `401`.

## 8) Probar compatibilidad de contrato
- Revisar `GET /api-docs` y confirmar campo `email` en esquemas de empleados.
- Verificar rutas versionadas `/api/v1/...` para endpoints modificados.

## 9) Validacion automatica
```bash
mvn test
mvn -Dtest=*IntegrationTest verify
```

## 10) Validacion sin Docker (fallback)
Si Docker/Testcontainers no esta disponible:
```bash
mvn -DskipTests compile
mvn '-Dtest=EmpleadoServicePasswordValidationTest,EmpleadoServiceDepartamentoValidationTest,EmpleadoAuthServiceTest' test
```
