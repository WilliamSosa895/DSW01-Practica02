package com.dsw01.practica02.integration;

import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoAuthenticationPerformanceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Value("${auth.performance.sample-size:100}")
    private int sampleSize;

    @Value("${auth.performance.max-under-2s:95}")
    private int minUnder2s;

    @BeforeEach
    void setupUser() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();
        Departamento departamento = new Departamento();
        departamento.setNombre("Perf");
        departamento = departamentoRepository.save(departamento);
        Empleado auth = new Empleado();
        auth.setNombre("perfuser");
        auth.setEmail("perfuser@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(departamento);
        empleadoRepository.save(auth);
    }

    @Test
    void shouldMeetSc004Threshold() throws Exception {
        int under2s = 0;

        for (int i = 0; i < sampleSize; i++) {
            String password = i % 2 == 0 ? "Abcdef1!" : "Wrong1!";
            long start = System.nanoTime();
            mockMvc.perform(get("/api/v1/empleados").with(httpBasic("perfuser@example.com", password)).param("size", "5"))
                    .andReturn();
            long elapsedMillis = (System.nanoTime() - start) / 1_000_000;
            if (elapsedMillis < 2_000) {
                under2s++;
            }
        }

        assertTrue(under2s >= minUnder2s,
                "Expected at least " + minUnder2s + " responses under 2s, got " + under2s);
    }
}
