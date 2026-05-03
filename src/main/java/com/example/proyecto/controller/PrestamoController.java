package com.example.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.proyecto.dtos.PrestamoDto;
import com.example.proyecto.model.*;
import com.example.proyecto.Services.PrestamoService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    // POST /api/prestamos
    @PostMapping
    public ResponseEntity<PrestamoDto> crear(@Valid @RequestBody PrestamoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.crearPrestamo(dto));
    }

    // GET /api/prestamos
    @GetMapping
    public ResponseEntity<List<PrestamoDto>> listar() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    // GET /api/prestamos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(prestamoService.obtenerPorId(id));
    }

    // GET /api/prestamos/usuario/{usuarioId}
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoDto>> obtenerPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(prestamoService.obtenerPorUsuario(usuarioId));
    }

    // GET /api/prestamos/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamoDto>> obtenerPorEstado(@PathVariable EstadoPrestamo estado) {
        return ResponseEntity.ok(prestamoService.obtenerPorEstado(estado));
    }

    // PATCH /api/prestamos/{id}/devolver
    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoDto> registrarDevolucion(@PathVariable String id) {
        return ResponseEntity.ok(prestamoService.registrarDevolucion(id));
    }

    // PATCH /api/prestamos/vencidos
    @PatchMapping("/vencidos")
    public ResponseEntity<Void> actualizarVencidos() {
        prestamoService.actualizarVencidos();
        return ResponseEntity.noContent().build();
    }

    // DELETE /api/prestamos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }
}
