# Quickstart: CRUD De Departamentos Relacionados A Empleados

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
- Health: `GET http://localhost:8080/actuator/health`
- OpenAPI JSON: `GET http://localhost:8080/api-docs`
- Documentacion: `http://localhost:8080/swagger-ui.html`

## 4) Probar CRUD de departamentos
1. Crear departamento en ruta versionada `/api/v1/departamentos`.
2. Consultar detalle por clave y validar nombre.
3. Actualizar nombre del departamento.
4. Eliminar departamento sin empleados asociados.

## 5) Probar relacion empleado-departamento
1. Crear departamento base para asignacion.
2. Crear/actualizar empleado con `departamentoClave` valido.
3. Intentar asignar `departamentoClave` inexistente y validar rechazo.

## 6) Probar regla de eliminacion con dependencias
- Intentar eliminar departamento con empleados asociados y validar error funcional de conflicto.
- Validar contrato de error: `status=409`, `error=Conflict`, `message` y `path`.

## 7) Probar seguridad de endpoints
- Sin autenticacion: `GET /api/v1/departamentos` debe responder `401`.
- Con autenticacion valida: `GET /api/v1/departamentos?page=0&size=5` debe responder `200`.
- Sin autenticacion: `GET /actuator/health`, `GET /api-docs`, `GET /swagger-ui.html` deben estar permitidos.

## 8) Probar paginacion
- Solicitar listados de departamentos con `page=0&size=5`.
- Solicitar listados de empleados con `page=0&size=5`.
- Validar rechazo cuando `size` sea distinto de 5.

## 9) Validacion automatica
```bash
mvn test
mvn -Dtest=*IntegrationTest verify
```

## 10) Validacion sin Docker (fallback)
Si Docker/Testcontainers no esta disponible, validar al menos compilacion y unit tests:
```bash
mvn -DskipTests compile
mvn '-Dtest=EmpleadoResponseMappingTest,EmpleadoServicePasswordValidationTest,EmpleadoServiceDepartamentoValidationTest,DepartamentoServiceTest,EmpleadoAuthServiceTest' test
```
