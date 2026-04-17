# Quickstart: Autenticacion Por Empleado

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
- Documentacion: `http://localhost:8080/swagger-ui.html`

## 4) Probar autenticacion por empleado
1. Crear/actualizar empleado con `nombre` y `contrasena` validos (ruta versionada `/api/v1/...`).
2. Consumir endpoint protegido usando Basic Auth con `nombre` y `contrasena` del empleado.
3. Verificar rechazo con credenciales invalidas.

## 5) Probar seguridad de respuesta
- Consultar empleado por ruta de lectura versionada y verificar que `contrasena` no aparezca en la respuesta.

## 6) Probar politica FR-007
- Casos rechazados (`400`): `null`, vacia, solo espacios, longitud menor a 8, mayor a 64, sin mayuscula, sin minuscula, sin digito, sin caracter especial.
- Caso aceptado: `contrasena` con longitud 8..64 y complejidad completa.

## 7) Probar paginacion
- Solicitar listado en ruta versionada con `page=0&size=5`.
- Confirmar metadatos (`page`, `size`, `totalElements`, `totalPages`) y contenido por bloques de 5.

## 8) Probar SC-004
- Ejecutar prueba de rendimiento de autenticacion con 100 solicitudes consecutivas mixtas.
- Validar que al menos 95 respuestas completen en menos de 2 segundos.

## 9) Validacion automatica
```bash
mvn test
mvn -Dtest=*IntegrationTest verify
```

## 10) Evidencia de ejecucion
- Fecha de validacion: 2026-03-04
- Comando ejecutado: `mvn test -q`
- Resultado: `EXIT:0`
- Cobertura validada: pruebas unitarias, integracion, contrato OpenAPI y SC-004.
