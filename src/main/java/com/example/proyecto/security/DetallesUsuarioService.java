package com.example.proyecto.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.proyecto.model.*;
import com.example.proyecto.reository.*;

@Service
public class DetallesUsuarioService implements UserDetailsService {

    private final UsuarioAuthRepository authRepo;
    private final UsuarioRepository usuarioRepo;

    public DetallesUsuarioService(UsuarioAuthRepository authRepo, UsuarioRepository usuarioRepo) {
        this.authRepo = authRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscar credenciales en UsuarioAuth
        UsuarioAuth auth = authRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Buscar perfil para validar estado
        Usuario perfil = usuarioRepo.findById(auth.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Perfil no encontrado para: " + username));

        // 3. Validar que el usuario esté ACTIVO (INACTIVO o SUSPENDIDO no pueden entrar)
        if (perfil.getEstado() != EstadoUsuario.ACTIVO) {
            throw new UsernameNotFoundException(
                "Usuario " + perfil.getEstado().name().toLowerCase() + ". Contacte al administrador.");
        }

        // 4. Construir UserDetails con roles
        return User.builder()
                .username(auth.getUsername())
                .password(auth.getPassword())
                .authorities(
                    auth.getRoles().stream()
                        .map(Enum::name)
                        .toArray(String[]::new))
                .build();
    }
}