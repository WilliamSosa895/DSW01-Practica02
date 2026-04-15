package com.dsw01.practica02.service;

import com.dsw01.practica02.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.dto.DepartamentoDetailResponse;
import com.dsw01.practica02.dto.DepartamentoPageResponse;
import com.dsw01.practica02.dto.DepartamentoResponse;
import com.dsw01.practica02.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.dto.EmpleadoResumenResponse;
import com.dsw01.practica02.dto.PaginationQuery;
import com.dsw01.practica02.exception.DepartmentHasEmployeesException;
import com.dsw01.practica02.exception.DepartmentNotFoundException;
import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.DepartamentoRepository;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository,
                               EmpleadoRepository empleadoRepository) {
        this.departamentoRepository = departamentoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public DepartamentoResponse create(DepartamentoCreateRequest request) {
        validateUniqueNameForCreate(request.getNombre());
        Departamento departamento = new Departamento();
        departamento.setNombre(request.getNombre());
        return toResponse(departamentoRepository.save(departamento));
    }

    public DepartamentoResponse getByClave(Long clave) {
        return toResponse(getByClaveEntity(clave));
    }

    public DepartamentoDetailResponse getDetailByClave(Long clave) {
        Departamento departamento = getByClaveEntity(clave);
        List<EmpleadoResumenResponse> empleados = empleadoRepository.findByDepartamentoClaveOrderByClaveAsc(clave)
                .stream()
                .map(this::toEmpleadoResumen)
                .toList();
        return new DepartamentoDetailResponse(departamento.getClave(), departamento.getNombre(), empleados);
    }

    public DepartamentoPageResponse list(Integer page, Integer size) {
        PaginationQuery query = new PaginationQuery(page, size);
        query.validate();
        Page<Departamento> result = departamentoRepository.findAll(PageRequest.of(query.getPageOrDefault(), query.getSizeOrDefault()));
        List<DepartamentoResponse> content = result.getContent().stream().map(this::toResponse).toList();
        return new DepartamentoPageResponse(content, result.getNumber(), result.getSize(), result.getTotalElements(), result.getTotalPages());
    }

    public DepartamentoResponse update(Long clave, DepartamentoUpdateRequest request) {
        Departamento departamento = getByClaveEntity(clave);
        validateUniqueNameForUpdate(clave, request.getNombre());
        departamento.setNombre(request.getNombre());
        return toResponse(departamentoRepository.save(departamento));
    }

    public void delete(Long clave) {
        Departamento departamento = getByClaveEntity(clave);
        if (empleadoRepository.existsByDepartamentoClave(clave)) {
            throw new DepartmentHasEmployeesException("No se puede eliminar el departamento porque tiene empleados asociados");
        }
        departamentoRepository.delete(departamento);
    }

    public Departamento getByClaveEntity(Long clave) {
        return departamentoRepository.findById(clave)
                .orElseThrow(() -> new DepartmentNotFoundException("Departamento no encontrado con clave " + clave));
    }

    private void validateUniqueNameForCreate(String nombre) {
        if (departamentoRepository.findByNombreIgnoreCase(nombre).isPresent()) {
            throw new DataIntegrityViolationException("nombre de departamento duplicado");
        }
    }

    private void validateUniqueNameForUpdate(Long clave, String nombre) {
        if (departamentoRepository.existsByNombreIgnoreCaseAndClaveNot(nombre, clave)) {
            throw new DataIntegrityViolationException("nombre de departamento duplicado");
        }
    }

    private DepartamentoResponse toResponse(Departamento departamento) {
        return new DepartamentoResponse(departamento.getClave(), departamento.getNombre());
    }

    private EmpleadoResumenResponse toEmpleadoResumen(Empleado empleado) {
        return new EmpleadoResumenResponse(
                empleado.getClave(),
                empleado.getNombre(),
                empleado.getDireccion(),
                empleado.getTelefono()
        );
    }
}
