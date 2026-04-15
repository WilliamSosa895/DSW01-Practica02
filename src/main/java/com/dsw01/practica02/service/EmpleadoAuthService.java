package com.dsw01.practica02.service;

import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.model.EventoAutenticacion;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.repository.EventoAutenticacionRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EmpleadoAuthService {

    private final EmpleadoRepository empleadoRepository;
    private final EventoAutenticacionRepository eventoAutenticacionRepository;

    public EmpleadoAuthService(EmpleadoRepository empleadoRepository,
                               EventoAutenticacionRepository eventoAutenticacionRepository) {
        this.empleadoRepository = empleadoRepository;
        this.eventoAutenticacionRepository = eventoAutenticacionRepository;
    }

    public void registrarExito(String usernameIntentado) {
        Empleado empleado = empleadoRepository.findByEmailIgnoreCaseAndActivoTrue(usernameIntentado)
                .orElseGet(() -> empleadoRepository.findByNombreAndActivoTrue(usernameIntentado).orElse(null));
        guardarEvento(usernameIntentado,
                empleado == null ? null : empleado.getClave(),
                EventoAutenticacion.Resultado.EXITO);
    }

    public void registrarFallo(String usernameIntentado) {
        Empleado empleado = empleadoRepository.findByEmailIgnoreCase(usernameIntentado)
                .orElseGet(() -> empleadoRepository.findByNombre(usernameIntentado).orElse(null));
        guardarEvento(usernameIntentado,
                empleado == null ? null : empleado.getClave(),
                EventoAutenticacion.Resultado.FALLO);
    }

    private void guardarEvento(String nombreIntentado, Long empleadoClave, EventoAutenticacion.Resultado resultado) {
        EventoAutenticacion evento = new EventoAutenticacion();
        evento.setNombreIntentado(nombreIntentado == null ? "UNKNOWN" : nombreIntentado);
        evento.setEmpleadoClave(empleadoClave);
        evento.setResultado(resultado);
        evento.setFechaHora(OffsetDateTime.now());
        eventoAutenticacionRepository.save(evento);
    }
}
