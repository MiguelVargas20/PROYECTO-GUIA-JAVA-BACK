package com.example.proyecto.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto.dtos.LibroDto;
import com.example.proyecto.model.EstadoLibro;
import com.example.proyecto.services.LibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // POST /api/libros
    @PostMapping
    public ResponseEntity<LibroDto> crear(@Valid @RequestBody LibroDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crearLibro(dto));
    }

    // GET /api/libros
    @GetMapping
    public ResponseEntity<List<LibroDto>> listar() {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    // GET /api/libros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LibroDto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(libroService.obtenerPorId(id));
    }

    // GET /api/libros/buscar?titulo=xxx
    @GetMapping("/buscar")
    public ResponseEntity<List<LibroDto>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    // GET /api/libros/autor/{autor}
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LibroDto>> buscarPorAutor(@PathVariable String autor) {
        return ResponseEntity.ok(libroService.buscarPorAutor(autor));
    }

    // GET /api/libros/genero/{genero}
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<LibroDto>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(libroService.buscarPorGenero(genero));
    }

    // GET /api/libros/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<LibroDto>> obtenerPorEstado(@PathVariable EstadoLibro estado) {
        return ResponseEntity.ok(libroService.obtenerPorEstado(estado));
    }

    // PUT /api/libros/{id}
    @PutMapping("/{id}")
    public ResponseEntity<LibroDto> actualizar(
            @PathVariable String id,
            @RequestBody LibroDto dto) {
        return ResponseEntity.ok(libroService.actualizarLibro(id, dto));
    }

    // DELETE /api/libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        libroService.eliminarLibro(id);
        return ResponseEntity.noContent().build();
    }
}