package com.dsw01.practica02.model;

public enum RolEmpleado {
    MASTER,
    STANDARD;

    public String asSpringRole() {
        return name();
    }
}
