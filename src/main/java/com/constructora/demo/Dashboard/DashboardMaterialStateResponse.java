package com.constructora.demo.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardMaterialStateResponse {
    Integer id;
    String nombre;
    String estado;
}
