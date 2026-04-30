package com.example.proyecto.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Document(collection = "Prestamo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prestamo {
 
    /** ID secuencial personalizado: PREST-0001, PREST-0002, etc. */
    @Id
    private String id;
 
    /** ID del usuario que realiza el préstamo */
    private String usuarioId;
 
    /** Lista de IDs de los libros incluidos en el préstamo */
    private List<String> libroIds;
 
    /** Fecha en que se realizó el préstamo */
    private LocalDateTime fechaPrestamo;
 
    /** Fecha límite para la devolución */
    private LocalDate fechaLimite;
 
    /** Fecha real en que se devolvieron los libros (null si aún no se devuelve) */
    private LocalDate fechaDevolucion;
 
    /** Estado del préstamo: ACTIVO | DEVUELTO | VENCIDO | RENOVADO */
    private EstadoPrestamo estado;
}