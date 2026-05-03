package com.example.proyecto.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String CLAVE;

    @Value("${jwt.expiration}")
    private long tiempoExpiracion;

    // Método interno esencial para convertir el String del properties en una Key real
    private Key obtenerClave() {
        return Keys.hmacShaKeyFor(CLAVE.getBytes());
    }

    /**
     * Genera el token JWT con los datos del usuario.
     */
    public String generarToken(String username, List<String> roles, String nombre, String apellido, String id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("nombre", nombre)
                .claim("apellido", apellido)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
                .signWith(obtenerClave(), SignatureAlgorithm.HS256) // CORREGIDO: Usaba obtenerClaims() por error
                .compact();
    }

    // Extrae todos los claims del token
    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerClave()) // CORREGIDO: Usaba obtenerClaims() circularmente
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extrae el username (subject) del token
    public String extraerUsername(String token) {
        return obtenerClaims(token).getSubject();
    }

    // Extrae los roles del token
    @SuppressWarnings("unchecked") // Quita la línea amarilla de Type Safety
    public List<String> extraerRoles(String token) {
        return obtenerClaims(token).get("roles", List.class);
    }

    // Extrae el ID del usuario desde el token
    public String extraerId(String token) {
        return obtenerClaims(token).get("id", String.class);
    }

    // Valida que el token no haya expirado y sea correcto
    public boolean tokenValido(String token) {
        try {
            return !obtenerClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false; 
        }
    }
}