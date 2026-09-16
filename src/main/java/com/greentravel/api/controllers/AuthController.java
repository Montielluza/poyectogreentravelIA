package com.greentravel.api.controllers;

import com.greentravel.api.dtos.AuthResponseDTO;
import com.greentravel.api.dtos.RegisterRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrarUsuario(@Valid @RequestBody RegisterRequestDTO dto) {
        AuthResponseDTO response = new AuthResponseDTO(
                "Estructura inicial recibida correctamente",
                "SUCCESS",
                Map.of("email", dto.email(), "rol", dto.rol())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}