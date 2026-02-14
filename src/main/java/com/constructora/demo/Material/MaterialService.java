package com.constructora.demo.Material;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService {

    final private MaterialRepository materialRepository;

    public MaterialResponse createMaterial(MaterialRequest materialRequest) {

        int porcentajeCalculated = 0;

        if (materialRequest.getMaxStock() > 0) {
            porcentajeCalculated = (int) Math.round(
                    (materialRequest.getStock() * 100.0) / materialRequest.getMaxStock());
        }

        Material material = Material.builder()
                .nombre(materialRequest.getNombre())
                .descripcion(materialRequest.getDescripcion())
                .stock(materialRequest.getStock())
                .maxStock(materialRequest.getMaxStock())
                .porcentajeCantidad(porcentajeCalculated)
                .build();

        materialRepository.save(material);

        return MaterialResponse.builder()
                .id(material.getId())
                .nombre(material.getNombre())
                .descripcion(material.getDescripcion())
                .stock(material.getStock())
                .maxStock(material.getMaxStock())
                .porcentajeCantidad(porcentajeCalculated)
                .build();
    }

    public MaterialSummaryResponse materialsSummary() {
        long totalArticles = materialRepository.countArticles();
        long lowStockArticles = materialRepository.countLowStockArticles();
        long criticalStockArticles = materialRepository.countCriticalStockArticles();

        return MaterialSummaryResponse.builder()
                .totalArticulos(totalArticles)
                .stockBajo(lowStockArticles)
                .stockCritico(criticalStockArticles)
                .build();
    }

    public List<MaterialResponse> getAllMaterials() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialResponse::from)
                .toList();
    }

    public MaterialResponse updateMaterial(int id, MaterialRequest materialReq) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        if (materialReq.getNombre() != null)
            material.setNombre(materialReq.getNombre());

        if (materialReq.getDescripcion() != null)
            material.setDescripcion(materialReq.getDescripcion());

        // Actualizar maxStock
        if (materialReq.getMaxStock() != null) {
            if (materialReq.getMaxStock() < material.getStock()) {
                throw new IllegalArgumentException("El maxStock no puede ser menor al stock actual");
            }
            material.setMaxStock(materialReq.getMaxStock());
        }

        // Actualizar stock
        if (materialReq.getStock() != null) {
            if (materialReq.getStock() > material.getMaxStock()) {
                throw new IllegalArgumentException("El stock no puede superar el maxStock");
            }
            material.setStock(materialReq.getStock());
        }

        // Recalcular porcentaje
        int porcentaje = 0;
        if (material.getMaxStock() > 0) {
            porcentaje = (int) Math.round(
                    (material.getStock() * 100.0) / material.getMaxStock());
        }
        material.setPorcentajeCantidad(porcentaje);

        materialRepository.save(material);

        return MaterialResponse.from(material);
    }

}
