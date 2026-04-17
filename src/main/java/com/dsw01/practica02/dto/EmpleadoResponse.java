package com.dsw01.practica02.dto;

import com.dsw01.practica02.model.RolEmpleado;

public class EmpleadoResponse {

    private Long clave;
    private String nombre;
    private String email;
    private String direccion;
    private String telefono;
    private RolEmpleado rol;
    private Long version;
    private DepartamentoResponse departamento;

    public EmpleadoResponse() {
    }

    public EmpleadoResponse(Long clave, String nombre, String email, String direccion, String telefono, DepartamentoResponse departamento) {
        this.clave = clave;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.departamento = departamento;
    }

    public EmpleadoResponse(Long clave,
                            String nombre,
                            String email,
                            String direccion,
                            String telefono,
                            RolEmpleado rol,
                            Long version,
                            DepartamentoResponse departamento) {
        this.clave = clave;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rol = rol;
        this.version = version;
        this.departamento = departamento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public DepartamentoResponse getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoResponse departamento) {
        this.departamento = departamento;
    }
}
