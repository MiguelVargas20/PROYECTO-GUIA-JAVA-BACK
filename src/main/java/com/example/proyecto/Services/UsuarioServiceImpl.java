package com.example.proyecto.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto.dtos.UsuarioDto;
import com.example.proyecto.dtos.UsuarioRegistroDto;
import com.example.proyecto.mapper.UsuarioMapper;
import com.example.proyecto.model.Usuario;
import com.example.proyecto.model.UsuarioAuth;
import com.example.proyecto.model.Rol;
import com.example.proyecto.reository.UsuarioAuthRepository;
import com.example.proyecto.reository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final UsuarioAuthRepository authRepo;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepo,
            UsuarioAuthRepository authRepo,
            UsuarioMapper usuarioMapper,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.authRepo = authRepo;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto dto) {

        // 1. Validar que el documento no esté ya registrado
        if (usuarioRepo.existsByDocNum(dto.getDocId().getNumero())) {
            throw new RuntimeException("El documento ya está registrado.");
        }

        // 2. Validar que el username no esté en uso
        if (authRepo.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        // 3. Validar que el correo no esté registrado
        if (usuarioRepo.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // 4. Guardar perfil en colección UsuarioPerfil
        Usuario perfil = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .docId(dto.getDocId())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .direccion(dto.getDireccion())
                .fechaNacimiento(dto.getFechaNacimiento())
                .estado(dto.getEstado())
                .fechaRegistro(LocalDateTime.now())
                .build();

        Usuario perfilGuardado = usuarioRepo.save(perfil);

        // 5. Guardar credenciales en UsuarioAuth con el mismo ID del perfil
        UsuarioAuth auth = UsuarioAuth.builder()
                .id(perfilGuardado.getId())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(
                    (dto.getRoles() != null && !dto.getRoles().isEmpty())
                        ? dto.getRoles()
                        : List.of(Rol.LECTOR) // Rol por defecto
                )
                .build();

        authRepo.save(auth);

        return dto;
    }

    @Override
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepo.findAll().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto obtenerPorId(String id) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto obtenerPorDocNum(String docnum) {
        Usuario usuario = usuarioRepo.findByDocNum(docnum)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con documento: " + docnum));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto obtenerPorCorreo(String correo) {
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto actualizarUsuario(String id, UsuarioDto dto) {
        Usuario existente = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe usuario con ID: " + id));

        usuarioMapper.actualizarUsuario(dto, existente);

        return usuarioMapper.toDto(usuarioRepo.save(existente));
    }

    @Override
    @Transactional
    public void eliminarUsuario(String id) {
        if (!usuarioRepo.existsById(id)) {
            throw new RuntimeException("ID no encontrado para eliminar: " + id);
        }
        usuarioRepo.deleteById(id);

        // Eliminar también las credenciales si existen
        if (authRepo.existsById(id)) {
            authRepo.deleteById(id);
        }
    }

    @Override
    public boolean existePorDocumento(String docnum) {
        return usuarioRepo.existsByDocNum(docnum);
    }
}