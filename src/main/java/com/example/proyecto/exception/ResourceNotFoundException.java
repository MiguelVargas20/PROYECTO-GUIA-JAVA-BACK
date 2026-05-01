package com.example.proyecto.exception;

public class ResourceNotFoundException extends RuntimeException {
 
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
 
    public ResourceNotFoundException(String recurso, String campo, String valor) {
        super(recurso + " no encontrado con " + campo + ": " + valor);
    }
}