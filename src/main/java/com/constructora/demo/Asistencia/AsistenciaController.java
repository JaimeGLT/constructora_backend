package com.constructora.demo.Asistencia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/attendance")
@SecurityRequirement(name = "bearerAuth")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    @PostMapping("/mark")
    @PreAuthorize("hasRole('OBRERO')")
    public ResponseEntity<String> marcarAsistencia(@RequestBody AsistenciaRequestDTO request) {
        try {
            // NOTA: En un sistema real, el usuarioId NO se envía en el body por seguridad.
            // Se extrae del token JWT de Spring Security. Por ahora, si tu materia no lo
            // exige, déjalo así.
            asistenciaService.registrarAsistencia(request.getUsuarioId(), request.getLatitud(), request.getLongitud());
            return ResponseEntity.ok("Asistencia registrada exitosamente.");

        } catch (IllegalArgumentException e) {
            // Error 400: Está muy lejos de la obra
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (IllegalStateException e) {
            // Error 409: Conflicto - Ya marcó hoy
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            // Error 500: Fallo del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('OBRERO')")
    public ResponseEntity<AsistenciaStatusResponseDTO> obtenerEstadoAsistencia(@RequestParam Integer usuarioId) {
        if (usuarioId == null) {
            return ResponseEntity.badRequest().build();
        }

        AsistenciaStatusResponseDTO status = asistenciaService.verificarEstadoHoy(usuarioId);
        return ResponseEntity.ok(status);
    }
}