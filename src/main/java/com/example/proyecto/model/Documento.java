package com.example.proyecto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Documento {
 
    /** Tipo de documento: CC, TI, CE, PA */
    private TipoDocumento tipo;
 
    /** Número del documento */
    private String numero;
}
 