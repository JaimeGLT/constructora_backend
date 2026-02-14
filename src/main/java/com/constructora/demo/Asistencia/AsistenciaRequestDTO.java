package com.constructora.demo.Asistencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaRequestDTO {
    Integer usuarioId; // En un sistema real, esto se extrae del token JWT, no se envía en el body;
    Double latitud;
    Double longitud;
}
