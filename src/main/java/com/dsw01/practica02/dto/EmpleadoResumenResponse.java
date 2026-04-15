package com.dsw01.practica02.dto;

import com.dsw01.practica02.model.RolEmpleado;

public class EmpleadoResumenResponse {

    private Long clave;
    private String nombre;
    private String direccion;
    private String telefono;
    private RolEmpleado rol;

    public EmpleadoResumenResponse() {
    }

    public EmpleadoResumenResponse(Long clave, String nombre, String direccion, String telefono) {
        this.clave = clave;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public EmpleadoResumenResponse(Long clave, String nombre, String direccion, String telefono, RolEmpleado rol) {
        this.clave = clave;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rol = rol;
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

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }
}
