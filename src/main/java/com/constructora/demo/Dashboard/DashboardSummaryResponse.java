package com.constructora.demo.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardSummaryResponse {
    private Integer asistenciaHoy;
    private Integer totalEmpleados;
    private Double penalizaciones;
}
