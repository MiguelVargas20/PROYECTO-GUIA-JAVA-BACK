package com.example.proyecto.Services;

import com.example.proyecto.dtos.LoginDto;
import com.example.proyecto.dtos.LoginResponseDto;

public interface AuthService {
 
    /** Valida credenciales y retorna datos del usuario autenticado (token será null hasta implementar JWT) */
    LoginResponseDto login(LoginDto dto);
}
 