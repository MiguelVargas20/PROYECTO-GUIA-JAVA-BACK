package com.example.proyecto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Direccion {
 
    /** Ciudad de residencia */
    private String ciudad;
 
    /** Dirección de residencia (calle, carrera, etc.) */
    private String direccion;
}
 