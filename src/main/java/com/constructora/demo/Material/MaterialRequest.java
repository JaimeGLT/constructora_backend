package com.constructora.demo.Material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialRequest {
    String nombre;
    String descripcion;
    Integer stock;
    Integer maxStock;
}
