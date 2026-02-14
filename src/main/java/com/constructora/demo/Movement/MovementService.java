package com.constructora.demo.Movement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.constructora.demo.Material.Material;
import com.constructora.demo.Material.MaterialRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementService {

    final private MaterialRepository materialRepository;
    final private MovementRepository movementRepository;

    @Transactional
    public MovementResponse createMovement(int materialId, MovementRequest movementReq) {

        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new RuntimeException("Material no encontrado"));

        if (movementReq.getTipo() == TipoMovimiento.SALIDA && movementReq.getCantidad() > material.getStock())
            throw new IllegalArgumentException(
                    "La cantidad no puede ser mayor al stock actual: " + material.getStock());
        else if (movementReq.getTipo() == TipoMovimiento.ENTRADA
                && movementReq.getCantidad() > (material.getMaxStock() - material.getStock()))
            throw new IllegalArgumentException(
                    "No puedes añadir mas de: " + (material.getMaxStock() - material.getStock()) + " unidades");

        // Actualizar stock
        if (movementReq.getTipo() == TipoMovimiento.ENTRADA) {
            material.setStock(material.getStock() + movementReq.getCantidad());
        } else {
            material.setStock(material.getStock() - movementReq.getCantidad());
        }
        materialRepository.save(material);

        Movement movement = Movement.builder()
                .material(material)
                .tipo(movementReq.getTipo())
                .cantidad(movementReq.getCantidad())
                .nota(movementReq.getNota())
                .build();
        movementRepository.save(movement);

        return MovementResponse.builder()
                .motivo(movement.getTipo())
                .descripcion(movementReq.getNota())
                .fechaActividad(movement.getFecha())
                .build();
    }

    public List<MovementResponse> getRecentActivity() {
        return movementRepository.findAllByOrderByFechaDesc()
                .stream()
                .map(MovementResponse::from)
                .toList();
    }

}
