package com.example.proyecto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.proyecto.dtos.LibroDto;
import com.example.proyecto.mapper.LibroMapper;
import com.example.proyecto.model.EstadoLibro;
import com.example.proyecto.model.Libro;
import com.example.proyecto.reository.LibroRepository;



@Service
public class LibroServiceImpl implements LibroService {
 
    private final LibroRepository libroRepo;
    private final LibroMapper libroMapper;
 
    public LibroServiceImpl(LibroRepository libroRepo, LibroMapper libroMapper) {
        this.libroRepo = libroRepo;
        this.libroMapper = libroMapper;
    }
 
    @Override
    public LibroDto crearLibro(LibroDto dto) {
 
        // Validar campos obligatorios
        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new RuntimeException("El título del libro es obligatorio.");
        }
        if (dto.getAutores() == null || dto.getAutores().isEmpty()) {
            throw new RuntimeException("El libro debe tener al menos un autor.");
        }
 
        // Validar duplicado por título y editorial
        if (libroRepo.existsByTituloAndEditorial(dto.getTitulo(), dto.getEditorial())) {
            throw new RuntimeException("Ya existe un libro con ese título y editorial.");
        }
 
        // Estado por defecto si no se envía
        if (dto.getEstado() == null) {
            dto.setEstado(EstadoLibro.DISPONIBLE);
        }
 
        Libro libro = libroMapper.toEntity(dto);
        return libroMapper.toDto(libroRepo.save(libro));
    }
 
    @Override
    public List<LibroDto> listarLibros() {
        return libroRepo.findAll().stream()
                .map(libroMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public LibroDto obtenerPorId(String id) {
        Libro libro = libroRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
        return libroMapper.toDto(libro);
    }
 
    @Override
    public List<LibroDto> buscarPorTitulo(String titulo) {
        return libroRepo.findByTituloContaining(titulo).stream()
                .map(libroMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public List<LibroDto> buscarPorAutor(String autor) {
        return libroRepo.findByAutor(autor).stream()
                .map(libroMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public List<LibroDto> buscarPorGenero(String genero) {
        return libroRepo.findByGenero(genero).stream()
                .map(libroMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public List<LibroDto> obtenerPorEstado(EstadoLibro estado) {
        return libroRepo.findByEstado(estado).stream()
                .map(libroMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public LibroDto actualizarLibro(String id, LibroDto dto) {
        Libro existente = libroRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
 
        libroMapper.actualizarLibro(dto, existente);
 
        return libroMapper.toDto(libroRepo.save(existente));
    }
 
    @Override
    public void eliminarLibro(String id) {
        if (!libroRepo.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
        libroRepo.deleteById(id);
    }
}
 