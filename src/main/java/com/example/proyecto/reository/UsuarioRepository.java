package com.example.proyecto.reository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

import com.example.proyecto.model.Usuario;
import com.example.proyecto.model.EstadoUsuario;
 
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
 
    // Buscar por correo
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
 
    // Buscar por número de documento
    @Query("{ 'docId.numero' : ?0 }")
    Optional<Usuario> findByDocNum(String numero);
 
    @Query(value = "{ 'docId.numero' : ?0 }", exists = true)
    boolean existsByDocNum(String numero);
 
    // Filtrar por estado: ACTIVO | INACTIVO | SUSPENDIDO
    List<Usuario> findByEstado(EstadoUsuario estado);
}
 