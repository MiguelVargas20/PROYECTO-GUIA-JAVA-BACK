package com.example.proyecto.dtos;

import java.time.LocalDate;
import java.util.List;

import com.example.proyecto.model.Direccion;
import com.example.proyecto.model.Documento;
import com.example.proyecto.model.EstadoUsuario;
import com.example.proyecto.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRegistroDto {

    // ── Datos de perfil ──────────────────────────────────────
    private String nombre;
    private String apellido;
    private Documento docId;        // ← corregido: era "Id" con mayúscula
    private String telefono;
    private String correo;
    private Direccion direccion;
    private LocalDate fechaNacimiento;
    private EstadoUsuario estado;

    // ── Credenciales ─────────────────────────────────────────
    private String username;
    private String password;
    private List<Rol> roles;
}