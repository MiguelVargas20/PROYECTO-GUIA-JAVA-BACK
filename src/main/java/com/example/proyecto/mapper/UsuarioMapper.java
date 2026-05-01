package com.example.proyecto.mapper;

import java.util.List;

import com.example.proyecto.dtos.UsuarioDto;
import com.example.proyecto.model.Usuario;


public interface UsuarioMapper {
 
    // Convierte UsuarioDto a entidad Usuario
    Usuario toEntity(UsuarioDto dto);
 
    // Convierte entidad Usuario a UsuarioDto
    UsuarioDto toDto(Usuario usuario);
 
    // Convierte lista de Usuario a lista de UsuarioDto
    List<UsuarioDto> toDtoList(List<Usuario> usuarios);
 
    // Actualiza una entidad Usuario existente con datos de un UsuarioDto
    void actualizarUsuario(UsuarioDto dto, Usuario usuario);
}
 