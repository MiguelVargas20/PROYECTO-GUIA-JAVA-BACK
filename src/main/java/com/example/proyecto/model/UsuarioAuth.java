package com.example.proyecto.model;

import java.util.List;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Document(collection = "UsuarioAuth")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAuth {
 
    @Id
    private String id;
 
    /** Username único para login */
    @Indexed(unique = true)
    private String username;
 
    /** Contraseña encriptada con BCrypt */
    private String password;
 
    /** Roles asignados al usuario */
    private List<Rol> roles;
}
 