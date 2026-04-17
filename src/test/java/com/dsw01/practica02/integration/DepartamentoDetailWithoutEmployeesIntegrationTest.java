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
class DepartamentoDetailWithoutEmployeesIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Long emptyClave;

    @BeforeEach
    void setupData() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Departamento authDep = new Departamento();
        authDep.setNombre("AuthDep");
        authDep = departamentoRepository.save(authDep);

        Empleado auth = new Empleado();
        auth.setNombre("depempty");
        auth.setEmail("depempty@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(authDep);
        empleadoRepository.save(auth);

        Departamento empty = new Departamento();
        empty.setNombre("SinEmpleados");
        empty = departamentoRepository.save(empty);
        emptyClave = empty.getClave();
    }

    @Test
    void shouldReturnEmptyEmployeesList() throws Exception {
        mockMvc.perform(get("/api/v1/departamentos/{clave}", emptyClave).with(httpBasic("depempty@example.com", "Abcdef1!")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clave").value(emptyClave))
                .andExpect(jsonPath("$.empleados").isArray())
                .andExpect(jsonPath("$.empleados").isEmpty());
    }
}
