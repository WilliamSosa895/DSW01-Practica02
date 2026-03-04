package com.dsw01.practica02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmpleadoUpdateRequest {

    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre no debe superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "direccion es obligatoria")
    @Size(max = 100, message = "direccion no debe superar 100 caracteres")
    private String direccion;

    @NotBlank(message = "telefono es obligatorio")
    @Size(max = 100, message = "telefono no debe superar 100 caracteres")
    private String telefono;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
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
}
