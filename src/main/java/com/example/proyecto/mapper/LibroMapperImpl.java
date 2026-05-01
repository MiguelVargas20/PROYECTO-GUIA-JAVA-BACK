package com.example.proyecto.mapper;

import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Component;
 
import com.example.proyecto.dtos.LibroDto;
import com.example.proyecto.model.Libro;
 
@Component
public class LibroMapperImpl implements LibroMapper {
 
    // Convierte LibroDto a entidad Libro
    @Override
    public Libro toEntity(LibroDto dto) {
        if (dto == null) return null;
 
        return Libro.builder()
                .id(dto.getId())
                .titulo(dto.getTitulo())
                .autores(dto.getAutores())
                .genero(dto.getGenero())
                .editorial(dto.getEditorial())
                .anioPub(dto.getAnioPub())
                .ejemplares(dto.getEjemplares())
                .ejemplaresDisponibles(dto.getEjemplaresDisponibles())
                .estado(dto.getEstado())
                .build();
    }
 
    // Convierte entidad Libro a LibroDto
    @Override
    public LibroDto toDto(Libro libro) {
        if (libro == null) return null;
 
        return LibroDto.builder()
                .id(libro.getId())
                .titulo(libro.getTitulo())
                .autores(libro.getAutores())
                .genero(libro.getGenero())
                .editorial(libro.getEditorial())
                .anioPub(libro.getAnioPub())
                .ejemplares(libro.getEjemplares())
                .ejemplaresDisponibles(libro.getEjemplaresDisponibles())
                .estado(libro.getEstado())
                .build();
    }
 
    // Convierte lista de Libro a lista de LibroDto
    @Override
    public List<LibroDto> toDtoList(List<Libro> libros) {
        if (libros == null) return null;
        return libros.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
 
    // Actualiza los campos editables de un Libro (no toca ID ni ejemplaresDisponibles desde aquí)
    @Override
    public void actualizarLibro(LibroDto dto, Libro libro) {
        if (dto == null || libro == null) return;
 
        libro.setTitulo(dto.getTitulo());
        libro.setAutores(dto.getAutores());
        libro.setGenero(dto.getGenero());
        libro.setEditorial(dto.getEditorial());
        libro.setAnioPub(dto.getAnioPub());
        libro.setEjemplares(dto.getEjemplares());
        libro.setEstado(dto.getEstado());
    }
}
