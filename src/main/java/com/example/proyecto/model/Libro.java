package com.example.proyecto.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Document(collection = "Libro")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Libro {
 
    @Id
    private String id;
 
    /** Título del libro */
    private String titulo;
 
    /** Lista de autores del libro */
    private List<String> autores;
 
    /** Género literario */
    private String genero;
 
    /** Editorial que publicó el libro */
    private String editorial;
 
    /** Año de publicación */
    private int anioPub;
 
    /** Cantidad total de ejemplares */
    private int ejemplares;
 
    /** Cantidad de ejemplares disponibles para préstamo */
    private int ejemplaresDisponibles;
 
    /** Estado del libro: DISPONIBLE | AGOTADO | DADO_DE_BAJA */
    private EstadoLibro estado;
}