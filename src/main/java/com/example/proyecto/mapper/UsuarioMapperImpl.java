package com.example.proyecto.mapper;

import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Component;

import com.example.proyecto.dtos.UsuarioDto;
import com.example.proyecto.model.Usuario;

 
@Component
public class UsuarioMapperImpl implements UsuarioMapper {
 
    // Convierte UsuarioDto a entidad Usuario
    @Override
    public Usuario toEntity(UsuarioDto dto) {
        if (dto == null) return null;
 
        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .docId(dto.getDocId())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .direccion(dto.getDireccion())
                .fechaNacimiento(dto.getFechaNacimiento())
                .estado(dto.getEstado())
                .fechaRegistro(dto.getFechaRegistro())
                .build();
    }
 
    // Convierte entidad Usuario a UsuarioDto
    @Override
    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;
 
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .docId(usuario.getDocId())
                .telefono(usuario.getTelefono())
                .correo(usuario.getCorreo())
                .direccion(usuario.getDireccion())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
 
    // Convierte lista de Usuario a lista de UsuarioDto
    @Override
    public List<UsuarioDto> toDtoList(List<Usuario> usuarios) {
        if (usuarios == null) return null;
        return usuarios.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
 
    // Actualiza solo los campos editables del perfil (no toca ID ni fechaRegistro)
    @Override
    public void actualizarUsuario(UsuarioDto dto, Usuario usuario) {
        if (dto == null || usuario == null) return;
 
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setDocId(dto.getDocId());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCorreo(dto.getCorreo());
        usuario.setDireccion(dto.getDireccion());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setEstado(dto.getEstado());
    }
}
 