package com.constructora.demo.Dashboard;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboarService;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
        return ResponseEntity.ok(dashboarService.getDashboardSummary());
    }

    @GetMapping("/critical-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DashboardCriticalMaterialResponse>> getCriticalStock() {
        return ResponseEntity.ok(dashboarService.getCriticalStock());
    }

    @GetMapping("/material-states")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DashboardMaterialStateResponse>> getMaterialStates() {
        return ResponseEntity.ok(dashboarService.getUltimosMateriales());
    }

}
