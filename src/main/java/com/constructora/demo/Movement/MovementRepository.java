package com.constructora.demo.Movement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Integer> {
    List<Movement> findAllByOrderByFechaDesc();
}
