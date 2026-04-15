package com.dsw01.practica02.repository;

import com.dsw01.practica02.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    Optional<Departamento> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndClaveNot(String nombre, Long clave);
}
