package com.constructora.demo.Auth;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.constructora.demo.User.Role;
import com.constructora.demo.User.User;
import com.constructora.demo.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public AuthResponse login(LoginRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .dni(request.getDni())
                .nombre_completo(request.getNombre_completo())
                .password_hash(request.getPassword_hash())
                .rol(Role.USER)
                .cargo(request.getCargo())
                .estado(request.getEstado())
                .created_at(request.getCreated_at())
                .salario_diario(request.getSalario_diario())
                .ultimo_gps_texto(request.getUltimo_gps_texto())
                .build();

        userRepository.save(user);

    }
}
