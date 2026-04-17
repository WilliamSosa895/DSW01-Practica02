package com.dsw01.practica02.unit;

import com.dsw01.practica02.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.exception.DepartmentHasEmployeesException;
import com.dsw01.practica02.exception.DepartmentNotFoundException;
import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.DepartamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    @Test
    void shouldCreateDepartment() {
        DepartamentoCreateRequest request = new DepartamentoCreateRequest();
        request.setNombre("IT");

        Departamento saved = new Departamento();
        saved.setClave(1L);
        saved.setNombre("IT");

        when(departamentoRepository.findByNombreIgnoreCase("IT")).thenReturn(Optional.empty());
        when(departamentoRepository.save(any(Departamento.class))).thenReturn(saved);

        assertEquals("IT", departamentoService.create(request).getNombre());
    }

    @Test
    void shouldThrowNotFoundWhenDepartmentMissing() {
        when(departamentoRepository.findById(88L)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class, () -> departamentoService.getByClave(88L));
    }

    @Test
    void shouldBlockDeleteWhenEmployeesExist() {
        Departamento dep = new Departamento();
        dep.setClave(7L);
        dep.setNombre("Ops");

        when(departamentoRepository.findById(7L)).thenReturn(Optional.of(dep));
        when(empleadoRepository.existsByDepartamentoClave(7L)).thenReturn(true);

        assertThrows(DepartmentHasEmployeesException.class, () -> departamentoService.delete(7L));
    }

    @Test
    void shouldValidateUniqueNameOnUpdate() {
        Departamento dep = new Departamento();
        dep.setClave(5L);
        dep.setNombre("Old");

        DepartamentoUpdateRequest request = new DepartamentoUpdateRequest();
        request.setNombre("New");

        when(departamentoRepository.findById(5L)).thenReturn(Optional.of(dep));
        when(departamentoRepository.existsByNombreIgnoreCaseAndClaveNot("New", 5L)).thenReturn(false);
        when(departamentoRepository.save(any(Departamento.class))).thenAnswer(inv -> inv.getArgument(0));

        assertEquals("New", departamentoService.update(5L, request).getNombre());
    }
}
