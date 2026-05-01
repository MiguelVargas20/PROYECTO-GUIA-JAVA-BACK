package com.example.proyecto.services;

import java.util.Optional;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.proyecto.dtos.LoginDto;
import com.example.proyecto.dtos.LoginResponseDto;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.model.UsuarioAuth;
import com.example.proyecto.reository.UsuarioAuthRepository;
import com.example.proyecto.reository.UsuarioRepository;
@Service
public class AuthServiceImpl implements AuthService {
 
    private final UsuarioAuthRepository authRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
 
    public AuthServiceImpl(
            UsuarioAuthRepository authRepo,
            UsuarioRepository usuarioRepo,
            PasswordEncoder passwordEncoder) {
        this.authRepo = authRepo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }
 
    @Override
    public LoginResponseDto login(LoginDto dto) {
 
        String identificador = dto.getUsername();
        UsuarioAuth auth;
 
        // 1. Intentar buscar por username en UsuarioAuth
        Optional<UsuarioAuth> porUsername = authRepo.findByUsername(identificador);
 
        if (porUsername.isPresent()) {
            auth = porUsername.get();
        } else {
            // 2. Si no es username, buscar en UsuarioPerfil por correo o documento
            Usuario usuario = usuarioRepo.findByCorreo(identificador)
                    .or(() -> usuarioRepo.findByDocNum(identificador))
                    .orElseThrow(() -> new RuntimeException("Credenciales no encontradas."));
 
            // Los IDs están sincronizados: buscamos el Auth con el ID del Perfil
            auth = authRepo.findById(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Error de integridad: perfil sin credenciales."));
        }
 
        // 3. Verificar contraseña con BCrypt
        if (!passwordEncoder.matches(dto.getPassword(), auth.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }
 
        // 4. Obtener el perfil completo para la respuesta
        Usuario perfil = usuarioRepo.findById(auth.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado."));
 
        // 5. Construir respuesta (token será null hasta implementar JWT)
        return LoginResponseDto.builder()
                .token(null)
                .username(auth.getUsername())
                .roles(auth.getRoles())
                .build();
    }
}