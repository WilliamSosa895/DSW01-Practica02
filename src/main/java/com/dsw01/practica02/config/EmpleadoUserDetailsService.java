package com.dsw01.practica02.config;

import com.dsw01.practica02.model.Empleado;
import com.dsw01.practica02.model.RolEmpleado;
import com.dsw01.practica02.repository.EmpleadoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoUserDetailsService implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoUserDetailsService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Transitional compatibility mandated by constitution while moving to email auth.
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                .password("admin123")
                .roles("MASTER")
                .build();
        }

        Empleado empleado = empleadoRepository.findByEmailIgnoreCaseAndActivoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado o inactivo"));

        return User.withUsername(empleado.getEmail())
                .password(empleado.getContrasena())
                .roles(resolveRole(empleado).asSpringRole())
                .build();
    }

    private RolEmpleado resolveRole(Empleado empleado) {
        return empleado.getRol() == null ? RolEmpleado.STANDARD : empleado.getRol();
    }
}
