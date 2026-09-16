package com.greentravel.api.dtos;

public record AuthResponseDTO(
        String mensaje,
        String status,
        Object data
) {}