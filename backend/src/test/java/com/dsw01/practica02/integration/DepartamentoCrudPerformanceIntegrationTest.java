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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class DepartamentoCrudPerformanceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void setupData() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Departamento authDep = new Departamento();
        authDep.setNombre("AuthDep");
        authDep = departamentoRepository.save(authDep);

        Empleado auth = new Empleado();
        auth.setNombre("depperf");
        auth.setEmail("depperf@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(authDep);
        empleadoRepository.save(auth);

        for (int i = 0; i < 10; i++) {
            Departamento dep = new Departamento();
            dep.setNombre("PERF-" + i);
            departamentoRepository.save(dep);
        }
    }

    @Test
    void shouldMeetReadPerformanceGoal() throws Exception {
        int sampleSize = 30;
        int under500ms = 0;

        for (int i = 0; i < sampleSize; i++) {
            long start = System.nanoTime();
            mockMvc.perform(get("/api/v1/departamentos").with(httpBasic("depperf@example.com", "Abcdef1!")).param("size", "5"))
                    .andReturn();
            long elapsedMillis = (System.nanoTime() - start) / 1_000_000;
            if (elapsedMillis <= 500) {
                under500ms++;
            }
        }

        assertTrue(under500ms >= 27, "Expected at least 27 requests under 500ms, got " + under500ms);
    }
}
