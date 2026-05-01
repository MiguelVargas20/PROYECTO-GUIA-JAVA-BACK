package com.example.proyecto.mapper;

import java.util.List;

import com.example.proyecto.dtos.PrestamoDto;
import com.example.proyecto.model.Prestamo;

 
public interface PrestamoMapper {
 
    // Convierte PrestamoDto a entidad Prestamo
    Prestamo toEntity(PrestamoDto dto);
 
    // Convierte entidad Prestamo a PrestamoDto
    PrestamoDto toDto(Prestamo prestamo);
 
    // Convierte lista de Prestamo a lista de PrestamoDto
    List<PrestamoDto> toDtoList(List<Prestamo> prestamos);
 
    // Actualiza una entidad Prestamo existente con datos de un PrestamoDto
    void actualizarPrestamo(PrestamoDto dto, Prestamo prestamo);
}
 