package com.constructora.demo.Auth;

import java.math.BigDecimal;

import com.constructora.demo.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nombreCompleto;
    private String dni;
    private String password;
    private Role rol;
    private String cargo;
    private BigDecimal salarioDiario;

}