package com.dsw01.practica02.unit;

import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.dto.DepartamentoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EmpleadoResponseMappingTest {

    @Test
    void shouldExposeOnlySafeFields() {
        EmpleadoResponse response = new EmpleadoResponse(10L, "Ana", "ana@example.com", "Dir", "Tel", new DepartamentoResponse(1L, "IT"));

        assertEquals(10L, response.getClave());
        assertEquals("Ana", response.getNombre());
        assertEquals("ana@example.com", response.getEmail());
        assertEquals("Dir", response.getDireccion());
        assertEquals("Tel", response.getTelefono());
        assertEquals(1L, response.getDepartamento().getClave());
        assertEquals("IT", response.getDepartamento().getNombre());

        // Guardrail: response DTO must not include password-like accessor.
        boolean hasPasswordAccessor = java.util.Arrays.stream(EmpleadoResponse.class.getMethods())
                .anyMatch(m -> m.getName().toLowerCase().contains("contrasena") || m.getName().toLowerCase().contains("password"));
        assertFalse(hasPasswordAccessor);
    }
}
