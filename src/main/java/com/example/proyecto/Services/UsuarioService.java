package com.example.proyecto.services;

import java.util.List;

import com.example.proyecto.dtos.UsuarioDto;
import com.example.proyecto.dtos.UsuarioRegistroDto;


public interface UsuarioService {
 
    /** Registra perfil en UsuarioPerfil y credenciales en UsuarioAuth con el mismo ID */
    UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto dto);
 
    /** Obtiene todos los usuarios del sistema */
    List<UsuarioDto> listarUsuarios();
 
    /** Busca un usuario por su ID de MongoDB */
    UsuarioDto obtenerPorId(String id);
 
    /** Busca un usuario por su número de documento */
    UsuarioDto obtenerPorDocNum(String docnum);
 
    /** Busca un usuario por su correo */
    UsuarioDto obtenerPorCorreo(String correo);
 
    /** Actualiza datos de perfil. No toca credenciales */
    UsuarioDto actualizarUsuario(String id, UsuarioDto dto);
 
    /** Elimina perfil y credenciales del usuario */
    void eliminarUsuario(String id);
 
    /** Valida si ya existe un usuario con ese número de documento */
    boolean existePorDocumento(String docnum);
}
 