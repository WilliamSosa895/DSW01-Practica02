package com.dsw01.practica02.service;

import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.dto.EmpleadoPageResponse;
import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.dto.PaginationQuery;
import com.dsw01.practica02.exception.ResourceNotFoundException;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public EmpleadoResponse create(EmpleadoCreateRequest request) {
        Empleado empleado = new Empleado();
        empleado.setNombre(request.getNombre());
        empleado.setDireccion(request.getDireccion());
        empleado.setTelefono(request.getTelefono());
        Empleado saved = empleadoRepository.save(empleado);
        return toResponse(saved);
    }

    public EmpleadoResponse getByClave(Long clave) {
        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con clave " + clave));
        return toResponse(empleado);
    }

    public EmpleadoPageResponse list(Integer page, Integer size) {
        PaginationQuery query = new PaginationQuery(page, size);
        query.validate();
        Page<Empleado> result = empleadoRepository.findAll(PageRequest.of(query.getPageOrDefault(), query.getSizeOrDefault()));
        List<EmpleadoResponse> content = result.getContent().stream().map(this::toResponse).toList();
        return new EmpleadoPageResponse(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    public EmpleadoResponse update(Long clave, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con clave " + clave));
        empleado.setNombre(request.getNombre());
        empleado.setDireccion(request.getDireccion());
        empleado.setTelefono(request.getTelefono());
        Empleado updated = empleadoRepository.save(empleado);
        return toResponse(updated);
    }

    public void delete(Long clave) {
        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con clave " + clave));
        empleadoRepository.delete(empleado);
    }

    private EmpleadoResponse toResponse(Empleado empleado) {
        return new EmpleadoResponse(
                empleado.getClave(),
                empleado.getNombre(),
                empleado.getDireccion(),
                empleado.getTelefono()
        );
    }
}
