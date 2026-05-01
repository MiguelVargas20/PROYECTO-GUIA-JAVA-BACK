package com.example.proyecto.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.proyecto.reository.*;
import com.example.proyecto.dtos.*;
import com.example.proyecto.model.*;
import com.example.proyecto.mapper.*;

@Service
public class PrestamoServiceImpl implements PrestamoService {
 
    private final PrestamoRepository prestamoRepo;
    private final LibroRepository libroRepo;
    private final UsuarioRepository usuarioRepo;
    private final PrestamoMapper prestamoMapper;
 
    public PrestamoServiceImpl(
            PrestamoRepository prestamoRepo,
            LibroRepository libroRepo,
            UsuarioRepository usuarioRepo,
            PrestamoMapper prestamoMapper) {
        this.prestamoRepo = prestamoRepo;
        this.libroRepo = libroRepo;
        this.usuarioRepo = usuarioRepo;
        this.prestamoMapper = prestamoMapper;
    }
 
    @Override
    @Transactional
    public PrestamoDto crearPrestamo(PrestamoDto dto) {
 
        // 1. Validar que el usuario existe
        if (!usuarioRepo.existsById(dto.getUsuarioId())) {
            throw new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId());
        }
 
        // 2. Validar que el usuario no tenga ya un préstamo ACTIVO
        if (prestamoRepo.existsByUsuarioIdAndEstado(dto.getUsuarioId(), EstadoPrestamo.ACTIVO)) {
            throw new RuntimeException("El usuario ya tiene un préstamo activo pendiente de devolución.");
        }
 
        // 3. Validar que se enviaron libros
        if (dto.getLibroIds() == null || dto.getLibroIds().isEmpty()) {
            throw new RuntimeException("El préstamo debe incluir al menos un libro.");
        }
 
        // 4. Validar disponibilidad de cada libro y descontar ejemplares
        for (String libroId : dto.getLibroIds()) {
            Libro libro = libroRepo.findById(libroId)
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + libroId));
 
            if (libro.getEjemplaresDisponibles() <= 0) {
                throw new RuntimeException("El libro '" + libro.getTitulo() + "' no tiene ejemplares disponibles.");
            }
 
            // Descontar un ejemplar disponible
            libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() - 1);
 
            // Si ya no quedan ejemplares, marcar como AGOTADO
            if (libro.getEjemplaresDisponibles() == 0) {
                libro.setEstado(EstadoLibro.AGOTADO);
            }
 
            libroRepo.save(libro);
        }
 
        // 5. Generar ID secuencial tipo PREST-XXXX (usando UUID corto)
        String idPrestamo = "PREST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
 
        // 6. Crear el préstamo
        Prestamo prestamo = Prestamo.builder()
                .id(idPrestamo)
                .usuarioId(dto.getUsuarioId())
                .libroIds(dto.getLibroIds())
                .fechaPrestamo(LocalDateTime.now())
                .fechaLimite(dto.getFechaLimite() != null
                        ? dto.getFechaLimite()
                        : LocalDate.now().plusDays(15)) // 15 días por defecto
                .fechaDevolucion(null)
                .estado(EstadoPrestamo.ACTIVO)
                .build();
 
        return prestamoMapper.toDto(prestamoRepo.save(prestamo));
    }
 
    @Override
    public List<PrestamoDto> listarPrestamos() {
        return prestamoRepo.findAll().stream()
                .map(prestamoMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public PrestamoDto obtenerPorId(String id) {
        Prestamo prestamo = prestamoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        return prestamoMapper.toDto(prestamo);
    }
 
    @Override
    public List<PrestamoDto> obtenerPorUsuario(String usuarioId) {
        return prestamoRepo.findByUsuarioId(usuarioId).stream()
                .map(prestamoMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    public List<PrestamoDto> obtenerPorEstado(EstadoPrestamo estado) {
        return prestamoRepo.findByEstado(estado).stream()
                .map(prestamoMapper::toDto)
                .collect(Collectors.toList());
    }
 
    @Override
    @Transactional
    public PrestamoDto registrarDevolucion(String id) {
        Prestamo prestamo = prestamoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
 
        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new RuntimeException("Este préstamo ya fue devuelto.");
        }
 
        // Devolver ejemplares a cada libro
        for (String libroId : prestamo.getLibroIds()) {
            libroRepo.findById(libroId).ifPresent(libro -> {
                libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles() + 1);
                // Si estaba AGOTADO, vuelve a DISPONIBLE
                if (libro.getEstado() == EstadoLibro.AGOTADO) {
                    libro.setEstado(EstadoLibro.DISPONIBLE);
                }
                libroRepo.save(libro);
            });
        }
 
        // Registrar devolución
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
 
        return prestamoMapper.toDto(prestamoRepo.save(prestamo));
    }
 
    @Override
    @Transactional
    public void actualizarVencidos() {
        // Busca préstamos ACTIVO con fechaLimite anterior a hoy y los marca como VENCIDO
        List<Prestamo> vencidos = prestamoRepo.findVencidos(LocalDate.now());
        for (Prestamo p : vencidos) {
            p.setEstado(EstadoPrestamo.VENCIDO);
            prestamoRepo.save(p);
        }
        System.out.println("Préstamos vencidos actualizados: " + vencidos.size());
    }
 
    @Override
    public void eliminarPrestamo(String id) {
        if (!prestamoRepo.existsById(id)) {
            throw new RuntimeException("Préstamo no encontrado con ID: " + id);
        }
        prestamoRepo.deleteById(id);
    }
}