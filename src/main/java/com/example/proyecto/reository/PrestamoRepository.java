package com.example.proyecto.reository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.proyecto.model.Prestamo;
import com.example.proyecto.model.EstadoPrestamo;

public interface PrestamoRepository extends MongoRepository<Prestamo, String> {

    // Todos los préstamos de un usuario
    List<Prestamo> findByUsuarioId(String usuarioId);

    // Préstamos de un usuario filtrados por estado
    List<Prestamo> findByUsuarioIdAndEstado(String usuarioId, EstadoPrestamo estado);

    // Filtrar por estado general: ACTIVO | DEVUELTO | VENCIDO | RENOVADO
    List<Prestamo> findByEstado(EstadoPrestamo estado);

    // Préstamos que incluyen un libro específico
    @Query("{ 'libroIds': ?0 }")
    List<Prestamo> findByLibroId(String libroId);

    // Préstamos vencidos: ACTIVO y fechaLimite anterior a hoy
    @Query("{ 'estado': 'ACTIVO', 'fechaLimite': { $lt: ?0 } }")
    List<Prestamo> findVencidos(LocalDate hoy);

    // Verificar si un usuario tiene algún préstamo activo
    boolean existsByUsuarioIdAndEstado(String usuarioId, EstadoPrestamo estado);
}