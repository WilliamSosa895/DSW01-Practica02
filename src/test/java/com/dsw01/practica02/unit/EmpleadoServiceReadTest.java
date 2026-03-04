package com.dsw01.practica02.unit;

import com.dsw01.practica02.dto.EmpleadoPageResponse;
import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.exception.ResourceNotFoundException;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceReadTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void shouldReturnEmpleadoByClave() {
        Empleado empleado = new Empleado();
        empleado.setClave(1L);
        empleado.setNombre("Ana");
        empleado.setDireccion("Calle A");
        empleado.setTelefono("999");

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        EmpleadoResponse response = empleadoService.getByClave(1L);

        assertEquals(1L, response.getClave());
        assertEquals("Ana", response.getNombre());
    }

    @Test
    void shouldThrowNotFoundWhenClaveDoesNotExist() {
        when(empleadoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> empleadoService.getByClave(99L));
    }

    @Test
    void shouldReturnPagedEmployees() {
        Empleado empleado = new Empleado();
        empleado.setClave(1L);
        empleado.setNombre("Ana");
        empleado.setDireccion("Calle A");
        empleado.setTelefono("999");

        Page<Empleado> page = new PageImpl<>(List.of(empleado), PageRequest.of(0, 5), 1);
        when(empleadoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        EmpleadoPageResponse response = empleadoService.list(0, 5);

        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getPage());
        assertEquals(5, response.getSize());
    }

    @Test
    void shouldRejectInvalidPageOrSize() {
        assertThrows(IllegalArgumentException.class, () -> empleadoService.list(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> empleadoService.list(0, 10));
    }

    @Test
    void shouldReturnEmptyContentWhenPageOutOfRange() {
        Page<Empleado> page = new PageImpl<>(List.of(), PageRequest.of(5, 5), 2);
        when(empleadoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        EmpleadoPageResponse response = empleadoService.list(5, 5);

        assertEquals(0, response.getContent().size());
    }
}
