package com.dsw01.practica02.unit;

import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.exception.ResourceNotFoundException;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceWriteTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void shouldUpdateEmpleado() {
        Empleado existing = new Empleado();
        existing.setClave(1L);
        existing.setNombre("Old");
        existing.setDireccion("Old St");
        existing.setTelefono("111");

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("New");
        request.setDireccion("New St");
        request.setTelefono("222");

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(empleadoRepository.save(existing)).thenReturn(existing);

        EmpleadoResponse response = empleadoService.update(1L, request);

        assertEquals("New", response.getNombre());
        assertEquals("New St", response.getDireccion());
    }

    @Test
    void shouldDeleteEmpleado() {
        Empleado existing = new Empleado();
        existing.setClave(1L);

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(existing));

        empleadoService.delete(1L);

        verify(empleadoRepository).delete(existing);
    }

    @Test
    void shouldThrowWhenUpdatingMissingEmpleado() {
        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("A");
        request.setDireccion("B");
        request.setTelefono("C");

        when(empleadoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.update(10L, request));
    }
}
