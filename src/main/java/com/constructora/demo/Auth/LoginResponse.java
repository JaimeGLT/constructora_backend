package com.constructora.demo.Auth;

import com.constructora.demo.User.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    String token;
    Role role;
    Integer userId;
    String nombreCompleto;
}
