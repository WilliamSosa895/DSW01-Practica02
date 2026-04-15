package com.dsw01.practica02.unit;

import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.exception.ResourceNotFoundException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpleadoServicePasswordValidationTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private PasswordPolicyValidator passwordPolicyValidator;

    @Mock
    private DepartamentoService departamentoService;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void shouldValidatePasswordOnCreate() {
        Departamento dep = new Departamento();
        dep.setClave(1L);

        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Ana");
        request.setEmail("ana@example.com");
        request.setDireccion("Dir");
        request.setTelefono("Tel");
        request.setContrasena("Abcdef1!");
        request.setDepartamentoClave(1L);

        when(departamentoService.getByClaveEntity(1L)).thenReturn(dep);
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));

        empleadoService.create(request);

        verify(passwordPolicyValidator).validate("Abcdef1!");
    }

    @Test
    void shouldValidatePasswordOnUpdate() {
        Departamento dep = new Departamento();
        dep.setClave(1L);

        Empleado existing = new Empleado();
        existing.setClave(1L);
        existing.setDepartamento(dep);

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("Ana");
        request.setEmail("ana@example.com");
        request.setDireccion("Dir");
        request.setTelefono("Tel");
        request.setContrasena("Abcdef1!");
        request.setDepartamentoClave(1L);

        when(departamentoService.getByClaveEntity(1L)).thenReturn(dep);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocation -> invocation.getArgument(0));

        empleadoService.update(1L, request);

        verify(passwordPolicyValidator).validate("Abcdef1!");
    }

    @Test
    void shouldThrowNotFoundWhenUpdatingMissingEmpleado() {
        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("Ana");
        request.setEmail("ana@example.com");
        request.setDireccion("Dir");
        request.setTelefono("Tel");
        request.setContrasena("Abcdef1!");
        request.setDepartamentoClave(1L);

        when(empleadoRepository.findById(88L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.update(88L, request));
    }
}
