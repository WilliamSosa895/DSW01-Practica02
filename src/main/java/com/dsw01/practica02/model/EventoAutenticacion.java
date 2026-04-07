package com.dsw01.practica02.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "eventos_autenticacion")
public class EventoAutenticacion {

    public enum Resultado {
        EXITO,
        FALLO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreIntentado;

    @Column
    private Long empleadoClave;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Resultado resultado;

    @Column(nullable = false)
    private OffsetDateTime fechaHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreIntentado() {
        return nombreIntentado;
    }

    public void setNombreIntentado(String nombreIntentado) {
        this.nombreIntentado = nombreIntentado;
    }

    public Long getEmpleadoClave() {
        return empleadoClave;
    }

    public void setEmpleadoClave(Long empleadoClave) {
        this.empleadoClave = empleadoClave;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
