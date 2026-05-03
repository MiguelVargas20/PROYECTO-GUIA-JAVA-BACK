package com.example.proyecto.Services;


import java.util.List;

import com.example.proyecto.dtos.LibroDto;
import com.example.proyecto.model.EstadoLibro;


public interface LibroService {
 
    /** Registra un nuevo libro en el sistema */
    LibroDto crearLibro(LibroDto dto);
 
    /** Obtiene todos los libros */
    List<LibroDto> listarLibros();
 
    /** Busca un libro por su ID */
    LibroDto obtenerPorId(String id);
 
    /** Busca libros por título (búsqueda parcial) */
    List<LibroDto> buscarPorTitulo(String titulo);
 
    /** Busca libros por autor */
    List<LibroDto> buscarPorAutor(String autor);
 
    /** Busca libros por género */
    List<LibroDto> buscarPorGenero(String genero);
 
    /** Filtra libros por estado */
    List<LibroDto> obtenerPorEstado(EstadoLibro estado);
 
    /** Actualiza los datos de un libro */
    LibroDto actualizarLibro(String id, LibroDto dto);
 
    /** Elimina un libro del sistema */
    void eliminarLibro(String id);
}
 