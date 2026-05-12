package com.dsw01.practica02.unit;

import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.model.EventoAutenticacion;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.repository.EventoAutenticacionRepository;
import com.dsw01.practica02.service.EmpleadoAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoAuthServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private EventoAutenticacionRepository eventoAutenticacionRepository;

    @InjectMocks
    private EmpleadoAuthService empleadoAuthService;

    @Test
    void shouldRegisterSuccessEvent() {
        Empleado empleado = new Empleado();
        empleado.setClave(9L);
        when(empleadoRepository.findByNombreAndActivoTrue("ana")).thenReturn(Optional.of(empleado));

        empleadoAuthService.registrarExito("ana");

        ArgumentCaptor<EventoAutenticacion> captor = ArgumentCaptor.forClass(EventoAutenticacion.class);
        verify(eventoAutenticacionRepository).save(captor.capture());
        assertEquals(EventoAutenticacion.Resultado.EXITO, captor.getValue().getResultado());
        assertEquals(9L, captor.getValue().getEmpleadoClave());
    }

    @Test
    void shouldRegisterFailureEventWhenUserMissing() {
        when(empleadoRepository.findByNombre("ghost")).thenReturn(Optional.empty());

        empleadoAuthService.registrarFallo("ghost");

        ArgumentCaptor<EventoAutenticacion> captor = ArgumentCaptor.forClass(EventoAutenticacion.class);
        verify(eventoAutenticacionRepository).save(captor.capture());
        assertEquals(EventoAutenticacion.Resultado.FALLO, captor.getValue().getResultado());
        assertEquals(null, captor.getValue().getEmpleadoClave());
    }
}
