package com.dsw01.practica02.dto;

public class DepartamentoResponse {

    private Long clave;
    private String nombre;

    public DepartamentoResponse() {
    }

    public DepartamentoResponse(Long clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
    }

    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
