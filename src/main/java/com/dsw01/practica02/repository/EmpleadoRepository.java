package com.dsw01.practica02.repository;

import com.dsw01.practica02.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
	Optional<Empleado> findByNombre(String nombre);

	Optional<Empleado> findByNombreAndActivoTrue(String nombre);

	Optional<Empleado> findByEmailIgnoreCase(String email);

	Optional<Empleado> findByEmailIgnoreCaseAndActivoTrue(String email);

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByEmailIgnoreCaseAndClaveNot(String email, Long clave);

	boolean existsByDepartamentoClave(Long departamentoClave);

	List<Empleado> findByDepartamentoClaveOrderByClaveAsc(Long departamentoClave);
}
