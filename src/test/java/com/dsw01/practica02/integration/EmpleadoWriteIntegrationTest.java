package com.dsw01.practica02.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class EmpleadoWriteIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdateAndDeleteEmpleado() throws Exception {
        Map<String, Object> createPayload = Map.of(
                "nombre", "Carlos",
                "direccion", "Sur 303",
                "telefono", "555-0202"
        );

        MvcResult createResult = mockMvc.perform(post("/api/empleados")
                        .with(httpBasic("admin", "admin 123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPayload)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode createdNode = objectMapper.readTree(createResult.getResponse().getContentAsString());
        long clave = createdNode.get("clave").asLong();

        Map<String, Object> updatePayload = Map.of(
                "nombre", "Carlos Update",
                "direccion", "Sur 404",
                "telefono", "555-0303"
        );

        mockMvc.perform(put("/api/empleados/{clave}", clave)
                        .with(httpBasic("admin", "admin 123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePayload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Update"));

        mockMvc.perform(delete("/api/empleados/{clave}", clave)
                        .with(httpBasic("admin", "admin 123")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/empleados/{clave}", clave)
                        .with(httpBasic("admin", "admin 123")))
                .andExpect(status().isNotFound());
    }
}
