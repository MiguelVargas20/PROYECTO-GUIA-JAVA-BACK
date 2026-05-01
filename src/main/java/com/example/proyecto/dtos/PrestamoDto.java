package com.example.proyecto.dtos;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.proyecto.model.EstadoPrestamo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrestamoDto {

    private String idPrest;
    private String idUsuario;
    private List<String> idsLibros;
    private LocalDateTime fPrestamo;
    private LocalDate fLimite;
    private LocalDate fDevolucion;
    private EstadoPrestamo est;
}