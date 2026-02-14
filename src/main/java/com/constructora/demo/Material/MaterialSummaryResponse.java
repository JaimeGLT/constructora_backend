package com.constructora.demo.Material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialSummaryResponse {
    long totalArticulos;
    long stockBajo;
    long stockCritico;
}
