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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpleadoPaginationIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void seedData() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Departamento departamento = new Departamento();
        departamento.setNombre("Pag");
        departamento = departamentoRepository.save(departamento);

        Empleado auth = new Empleado();
        auth.setNombre("pager");
        auth.setEmail("pager@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(departamento);
        empleadoRepository.save(auth);

        for (int i = 0; i < 8; i++) {
            Empleado e = new Empleado();
            e.setNombre("emp-" + i);
            e.setEmail("emp-" + i + "@example.com");
            e.setDireccion("d" + i);
            e.setTelefono("t" + i);
            e.setContrasena("Abcdef1!");
            e.setActivo(true);
            e.setDepartamento(departamento);
            empleadoRepository.save(e);
        }
    }

    @Test
    void shouldEnforceFixedPaginationSizeFive() throws Exception {
        mockMvc.perform(get("/api/v1/empleados").with(httpBasic("pager@example.com", "Abcdef1!")).param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(5));

        mockMvc.perform(get("/api/v1/empleados").with(httpBasic("pager@example.com", "Abcdef1!")).param("size", "4"))
                .andExpect(status().isBadRequest());
    }
}
