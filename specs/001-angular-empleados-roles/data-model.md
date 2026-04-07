# Data Model - Frontend Empleados con Roles

## Entidad: Empleado
- Descripcion: Usuario de negocio autenticable que puede consultar o administrar empleados segun rol.
- Campos clave:
  - clave: Long, identificador unico.
  - nombre: String, obligatorio, unico funcional existente.
  - email: String, obligatorio, unico, usado como username de autenticacion.
  - direccion: String, obligatorio.
  - telefono: String, obligatorio.
  - contrasena: String, obligatoria, sujeta a politica de password.
  - activo: boolean, obligatorio.
  - rol: Enum (MASTER, STANDARD), obligatorio para autorizacion funcional.
  - departamentoClave: Long, obligatorio (FK a Departamento).
- Reglas de validacion:
  - email unico case-insensitive.
  - contrasena valida segun politica de complejidad vigente.
  - no exponer contrasena en respuestas de lectura.

## Entidad: Departamento
- Descripcion: Agrupador organizacional de empleados.
- Campos clave:
  - clave: Long, identificador.
  - nombre: String, obligatorio, unico.
- Relacion:
  - 1 Departamento a N Empleados.

## Entidad: SesionAutenticada (modelo de aplicacion)
- Descripcion: Estado de cliente Angular para acceso autenticado con Basic Auth.
- Campos clave:
  - username: email o usuario tecnico.
  - authenticated: boolean.
  - rol: MASTER o STANDARD.
- Reglas:
  - se invalida en 401 y logout.
  - habilita/deshabilita acciones CRUD en UI.

## Estados y Transiciones
- No autenticado -> Autenticado:
  - Trigger: login exitoso.
- Autenticado -> No autenticado:
  - Trigger: logout voluntario, 401 recibido o credenciales invalidas.
- Empleado STANDARD intento escritura -> Denegado:
  - Trigger: accion create/update/delete.
  - Resultado: UI bloquea y API responde 403 si se fuerza peticion.
