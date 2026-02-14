package com.constructora.demo.Movement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementRequest {
    TipoMovimiento tipo;
    int cantidad;
    String nota;
}
