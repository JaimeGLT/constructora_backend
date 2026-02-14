package com.constructora.demo.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardCriticalMaterialResponse {
    Integer id;
    String nombre;
    Integer stockActual;
    Integer umbral;
    String estado;
}
