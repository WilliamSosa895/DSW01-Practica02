# Quickstart: CRUD de Empleados

## Prerrequisitos

- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar infraestructura

```bash
docker compose up -d postgres
```

## 2) Ejecutar la API

```bash
mvn spring-boot:run
```

## 3) Verificar endpoints públicos y protegidos

- Público: `GET http://localhost:8080/actuator/health`
- Público por especificación de feature: `http://localhost:8080/api-docs` y `http://localhost:8080/swagger-ui.html`
- Protegido: todo ` /api/** ` con Basic Auth (`admin` / `admin 123`)

## 4) Ejecutar flujo funcional mínimo

1. **Crear**: `POST /api/empleados` con `nombre`, `direccion`, `telefono`.
2. **Listar**: `GET /api/empleados?page=0&size=5`.
3. **Consultar**: `GET /api/empleados/{clave}` con la clave creada.
4. **Actualizar**: `PUT /api/empleados/{clave}` modificando campos editables.
5. **Eliminar**: `DELETE /api/empleados/{clave}`.
6. **Comprobar borrado**: `GET /api/empleados/{clave}` devuelve `404`.

## 5) Verificar validaciones

- Campos vacíos o con longitud > 100 en altas/actualizaciones devuelven `400`.
- En creación, cualquier `clave` enviada por cliente se ignora.
- Paginación inválida (`page < 0` o `size != 5`) devuelve `400`.
- Página fuera de rango devuelve `200` con `content` vacío y metadatos consistentes.

## 6) Ejecutar validación automática

```bash
mvn test
mvn -Dtest=*IntegrationTest verify
```
