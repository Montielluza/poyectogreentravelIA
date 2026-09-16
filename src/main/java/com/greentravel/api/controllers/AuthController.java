package com.greentravel.api.controllers;

import com.greentravel.api.dtos.AuthResponseDTO;
import com.greentravel.api.dtos.RegisterRequestDTO;
import com.greentravel.api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints para registro y gestión de usuarios")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Registra un usuario en GreenTravel encriptando su clave con BCrypt.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Correo duplicado o datos inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registrarUsuario(@Valid @RequestBody RegisterRequestDTO dto) {
        try {
            authService.registrar(dto);
            AuthResponseDTO response = new AuthResponseDTO(
                    "Usuario registrado exitosamente en GreenTravel.",
                    "SUCCESS",
                    Map.of("email", dto.email(), "rol", dto.rol())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            AuthResponseDTO errorResponse = new AuthResponseDTO(
                    e.getMessage(),
                    "ERROR",
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}