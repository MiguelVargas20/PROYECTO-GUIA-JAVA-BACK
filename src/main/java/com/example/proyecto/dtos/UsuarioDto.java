package com.example.proyecto.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.proyecto.model.Direccion;
import com.example.proyecto.model.Documento;
import com.example.proyecto.model.EstadoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
 
    private String id;
    private String nombre;
    private String apellido;
    private Documento docId;
    private String telefono;
    private String correo;
    private Direccion direccion;
    private LocalDate fechaNacimiento;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;
}
 