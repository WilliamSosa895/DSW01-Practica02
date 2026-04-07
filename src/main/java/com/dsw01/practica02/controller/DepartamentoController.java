package com.dsw01.practica02.controller;

import com.dsw01.practica02.dto.DepartamentoCreateRequest;
import com.dsw01.practica02.dto.DepartamentoDetailResponse;
import com.dsw01.practica02.dto.DepartamentoPageResponse;
import com.dsw01.practica02.dto.DepartamentoResponse;
import com.dsw01.practica02.dto.DepartamentoUpdateRequest;
import com.dsw01.practica02.service.DepartamentoService;
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
@RequestMapping("/api/v1/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponse> create(@Valid @RequestBody DepartamentoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoService.create(request));
    }

    @GetMapping("/{clave}")
    public DepartamentoDetailResponse getByClave(@PathVariable Long clave) {
        return departamentoService.getDetailByClave(clave);
    }

    @GetMapping
    public DepartamentoPageResponse list(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size) {
        return departamentoService.list(page, size);
    }

    @PutMapping("/{clave}")
    public DepartamentoResponse update(@PathVariable Long clave,
                                       @Valid @RequestBody DepartamentoUpdateRequest request) {
        return departamentoService.update(clave, request);
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> delete(@PathVariable Long clave) {
        departamentoService.delete(clave);
        return ResponseEntity.noContent().build();
    }
}
