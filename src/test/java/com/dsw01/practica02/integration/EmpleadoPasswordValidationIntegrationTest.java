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
class EmpleadoPasswordValidationIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Long departamentoClave;

    @BeforeEach
    void setupUser() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();
        Departamento departamento = new Departamento();
        departamento.setNombre("Validate");
        departamento = departamentoRepository.save(departamento);
        departamentoClave = departamento.getClave();

        Empleado auth = new Empleado();
        auth.setNombre("validator");
        auth.setEmail("validator@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(departamento);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldRejectInvalidFr007Passwords() throws Exception {
        String[] invalidPasswords = {
                "",
                "   ",
                "abcdef1!",
                "ABCDEF1!",
                "Abcdefgh!",
                "Abcdefg1"
        };

        for (String password : invalidPasswords) {
            Map<String, Object> payload = Map.of(
                    "nombre", "user-" + password.hashCode(),
                    "email", "user-" + Math.abs(password.hashCode()) + "@example.com",
                    "direccion", "d",
                    "telefono", "t",
                        "contrasena", password,
                        "departamentoClave", departamentoClave
            );

            mockMvc.perform(post("/api/v1/empleados")
                            .with(httpBasic("validator@example.com", "Abcdef1!"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(payload)))
                    .andExpect(status().isBadRequest());
        }
    }
}
