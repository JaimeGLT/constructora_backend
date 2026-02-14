package com.constructora.demo.User;

import java.time.format.DateTimeFormatter;

public class UserMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nombreCompleto(user.getNombreCompleto())
                .dni(user.getDni())
                .rol(user.getRol())
                .cargo(user.getCargo())
                .salarioDiario(user.getSalarioDiario())
                .estado(Boolean.TRUE.equals(user.getEstado()))
                .ultimoGpsTexto(user.getUltimoGpsTexto())
                .createdAt(
                        user.getCreatedAt() != null
                                ? user.getCreatedAt().format(FORMATTER)
                                : null)
                .build();
    }
}
