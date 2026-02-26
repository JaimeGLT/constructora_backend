package com.constructora.demo.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.constructora.demo.JWT.JwtService;
import com.constructora.demo.User.Role;
import com.constructora.demo.User.User;
import com.constructora.demo.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public LoginResponse login(LoginRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getDni(),
                                                request.getPassword()));
                User user = userRepository.findByDni(request.getDni()).orElseThrow();
                String token = jwtService.getToken(user);

                return LoginResponse.builder()
                                .token(token)
                                .userId(user.getId())
                                .nombreCompleto(user.getNombreCompleto())
                                .role(user.getRol())
                                .build();
        }

        public AuthResponse register(RegisterRequest request) {
                User user = User.builder()
                                .dni(request.getDni())
                                .nombreCompleto(request.getNombreCompleto())
                                .passwordHash(passwordEncoder.encode(request.getPassword()))
                                .rol(request.getRol() != null ? request.getRol() : Role.OBRERO)
                                .cargo(request.getCargo())
                                .estado(true)
                                .salarioDiario(request.getSalarioDiario())
                                .estadoAsistencia("FALTA")
                                .build();

                userRepository.save(user);

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();

        }
}
