package com.constructora.demo.Material;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class MaterialController {

    final private MaterialService materialService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MaterialResponse> createMaterial(@RequestBody MaterialRequest material) {
        return ResponseEntity.ok(materialService.createMaterial(material));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MaterialSummaryResponse> materialsSummary() {
        return ResponseEntity.ok(materialService.materialsSummary());
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MaterialResponse>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MaterialResponse> updateMaterial(@PathVariable int id,
            @RequestBody MaterialRequest materialReq) {
        return ResponseEntity.ok(materialService.updateMaterial(id, materialReq));
    }

}
