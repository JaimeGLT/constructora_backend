package com.constructora.demo.Movement;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementResponse {

    TipoMovimiento motivo;
    String descripcion;
    LocalDateTime fechaActividad;

    public static MovementResponse from(Movement movement) {
        return MovementResponse.builder()
                .motivo(movement.getTipo())
                .descripcion(movement.getNota())
                .fechaActividad(movement.getFecha())
                .build();
    }
}