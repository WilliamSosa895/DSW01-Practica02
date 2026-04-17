package com.dsw01.practica02.integration;

import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.model.EventoAutenticacion;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.repository.EventoAutenticacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoAuthFailureAuditIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EventoAutenticacionRepository eventoAutenticacionRepository;

    @BeforeEach
    void setupUser() {
        eventoAutenticacionRepository.deleteAll();
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();
        Departamento departamento = new Departamento();
        departamento.setNombre("Audit");
        departamento = departamentoRepository.save(departamento);
        Empleado auth = new Empleado();
        auth.setNombre("audituser");
        auth.setEmail("audituser@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(departamento);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldPersistFailureAuditEventOnInvalidLogin() throws Exception {
        mockMvc.perform(get("/api/v1/empleados").with(httpBasic("audituser@example.com", "BadPass1!")).param("size", "5"))
                .andExpect(status().isUnauthorized());

        assertThat(eventoAutenticacionRepository.findAll())
                .extracting(EventoAutenticacion::getResultado)
                .contains(EventoAutenticacion.Resultado.FALLO);
    }
}
