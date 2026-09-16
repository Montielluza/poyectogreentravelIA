package com.greentravel.api.services;

import com.greentravel.api.dtos.RegisterRequestDTO;
import com.greentravel.api.models.Role;
import com.greentravel.api.models.Usuario;
import com.greentravel.api.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrar(RegisterRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        Role rolEnum;
        try {
            rolEnum = Role.valueOf("ROLE_" + dto.rol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El rol ingresado no es válido. Debe ser TURISTA o ANFITRION.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setRol(rolEnum);

        usuarioRepository.save(usuario);
    }
}