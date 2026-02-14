package com.constructora.demo.Asistencia;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsistenciaStatusResponseDTO {
    private boolean yaMarco;
    private String horaMarcado; // Ej: "08:02 AM"
    private String estado; // Ej: "A TIEMPO"
}