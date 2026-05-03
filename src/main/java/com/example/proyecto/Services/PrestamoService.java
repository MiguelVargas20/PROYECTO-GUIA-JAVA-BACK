package com.example.proyecto.Services;

import java.util.List;

import com.example.proyecto.dtos.*;
import com.example.proyecto.model.*;


public interface PrestamoService {
 
    /** Crea un nuevo préstamo validando disponibilidad de libros y estado del usuario */
    PrestamoDto crearPrestamo(PrestamoDto dto);
 
    /** Obtiene todos los préstamos del sistema */
    List<PrestamoDto> listarPrestamos();
 
    /** Busca un préstamo por su ID */
    PrestamoDto obtenerPorId(String id);
 
    /** Obtiene todos los préstamos de un usuario */
    List<PrestamoDto> obtenerPorUsuario(String usuarioId);
 
    /** Filtra préstamos por estado */
    List<PrestamoDto> obtenerPorEstado(EstadoPrestamo estado);
 
    /** Registra la devolución de un préstamo */
    PrestamoDto registrarDevolucion(String id);
 
    /** Actualiza préstamos vencidos (ACTIVO + fechaLimite pasada → VENCIDO) */
    void actualizarVencidos();
 
    /** Elimina un préstamo por su ID */
    void eliminarPrestamo(String id);
}
 