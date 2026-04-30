package com.example.proyecto.reository;

import java.util.List;
 
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.proyecto.model.Libro;
import com.example.proyecto.model.EstadoLibro;

public interface LibroRepository extends MongoRepository<Libro, String> {
 
    // Buscar por título (coincidencia parcial, sin importar mayúsculas)
    @Query("{ 'titulo': { $regex: ?0, $options: 'i' } }")
    List<Libro> findByTituloContaining(String titulo);
 
    // Buscar por género
    List<Libro> findByGenero(String genero);
 
    // Buscar por editorial
    List<Libro> findByEditorial(String editorial);
 
    // Buscar libros que contengan un autor específico en la lista
    @Query("{ 'autores': ?0 }")
    List<Libro> findByAutor(String autor);
 
    // Filtrar por estado: DISPONIBLE | AGOTADO | DADO_DE_BAJA
    List<Libro> findByEstado(EstadoLibro estado);
 
    // Verificar si existe un libro por título y editorial (evitar duplicados)
    boolean existsByTituloAndEditorial(String titulo, String editorial);
}
 