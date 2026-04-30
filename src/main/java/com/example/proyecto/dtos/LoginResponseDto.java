package com.example.proyecto.dtos;

import java.util.List;

import com.example.proyecto.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
 
    private String token;
    private String username;
    private List<Rol> roles;
}
 