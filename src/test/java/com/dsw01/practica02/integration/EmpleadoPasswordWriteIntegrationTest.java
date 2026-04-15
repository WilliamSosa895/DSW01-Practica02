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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoPasswordWriteIntegrationTest extends AbstractIntegrationTest {

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
                departamento.setNombre("Write");
                departamento = departamentoRepository.save(departamento);
                departamentoClave = departamento.getClave();

        Empleado auth = new Empleado();
        auth.setNombre("writer");
        auth.setEmail("writer@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
                auth.setDepartamento(departamento);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldCreateAndUpdateWithValidPassword() throws Exception {
        Map<String, Object> payload = Map.of(
                "nombre", "pepe",
                "email", "pepe@example.com",
                "direccion", "d1",
                "telefono", "t1",
                "contrasena", "Abcdef1!",
                "departamentoClave", departamentoClave
        );

        MvcResult create = mockMvc.perform(post("/api/v1/empleados")
                        .with(httpBasic("writer@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clave").isNumber())
                .andExpect(jsonPath("$.nombre").value("pepe"))
                .andReturn();

        Long clave = objectMapper.readTree(create.getResponse().getContentAsString()).get("clave").asLong();

        Map<String, Object> update = Map.of(
                "nombre", "pepe2",
                "email", "pepe2@example.com",
                "direccion", "d2",
                "telefono", "t2",
                "contrasena", "Xyzabc1!",
                "departamentoClave", departamentoClave
        );

        mockMvc.perform(put("/api/v1/empleados/{clave}", clave)
                        .with(httpBasic("writer@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("pepe2"))
                .andExpect(jsonPath("$.contrasena").doesNotExist())
                .andExpect(jsonPath("$.clave").value(not(0)));
    }
}
