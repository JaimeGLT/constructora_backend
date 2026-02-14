package com.constructora.demo.User;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    Integer id;
    String nombreCompleto;
    String dni;
    Role rol;
    String cargo;
    BigDecimal salarioDiario;
    boolean estado;
    String ultimoGpsTexto;
    String createdAt;
}
