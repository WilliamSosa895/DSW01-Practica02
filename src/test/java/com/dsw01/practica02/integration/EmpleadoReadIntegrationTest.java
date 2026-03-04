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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoReadIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReadByClaveAndListPaged() throws Exception {
        Map<String, Object> payload = Map.of(
                "nombre", "Marta",
                "direccion", "Norte 202",
                "telefono", "555-0101"
        );

        MvcResult createResult = mockMvc.perform(post("/api/empleados")
                        .with(httpBasic("admin", "admin 123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode createdNode = objectMapper.readTree(createResult.getResponse().getContentAsString());
        long clave = createdNode.get("clave").asLong();

        mockMvc.perform(get("/api/empleados/{clave}", clave)
                        .with(httpBasic("admin", "admin 123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clave").value(clave));

        mockMvc.perform(get("/api/empleados")
                        .param("page", "0")
                        .param("size", "5")
                        .with(httpBasic("admin", "admin 123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.size").value(5));
    }
}
