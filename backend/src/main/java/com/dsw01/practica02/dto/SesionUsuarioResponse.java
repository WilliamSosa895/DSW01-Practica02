package com.dsw01.practica02.dto;

import com.dsw01.practica02.model.RolEmpleado;

public class SesionUsuarioResponse {

    private String username;
    private RolEmpleado rol;
    private boolean authenticated;

    public SesionUsuarioResponse() {
    }

    public SesionUsuarioResponse(String username, RolEmpleado rol, boolean authenticated) {
        this.username = username;
        this.rol = rol;
        this.authenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
