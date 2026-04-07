package com.dsw01.practica02.service;

import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.dto.EmpleadoPageResponse;
import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.dto.SesionUsuarioResponse;
import com.dsw01.practica02.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.dto.DepartamentoResponse;
import com.dsw01.practica02.dto.PaginationQuery;
import com.dsw01.practica02.exception.ConcurrencyConflictException;
import com.dsw01.practica02.exception.EmailAlreadyExistsException;
import com.dsw01.practica02.exception.ResourceNotFoundException;
import com.dsw01.practica02.model.Departamento;
import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.model.RolEmpleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import com.dsw01.practica02.service.validation.PasswordPolicyValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final PasswordPolicyValidator passwordPolicyValidator;
    private final DepartamentoService departamentoService;

    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           PasswordPolicyValidator passwordPolicyValidator,
                           DepartamentoService departamentoService) {
        this.empleadoRepository = empleadoRepository;
        this.passwordPolicyValidator = passwordPolicyValidator;
        this.departamentoService = departamentoService;
    }

    public EmpleadoResponse create(EmpleadoCreateRequest request) {
        passwordPolicyValidator.validate(request.getContrasena());
        String email = normalizeEmail(request.getEmail());
        if (empleadoRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyExistsException("email ya registrado en el sistema");
        }
        Empleado empleado = new Empleado();
        empleado.setNombre(request.getNombre());
        empleado.setEmail(email);
        empleado.setDireccion(request.getDireccion());
        empleado.setTelefono(request.getTelefono());
        empleado.setContrasena(request.getContrasena());
        empleado.setRol(request.getRol());
        empleado.setActivo(true);
        empleado.setDepartamento(departamentoService.getByClaveEntity(request.getDepartamentoClave()));
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
        passwordPolicyValidator.validate(request.getContrasena());
        String email = normalizeEmail(request.getEmail());
        if (empleadoRepository.existsByEmailIgnoreCaseAndClaveNot(email, clave)) {
            throw new EmailAlreadyExistsException("email ya registrado en el sistema");
        }
        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con clave " + clave));
        if (!request.getVersion().equals(empleado.getVersion())) {
            throw new ConcurrencyConflictException("version desfasada; recargar y reintentar");
        }
        empleado.setNombre(request.getNombre());
        empleado.setEmail(email);
        empleado.setDireccion(request.getDireccion());
        empleado.setTelefono(request.getTelefono());
        empleado.setContrasena(request.getContrasena());
        empleado.setRol(request.getRol());
        empleado.setDepartamento(departamentoService.getByClaveEntity(request.getDepartamentoClave()));
        Empleado updated = empleadoRepository.save(empleado);
        return toResponse(updated);
    }

    public SesionUsuarioResponse getSessionContext(Authentication authentication) {
        String username = authentication.getName();
        if ("admin".equals(username)) {
            return new SesionUsuarioResponse("admin", RolEmpleado.MASTER, true);
        }
        Empleado empleado = empleadoRepository.findByEmailIgnoreCaseAndActivoTrue(username)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado o inactivo"));
        RolEmpleado rol = empleado.getRol() == null ? RolEmpleado.STANDARD : empleado.getRol();
        return new SesionUsuarioResponse(empleado.getEmail(), rol, true);
    }

    public void delete(Long clave) {
        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con clave " + clave));
        empleadoRepository.delete(empleado);
    }

    public int normalizeLegacyEmailsForBackfill() {
        List<Empleado> empleados = empleadoRepository.findAll();
        int updated = 0;
        for (Empleado empleado : empleados) {
            if (empleado.getEmail() == null || empleado.getEmail().isBlank()) {
                Long clave = empleado.getClave() == null ? 0L : empleado.getClave();
                empleado.setEmail("legacy+" + clave + "@example.local");
                updated++;
            }
        }
        if (updated > 0) {
            empleadoRepository.saveAll(empleados);
        }
        return updated;
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private EmpleadoResponse toResponse(Empleado empleado) {
        Departamento departamento = empleado.getDepartamento();
        DepartamentoResponse departamentoResponse = new DepartamentoResponse(
            departamento.getClave(),
            departamento.getNombre()
        );
        return new EmpleadoResponse(
                empleado.getClave(),
                empleado.getNombre(),
            empleado.getEmail(),
                empleado.getDireccion(),
            empleado.getTelefono(),
            empleado.getRol(),
            empleado.getVersion(),
            departamentoResponse
        );
    }
}
