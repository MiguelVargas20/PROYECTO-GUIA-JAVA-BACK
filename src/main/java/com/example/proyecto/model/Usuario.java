package com.example.proyecto.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
 
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Document(collection = "UsuarioPerfil")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
 
    @Id
    private String id;
 
    /** Nombre del usuario */
    private String nombre;
 
    /** Apellido del usuario */
    private String apellido;
 
    /** Documento de identidad embebido */
    private Documento docId;
 
    /** Teléfono de contacto */
    private String telefono;
 
    /** Correo electrónico (único en el sistema) */
    @Indexed(unique = true)
    private String correo;
 
    /** Dirección de residencia */
    private Direccion direccion;
 
    /** Fecha de nacimiento */
    private LocalDate fechaNacimiento;
 
    /** Estado del usuario: ACTIVO | INACTIVO | SUSPENDIDO */
    private EstadoUsuario estado;
 
    /** Fecha de registro en el sistema */
    private LocalDateTime fechaRegistro;
}