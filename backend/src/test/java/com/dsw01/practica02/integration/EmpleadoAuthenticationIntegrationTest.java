package com.dsw01.practica02.integration;

import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoAuthenticationIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void setupUser() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();
        Departamento departamento = new Departamento();
        departamento.setNombre("Auth");
        departamento = departamentoRepository.save(departamento);
        Empleado auth = new Empleado();
        auth.setNombre("authuser");
        auth.setEmail("authuser@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(departamento);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldAllowAccessWithValidEmployeeCredentials() throws Exception {
        mockMvc.perform(get("/api/v1/empleados").with(httpBasic("authuser@example.com", "Abcdef1!")).param("size", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectAccessWithInvalidCredentials() throws Exception {
        mockMvc.perform(get("/api/v1/empleados").with(httpBasic("authuser@example.com", "wrong")).param("size", "5"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldExposeOnlyPublicHealthAndDocsWithoutAuth() throws Exception {
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
        mockMvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/api/v1/empleados").param("size", "5")).andExpect(status().isUnauthorized());
    }
}
