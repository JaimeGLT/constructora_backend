package com.constructora.demo.Material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("SELECT COUNT(m) FROM Material m")
    long countArticles();

    @Query("SELECT COUNT(m) FROM Material m WHERE m.stock < (m.maxStock / 2)")
    long countLowStockArticles();

    @Query("SELECT COUNT(m) FROM Material m WHERE m.stock <= (m.maxStock * 0.1)")
    long countCriticalStockArticles();
}
