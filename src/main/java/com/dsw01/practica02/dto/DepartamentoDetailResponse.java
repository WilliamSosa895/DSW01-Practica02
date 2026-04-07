package com.dsw01.practica02.dto;

import java.util.List;

public class DepartamentoDetailResponse {

    private Long clave;
    private String nombre;
    private List<EmpleadoResumenResponse> empleados;

    public DepartamentoDetailResponse() {
    }

    public DepartamentoDetailResponse(Long clave, String nombre, List<EmpleadoResumenResponse> empleados) {
        this.clave = clave;
        this.nombre = nombre;
        this.empleados = empleados;
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

    public List<EmpleadoResumenResponse> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoResumenResponse> empleados) {
        this.empleados = empleados;
    }
}
