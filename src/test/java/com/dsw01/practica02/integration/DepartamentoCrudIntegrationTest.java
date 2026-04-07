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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DepartamentoCrudIntegrationTest extends AbstractIntegrationTest {

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
        auth.setNombre("depcrud");
        auth.setEmail("depcrud@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(authDep);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldCreateReadUpdateAndDeleteDepartment() throws Exception {
        MvcResult create = mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic("depcrud@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("nombre", "IT"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("IT"))
                .andReturn();

        Long clave = objectMapper.readTree(create.getResponse().getContentAsString()).get("clave").asLong();

        mockMvc.perform(get("/api/v1/departamentos/{clave}", clave).with(httpBasic("depcrud@example.com", "Abcdef1!")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clave").value(clave))
                .andExpect(jsonPath("$.nombre").value("IT"));

        mockMvc.perform(put("/api/v1/departamentos/{clave}", clave)
                        .with(httpBasic("depcrud@example.com", "Abcdef1!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("nombre", "IT-NEW"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("IT-NEW"));

        mockMvc.perform(delete("/api/v1/departamentos/{clave}", clave).with(httpBasic("depcrud@example.com", "Abcdef1!")))
                .andExpect(status().isNoContent());
    }
}
