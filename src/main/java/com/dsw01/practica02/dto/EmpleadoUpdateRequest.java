package com.dsw01.practica02.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.dsw01.practica02.model.RolEmpleado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EmpleadoUpdateRequest {

    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre no debe superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "email es obligatorio")
    @Email(message = "email debe tener formato valido")
    @Size(min = 5, max = 254, message = "email debe tener longitud entre 5 y 254")
    private String email;

    @NotBlank(message = "direccion es obligatoria")
    @Size(max = 100, message = "direccion no debe superar 100 caracteres")
    private String direccion;

    @NotBlank(message = "telefono es obligatorio")
    @Size(max = 100, message = "telefono no debe superar 100 caracteres")
    private String telefono;

    @NotBlank(message = "contrasena es obligatoria")
    @Size(min = 8, max = 64, message = "contrasena debe tener longitud entre 8 y 64")
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private String contrasena;

    @NotNull(message = "departamentoClave es obligatorio")
    private Long departamentoClave;

    @NotNull(message = "rol es obligatorio")
    private RolEmpleado rol;

    @NotNull(message = "version es obligatoria")
    private Long version;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Long getDepartamentoClave() {
        return departamentoClave;
    }

    public void setDepartamentoClave(Long departamentoClave) {
        this.departamentoClave = departamentoClave;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
