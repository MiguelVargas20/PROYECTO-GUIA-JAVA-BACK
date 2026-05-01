package com.example.proyecto.mapper;

import java.util.List;

import com.example.proyecto.dtos.LibroDto;
import com.example.proyecto.model.Libro;

 
public interface LibroMapper {
 
    // Convierte LibroDto a entidad Libro
    Libro toEntity(LibroDto dto);
 
    // Convierte entidad Libro a LibroDto
    LibroDto toDto(Libro libro);
 
    // Convierte lista de Libro a lista de LibroDto
    List<LibroDto> toDtoList(List<Libro> libros);
 
    // Actualiza una entidad Libro existente con datos de un LibroDto
    void actualizarLibro(LibroDto dto, Libro libro);
}
 