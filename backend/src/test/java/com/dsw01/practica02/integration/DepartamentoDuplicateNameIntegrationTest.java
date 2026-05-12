package com.dsw01.practica02.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DepartamentoDuplicateNameIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void setupUser() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Departamento authDep = new Departamento();
        authDep.setNombre("AuthDep");
        authDep = departamentoRepository.save(authDep);

        Empleado auth = new Empleado();
        auth.setNombre("depdup");
        auth.setEmail("depdup@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(authDep);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldReturnConflictWhenDepartmentNameAlreadyExists() throws Exception {
        String body = objectMapper.writeValueAsString(Map.of("nombre", "RRHH"));

        mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic("depdup@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic("depdup@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }
}
