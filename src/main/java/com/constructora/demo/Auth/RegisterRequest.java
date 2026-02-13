package com.constructora.demo.Auth;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    String nombre_completo;
    String dni;
    String password_hash;
    Role rol;
    String cargo;
    BigDecimal salario_diario;
    Boolean estado;
    String ultimo_gps_texto;    
    LocalDateTime created_at;


}
