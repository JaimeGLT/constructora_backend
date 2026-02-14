package com.constructora.demo.Movement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@SecurityRequirement(name = "bearerAuth")
public class MovementController {

    final private MovementService movementService;

    @PostMapping("/{materialId}/transaction")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovementResponse> createMovement(@PathVariable int materialId,
            @RequestBody MovementRequest movement) {

        return ResponseEntity.ok(movementService.createMovement(materialId, movement));
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovementResponse>> getMovementHistory() {
        return ResponseEntity.ok(movementService.getRecentActivity());
    }

}
