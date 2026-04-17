package com.dsw01.practica02.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiContractIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldExposeOpenApiContractWithExpectedEndpoints() throws Exception {
        mockMvc.perform(get("/api-docs"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/api/v1/departamentos")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/api/v1/departamentos/{clave}")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/api/v1/empleados")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("/api/v1/empleados/{clave}")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("basicAuth")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("contrasena")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("departamentoClave")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("departamento")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("writeOnly")));
    }
}
