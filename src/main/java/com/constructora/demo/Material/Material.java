package com.constructora.demo.Material;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materiales")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(length = 50, nullable = false)
    String nombre;
    @Column(length = 255)
    String descripcion;
    Integer stock;
    @Column(name = "stock_maximo")
    Integer maxStock;
    @Column(name = "porcentaje_cantidad")
    Integer porcentajeCantidad;
}
