package com.constructora.demo.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByDni(String dni);

    @Query("""
            SELECT u FROM User u
            WHERE LOWER(u.nombreCompleto) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    List<User> searchEmployees(@Param("search") String search);

    @Query("SELECT COUNT(u) from User u")
    long countEmployees();

    @Query("SELECT COUNT(u) from User u WHERE u.estado = true")
    long countActivesEmployees();

    @Query("""
                SELECT COALESCE(SUM(u.salarioDiario), 0)
                FROM User u
            """)
    BigDecimal totalDailyNomina();

}
