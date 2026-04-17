package com.dsw01.practica02.service.validation;

import org.springframework.stereotype.Component;

@Component
public class PasswordPolicyValidator {

    public void validate(String contrasena) {
        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("contrasena es obligatoria");
        }
        if (contrasena.length() < 8 || contrasena.length() > 64) {
            throw new IllegalArgumentException("contrasena debe tener longitud entre 8 y 64");
        }
        if (!contrasena.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("contrasena debe incluir al menos una mayuscula");
        }
        if (!contrasena.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("contrasena debe incluir al menos una minuscula");
        }
        if (!contrasena.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("contrasena debe incluir al menos un digito");
        }
        if (!contrasena.matches(".*[^A-Za-z0-9].*")) {
            throw new IllegalArgumentException("contrasena debe incluir al menos un caracter especial");
        }
        if (!contrasena.matches("^[\\x20-\\x7E]+$")) {
            throw new IllegalArgumentException("contrasena solo permite simbolos ASCII imprimibles");
        }
    }
}
