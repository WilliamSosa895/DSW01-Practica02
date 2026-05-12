package com.dsw01.practica02.config;

import com.dsw01.practica02.service.EmpleadoAuthService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoAuthenticationEventsListener {

    private final EmpleadoAuthService empleadoAuthService;

    public EmpleadoAuthenticationEventsListener(EmpleadoAuthService empleadoAuthService) {
        this.empleadoAuthService = empleadoAuthService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            empleadoAuthService.registrarExito(authentication.getName());
        }
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication == null ? null : authentication.getName();
        empleadoAuthService.registrarFallo(username);
    }
}
