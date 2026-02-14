package com.constructora.demo.User;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllEmployees(String search) {

        List<User> users;

        if (search == null || search.trim().isEmpty()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.searchEmployees(search);
        }

        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public String updateEmployee(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean current = Boolean.TRUE.equals(user.getEstado());

        user.setEstado(!current);

        userRepository.save(user);

        return "Se ha cambiado el estado con exito";
    }

    public UserResponse userUpdateInfo(Integer id, UserUpdateRequest user) {
        User userFound = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (user.getCargo() != null)
            userFound.setCargo(user.getCargo());

        if (user.getDni() != null)
            userFound.setDni(user.getDni());

        if (user.getNombreCompleto() != null)
            userFound.setNombreCompleto(user.getNombreCompleto());

        if (user.getRol() != null)
            userFound.setRol(user.getRol());

        if (user.getSalarioDiario() != null)
            userFound.setSalarioDiario(user.getSalarioDiario());

        if (user.getEstado() != null)
            userFound.setEstado(user.getEstado());

        if (user.getPassword() != null)
            userFound.setPasswordHash(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userFound);

        return UserMapper.toResponse(userFound);
    }

    public SummaryUserResponse summaryEmployees() {
        long totalEmployees = userRepository.countEmployees();
        long totalActiveEmployees = userRepository.countActivesEmployees();
        BigDecimal totalDailyNomina = userRepository.totalDailyNomina();

        return SummaryUserResponse.builder()
                .empleadosTotales(totalEmployees)
                .empleadosActivos(totalActiveEmployees)
                .nominaDiaria(totalDailyNomina)
                .build();
    }
}