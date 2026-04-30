package com.example.proyecto.reository;

import java.util.Optional;
 
import org.springframework.data.mongodb.repository.MongoRepository;
 
import com.example.proyecto.model.UsuarioAuth;
 
public interface UsuarioAuthRepository extends MongoRepository<UsuarioAuth, String> {
 
    // Buscar por username para login
    Optional<UsuarioAuth> findByUsername(String username);
 
    boolean existsByUsername(String username);
}
 