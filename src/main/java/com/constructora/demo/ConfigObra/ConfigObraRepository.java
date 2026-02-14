package com.constructora.demo.ConfigObra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigObraRepository extends JpaRepository<com.constructora.demo.ConfigObra.ConfigObra, Integer> {
}
