package com.dsw01.practica02.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartamentoUpdateRequest {

    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre no debe superar 100 caracteres")
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
