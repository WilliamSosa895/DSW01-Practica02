package com.dsw01.practica02.unit;

import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.exception.DepartmentNotFoundException;
import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.DepartamentoService;
import com.dsw01.practica02.service.EmpleadoService;
import com.dsw01.practica02.service.validation.PasswordPolicyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceDepartamentoValidationTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private PasswordPolicyValidator passwordPolicyValidator;

    @Mock
    private DepartamentoService departamentoService;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void shouldRejectCreateWhenDepartmentDoesNotExist() {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Ana");
        request.setEmail("ana@example.com");
        request.setDireccion("Dir");
        request.setTelefono("Tel");
        request.setContrasena("Abcdef1!");
        request.setDepartamentoClave(999L);

        when(departamentoService.getByClaveEntity(999L)).thenThrow(new DepartmentNotFoundException("missing"));

        assertThrows(DepartmentNotFoundException.class, () -> empleadoService.create(request));
    }

    @Test
    void shouldAllowCreateWhenDepartmentExists() {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Ana");
        request.setEmail("ana@example.com");
        request.setDireccion("Dir");
        request.setTelefono("Tel");
        request.setContrasena("Abcdef1!");
        request.setDepartamentoClave(1L);

        Departamento departamento = new Departamento();
        departamento.setClave(1L);
        departamento.setNombre("IT");

        when(departamentoService.getByClaveEntity(1L)).thenReturn(departamento);
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(inv -> inv.getArgument(0));

        empleadoService.create(request);
    }
}
