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
class DepartamentoDetailIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Long targetClave;

    @BeforeEach
    void setupData() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Departamento authDep = new Departamento();
        authDep.setNombre("AuthDep");
        authDep = departamentoRepository.save(authDep);

        Empleado auth = new Empleado();
        auth.setNombre("depdetail");
        auth.setEmail("depdetail@example.com");
        auth.setDireccion("Dir");
        auth.setTelefono("Tel");
        auth.setContrasena("Abcdef1!");
        auth.setActivo(true);
        auth.setDepartamento(authDep);
        empleadoRepository.save(auth);

        Departamento target = new Departamento();
        target.setNombre("Ventas");
        target = departamentoRepository.save(target);
        targetClave = target.getClave();

        Empleado e = new Empleado();
        e.setNombre("empleado-detalle");
        e.setEmail("empleado-detalle@example.com");
        e.setDireccion("dir");
        e.setTelefono("tel");
        e.setContrasena("Abcdef1!");
        e.setActivo(true);
        e.setDepartamento(target);
        empleadoRepository.save(e);
    }

    @Test
    void shouldReturnDepartmentDetailWithEmployees() throws Exception {
        mockMvc.perform(get("/api/v1/departamentos/{clave}", targetClave).with(httpBasic("depdetail@example.com", "Abcdef1!")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clave").value(targetClave))
                .andExpect(jsonPath("$.empleados[0].nombre").value("empleado-detalle"))
                .andExpect(jsonPath("$.empleados[0].contrasena").doesNotExist());
    }
}
