package com.constructora.demo.Dashboard;

import com.constructora.demo.Asistencia.AsistenciaRepository;
import com.constructora.demo.Material.Material;
import com.constructora.demo.Material.MaterialRepository;
import com.constructora.demo.User.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AsistenciaRepository asistenciaRepository;
    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;

    public DashboardSummaryResponse getDashboardSummary() {
        long asistenciasHoy = asistenciaRepository.contarAsistenciasValidasDeHoy(java.time.LocalDate.now());
        long totalEmpleados = userRepository.countActivesEmployees();

        LocalDate hoy = LocalDate.now();

        // 2. FALTAS: Obrero activo que no vino = 100% de descuento
        Double descuentoFaltas = asistenciaRepository.sumarSalariosPorEstadoYUsuarioActivo(hoy, "FALTA");

        // 3. RETRASOS: Obrero activo que llegó tarde = 10% de descuento
        Double sumaSalariosRetrasados = asistenciaRepository.sumarSalariosPorEstadoYUsuarioActivo(hoy, "RETRASO");
        Double descuentoRetrasos = sumaSalariosRetrasados * 0.10;

        // 4. Total a favor de la empresa
        Double totalRetenidoHoy = descuentoFaltas + descuentoRetrasos;

        return DashboardSummaryResponse.builder()
                .asistenciaHoy((int) asistenciasHoy)
                .totalEmpleados((int) totalEmpleados)
                .penalizaciones(totalRetenidoHoy)
                .build();
    }

    public List<DashboardCriticalMaterialResponse> getCriticalStock() {
        List<Material> criticalMaterials = materialRepository.findCriticalStockArticles();

        return criticalMaterials.stream()
                .map(m -> DashboardCriticalMaterialResponse.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .stockActual(m.getStock())
                        .umbral((int) (m.getMaxStock() * 0.1))
                        .estado(m.getStock() <= (m.getMaxStock() * 0.1) ? "Bajo Stock" : "Adventencia")
                        .build())
                .toList();
    }

    public List<DashboardMaterialStateResponse> getUltimosMateriales() {

        // 2. Traes las entidades puras de la base de datos
        List<Material> materiales = materialRepository.findTop3ByOrderByIdDesc();

        return materiales.stream()
                .map(mat -> new DashboardMaterialStateResponse(
                        mat.getId(),
                        mat.getNombre(),
                        mat.getStock() <= (mat.getMaxStock() * 0.1) ? "CRÍTICO"
                                : mat.getStock() <= (mat.getMaxStock() * 0.4) ? "ADVERTENCIA" : "SALUDABLE"))
                .toList();
    }

}
