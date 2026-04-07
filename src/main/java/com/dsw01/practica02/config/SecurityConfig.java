package com.dsw01.practica02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        private final EmpleadoUserDetailsService empleadoUserDetailsService;
        private final RestAuthEntryPoint restAuthEntryPoint;
        private final RestAccessDeniedHandler restAccessDeniedHandler;

        public SecurityConfig(EmpleadoUserDetailsService empleadoUserDetailsService,
                              RestAuthEntryPoint restAuthEntryPoint,
                              RestAccessDeniedHandler restAccessDeniedHandler) {
                this.empleadoUserDetailsService = empleadoUserDetailsService;
                this.restAuthEntryPoint = restAuthEntryPoint;
                this.restAccessDeniedHandler = restAccessDeniedHandler;
        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/health",
                                "/api-docs",
                                "/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/empleados/**").hasRole("MASTER")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/empleados/**").hasRole("MASTER")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/v1/empleados/**").hasRole("MASTER")
                        .anyRequest().authenticated()
                )
                                .exceptionHandling(ex -> ex
                                        .authenticationEntryPoint(restAuthEntryPoint)
                                        .accessDeniedHandler(restAccessDeniedHandler)
                                )
                                .authenticationProvider(daoAuthenticationProvider())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
                return empleadoUserDetailsService;
        }

        @Bean
        @SuppressWarnings("deprecation")
        public PasswordEncoder passwordEncoder() {
                return NoOpPasswordEncoder.getInstance();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(empleadoUserDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
    }
}
