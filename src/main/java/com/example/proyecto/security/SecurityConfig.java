package com.example.proyecto.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ── Rutas públicas ───────────────────────────────────────────
                .requestMatchers(
                    "/api/auth/login",
                    "/api/usuarios/registro"
                ).permitAll()

                // ── Solo ADMINISTRADOR ───────────────────────────────────────

                // Gestión completa de usuarios
                .requestMatchers(HttpMethod.GET,    "/api/usuarios/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT,    "/api/usuarios/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("ADMINISTRADOR")

                // Crear y eliminar libros
                .requestMatchers(HttpMethod.POST,   "/api/libros/**").hasAuthority("ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/libros/**").hasAuthority("ADMINISTRADOR")

                // Eliminar préstamos
                .requestMatchers(HttpMethod.DELETE, "/api/prestamos/**").hasAuthority("ADMINISTRADOR")

                // ── ADMINISTRADOR o BIBLIOTECARIO ────────────────────────────

                // Editar libros y actualizar vencidos
                .requestMatchers(HttpMethod.PUT,   "/api/libros/**").hasAnyAuthority("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.PATCH, "/api/prestamos/vencidos").hasAnyAuthority("ADMINISTRADOR", "BIBLIOTECARIO")

                // Gestión de préstamos (crear, devolver, listar todos)
                .requestMatchers(HttpMethod.POST,  "/api/prestamos/**").hasAnyAuthority("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.PATCH, "/api/prestamos/**").hasAnyAuthority("ADMINISTRADOR", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.GET,   "/api/prestamos/**").hasAnyAuthority("ADMINISTRADOR", "BIBLIOTECARIO")

                // ── LECTOR (solo consulta) ───────────────────────────────────

                // Ver catálogo de libros
                .requestMatchers(HttpMethod.GET, "/api/libros/**").hasAnyAuthority("ADMINISTRADOR", "BIBLIOTECARIO", "LECTOR")

                // ── Todo lo demás requiere autenticación ─────────────────────
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS: permite solicitudes desde el frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}