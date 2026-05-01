package com.example.proyecto.mapper;

import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Component;

import com.example.proyecto.dtos.PrestamoDto;
import com.example.proyecto.model.Prestamo;

 
@Component
public class PrestamoMapperImpl implements PrestamoMapper {
 
    // Convierte PrestamoDto a entidad Prestamo
    @Override
    public Prestamo toEntity(PrestamoDto dto) {
        if (dto == null) return null;
 
        return Prestamo.builder()
                .id(dto.getIdPrest())
                .usuarioId(dto.getIdUsuario())
                .libroIds(dto.getIdsLibros())
                .fechaPrestamo(dto.getFPrestamo())
                .fechaLimite(dto.getFLimite())
                .fechaDevolucion(dto.getFDevolucion())
                .estado(dto.getEst())
                .build();
    }
 
    // Convierte entidad Prestamo a PrestamoDto
    @Override
    public PrestamoDto toDto(Prestamo prestamo) {
        if (prestamo == null) return null;
 
        return PrestamoDto.builder()
                .idPrest(prestamo.getId())
                .idUsuario(prestamo.getUsuarioId())
                .idsLibros(prestamo.getLibroIds())
                .fPrestamo(prestamo.getFechaPrestamo())
                .fLimite(prestamo.getFechaLimite())
                .fDevolucion(prestamo.getFechaDevolucion())
                .est(prestamo.getEstado())
                .build();
    }
 
    // Convierte lista de Prestamo a lista de PrestamoDto
    @Override
    public List<PrestamoDto> toDtoList(List<Prestamo> prestamos) {
        if (prestamos == null) return null;
        return prestamos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
 
    // Actualiza solo el estado y fechas (no se toca el ID ni el usuario ni los libros)
    @Override
    public void actualizarPrestamo(PrestamoDto dto, Prestamo prestamo) {
        if (dto == null || prestamo == null) return;
 
        prestamo.setFechaLimite(dto.getFLimite());
        prestamo.setFechaDevolucion(dto.getFDevolucion());
        prestamo.setEstado(dto.getEst());
    }
}