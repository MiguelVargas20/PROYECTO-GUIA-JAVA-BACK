package com.example.proyecto.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para cambiar la contraseña del usuario.
 * Recibe la contraseña actual (para verificar) y la nueva.
 *
 * PATCH /api/usuarios/{id}/password
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CambiarPasswordDto {

    /** Contraseña actual — para verificar que es el dueño */
    private String passwordActual;

    /** Nueva contraseña a guardar */
    private String passwordNueva;
}