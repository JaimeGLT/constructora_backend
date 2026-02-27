package com.constructora.demo.User;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserUpdateRequest {
    String nombreCompleto;
    String dni;
    Role rol;
    String cargo;
    BigDecimal salarioDiario;
    String password;
    Boolean estado;
}
