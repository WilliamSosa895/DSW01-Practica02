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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoDepartamentoAssignmentIntegrationTest extends AbstractIntegrationTest {

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
    void setup() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Departamento target = new Departamento();
        target.setNombre("Target");
        target = departamentoRepository.save(target);
        departamentoClave = target.getClave();

        Empleado auth = new Empleado();
        auth.setNombre("depassign");
        auth.setEmail("depassign@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(target);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldCreateEmployeeAssignedToExistingDepartment() throws Exception {
        Map<String, Object> payload = Map.of(
                "nombre", "nuevo",
            "email", "nuevo@example.com",
                "direccion", "dir",
                "telefono", "tel",
                "contrasena", "Abcdef1!",
                "departamentoClave", departamentoClave
        );

        mockMvc.perform(post("/api/v1/empleados")
                        .with(httpBasic("depassign@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.departamento.clave").value(departamentoClave));
    }
}
