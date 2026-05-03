package com.example.proyecto.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.proyecto.dtos.LoginDto;
import com.example.proyecto.dtos.LoginResponseDto;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.model.UsuarioAuth;
import com.example.proyecto.reository.UsuarioAuthRepository;
import com.example.proyecto.reository.UsuarioRepository;
import com.example.proyecto.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioAuthRepository authRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;          // ← agregado

    public AuthServiceImpl(
            UsuarioAuthRepository authRepo,
            UsuarioRepository usuarioRepo,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {              // ← agregado
        this.authRepo = authRepo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;             // ← agregado
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
            // 2. Si no es username, buscar por correo o documento
            Usuario usuario = usuarioRepo.findByCorreo(identificador)
                    .or(() -> usuarioRepo.findByDocNum(identificador))
                    .orElseThrow(() -> new RuntimeException("Credenciales no encontradas."));

            auth = authRepo.findById(usuario.getId())
                    .orElseThrow(() -> new RuntimeException("Error de integridad: perfil sin credenciales."));
        }

        // 3. Verificar contraseña con BCrypt
        if (!passwordEncoder.matches(dto.getPassword(), auth.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        // 4. Obtener perfil completo para datos del token
        Usuario perfil = usuarioRepo.findById(auth.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado."));

        // 5. Convertir roles a List<String> para el token
        List<String> roles = auth.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());

        // 6. Generar token JWT ← esto era lo que faltaba
        String token = jwtService.generarToken(
                auth.getUsername(),
                roles,
                perfil.getNombre(),
                perfil.getApellido(),
                perfil.getId()
        );

        // 7. Construir y retornar respuesta con token real
        return LoginResponseDto.builder()
                .token(token)
                .username(auth.getUsername())
                .roles(auth.getRoles())
                .build();
    }
}