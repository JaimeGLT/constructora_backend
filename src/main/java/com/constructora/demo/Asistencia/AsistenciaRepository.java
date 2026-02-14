package com.constructora.demo.Asistencia;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    Optional<Asistencia> findByUsuarioIdAndFecha(Integer usuarioId, LocalDate fecha);

    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.fecha = :fecha AND a.estado IN ('A TIEMPO', 'RETRASO')")
    long contarAsistenciasValidasDeHoy(@Param("fecha") LocalDate fecha);

    // Calcula la suma de salarios cruzando la fecha, el tipo de asistencia y
    // asegurando que el obrero no esté despedido
    @Query("SELECT COALESCE(SUM(u.salarioDiario), 0.0) " +
            "FROM Asistencia a JOIN User u ON a.usuarioId = u.id " +
            "WHERE a.fecha = :fecha " +
            "AND a.estado = :estadoAsistencia " +
            "AND u.estado = true") // EL ESCUDO: Solo usuarios activos
    Double sumarSalariosPorEstadoYUsuarioActivo(@Param("fecha") LocalDate fecha,
            @Param("estadoAsistencia") String estadoAsistencia);
}
