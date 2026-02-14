package com.constructora.demo.Material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialResponse {
    Integer id;
    String nombre;
    String descripcion;
    Integer stock;
    Integer maxStock;
    Integer porcentajeCantidad;
    String estado;

    public static MaterialResponse from(Material material) {
        return MaterialResponse.builder()
                .id(material.getId())
                .nombre(material.getNombre())
                .descripcion(material.getDescripcion())
                .stock(material.getStock())
                .maxStock(material.getMaxStock())
                .porcentajeCantidad(material.getPorcentajeCantidad())
                .estado(material.getStock() <= (material.getMaxStock() * 0.1) ? "Nivel Crítico"
                        : material.getStock() <= (material.getMaxStock() * 0.4) ? "Stock Bajo" : "Saludable")
                .build();
    }
}
