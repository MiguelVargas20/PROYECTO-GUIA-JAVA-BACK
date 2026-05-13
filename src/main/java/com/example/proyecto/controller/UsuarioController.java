package com.example.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto.dtos.CambiarPasswordDto;
import com.example.proyecto.dtos.UsuarioDto;
import com.example.proyecto.dtos.UsuarioRegistroDto;
import com.example.proyecto.Services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /api/usuarios/registro
    @PostMapping("/registro")
    public ResponseEntity<UsuarioRegistroDto> registrar(@Valid @RequestBody UsuarioRegistroDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrarUsuario(dto));
    }

    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    // GET /api/usuarios/doc/{docNum}
    @GetMapping("/doc/{docNum}")
    public ResponseEntity<UsuarioDto> obtenerPorDoc(@PathVariable String docNum) {
        return ResponseEntity.ok(usuarioService.obtenerPorDocNum(docNum));
    }

    // GET /api/usuarios/correo/{correo}
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioDto> obtenerPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.obtenerPorCorreo(correo));
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizar(
            @PathVariable String id,
            @RequestBody UsuarioDto dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

        /*** PATCH /api/usuarios/{id}/password
         * * Cambia la contraseña del usuario.
     * Body: { "passwordActual": "...", "passwordNueva": "..." }
     *
     * Separado del PUT para mayor claridad y seguridad.
     * Solo el propio usuario o un ADMIN debería llamar este endpoint.
     */
    
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> cambiarPassword(
            @PathVariable String id,
            @RequestBody CambiarPasswordDto dto) {
        usuarioService.cambiarPassword(id, dto);
        return ResponseEntity.noContent().build(); // 204 — sin contenido, éxito
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}