package com.dsw01.practica02.repository;

import com.dsw01.practica02.model.EventoAutenticacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoAutenticacionRepository extends JpaRepository<EventoAutenticacion, Long> {
}
