package com.dsw01.practica02.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departamentos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_departamento_nombre", columnNames = "nombre")
})
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clave;

    @Column(nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "departamento")
    private List<Empleado> empleados = new ArrayList<>();

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

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}



