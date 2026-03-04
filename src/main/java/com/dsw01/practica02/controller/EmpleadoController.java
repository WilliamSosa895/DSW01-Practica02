package com.dsw01.practica02.controller;

import com.dsw01.practica02.dto.EmpleadoCreateRequest;
import com.dsw01.practica02.dto.EmpleadoPageResponse;
import com.dsw01.practica02.dto.EmpleadoResponse;
import com.dsw01.practica02.dto.EmpleadoUpdateRequest;
import com.dsw01.practica02.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> create(@Valid @RequestBody EmpleadoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.create(request));
    }

    @GetMapping("/{clave}")
    public EmpleadoResponse getByClave(@PathVariable Long clave) {
        return empleadoService.getByClave(clave);
    }

    @GetMapping
    public EmpleadoPageResponse list(@RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size) {
        return empleadoService.list(page, size);
    }

    @PutMapping("/{clave}")
    public EmpleadoResponse update(@PathVariable Long clave,
                                   @Valid @RequestBody EmpleadoUpdateRequest request) {
        return empleadoService.update(clave, request);
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> delete(@PathVariable Long clave) {
        empleadoService.delete(clave);
        return ResponseEntity.noContent().build();
    }
}
