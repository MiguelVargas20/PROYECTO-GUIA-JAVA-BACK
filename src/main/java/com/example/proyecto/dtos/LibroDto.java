package com.example.proyecto.dtos;

import java.util.List;

import com.example.proyecto.model.EstadoLibro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroDto {
 
    private String id;
    private String titulo;
    private List<String> autores;
    private String genero;
    private String editorial;
    private int anioPub;
    private int ejemplares;
    private int ejemplaresDisponibles;
    private EstadoLibro estado;
}
 