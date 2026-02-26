package com.constructora.demo.Asistencia;

import org.springframework.stereotype.Service;

import com.constructora.demo.ConfigObra.ConfigObra;
import com.constructora.demo.ConfigObra.ConfigObraRepository;
import com.constructora.demo.User.User;
import com.constructora.demo.User.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final ConfigObraRepository configObraRepository;
    private final UserRepository userRepository;

    // Constante inmutable para la zona horaria. Esto evita errores tipográficos.
    private static final ZoneId ZONA_BOLIVIA = ZoneId.of("America/La_Paz");

    @Scheduled(cron = "0 0 0 * * *", zone = "America/La_Paz")
    @Transactional
    public void resetAttendanceStatus() {
        // Opción 1: Query nativa o JPQL para eficiencia masiva
        userRepository.updateAllAttendanceStatus("FALTA");
    }

    // Inyección de dependencias por constructor
    public AsistenciaService(AsistenciaRepository asistenciaRepository, ConfigObraRepository configObraRepository,
            UserRepository userRepository) {
        this.asistenciaRepository = asistenciaRepository;
        this.configObraRepository = configObraRepository;
        this.userRepository = userRepository;
    }

    public void registrarAsistencia(Integer usuarioId, double latObrero, double lonObrero) {
        // 1. Obtener la configuración
        ConfigObra config = configObraRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Configuración de obra no encontrada."));

        // Verificar que el usuario exista
        User usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        // 2. Calcular distancia
        double distancia = calcularDistancia(latObrero, lonObrero, config.getLatitudCentro(),
                config.getLongitudCentro());

        // 3. Validar radio
        if (distancia > config.getRadioPermitidoMetros()) {
            throw new IllegalArgumentException(
                    "Estás fuera del radio permitido. Distancia actual: " + Math.round(distancia) + " metros.");
        }

        // --- NUEVA LÓGICA DE NEGOCIO ESTRICTA PARA EL TIEMPO ---
        LocalTime horaActual = LocalTime.now(ZONA_BOLIVIA);
        LocalTime limiteATiempo = LocalTime.of(8, 30); // Hasta las 08:30 AM es A TIEMPO
        LocalTime limiteRetraso = LocalTime.of(11, 59); // Hasta las 11:59 AM es RETRASO

        // Calculamos el estado de acuerdo a la hora en la zona de la obra
        String estadoCalculado;
        if (!horaActual.isAfter(limiteATiempo)) {
            estadoCalculado = "A TIEMPO"; // 00:00 a 08:30
        } else if (!horaActual.isAfter(limiteRetraso)) {
            estadoCalculado = "RETRASO"; // 08:31 a 11:59
        } else {
            estadoCalculado = "FALTA"; // 12:00 en adelante
        }
        // -------------------------------------------------------

        // 4. Construir y guardar la entidad
        Asistencia asistencia = new Asistencia();
        asistencia.setUsuarioId(usuarioId);
        asistencia.setFecha(LocalDate.now(ZONA_BOLIVIA)); // Corregido: Fecha atada a la zona
        asistencia.setHoraEntrada(horaActual); // Usamos la hora capturada arriba
        asistencia.setLatitud(latObrero);
        asistencia.setLongitud(lonObrero);
        asistencia.setEstado(estadoCalculado);

        usuario.setEstadoAsistencia(estadoCalculado);

        try {
            asistenciaRepository.save(asistencia);
            userRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Ya has registrado tu asistencia el día de hoy.");
        }
    }

    // El motor matemático
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la tierra en km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // Convertir a metros
    }

    public AsistenciaStatusResponseDTO verificarEstadoHoy(Integer usuarioId) {
        // CORREGIDO: Ahora la verificación también usa la zona horaria correcta
        LocalDate hoy = LocalDate.now(ZONA_BOLIVIA);
        Optional<Asistencia> asistenciaHoy = asistenciaRepository.findByUsuarioIdAndFecha(usuarioId, hoy);

        if (asistenciaHoy.isPresent()) {
            Asistencia registro = asistenciaHoy.get();
            // Formatear la hora al estilo "08:02 AM" para la UI
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String horaFormateada = registro.getHoraEntrada().format(formatter);

            return new AsistenciaStatusResponseDTO(true, horaFormateada, registro.getEstado());
        } else {
            // Si no hay registro, devuelve falso y nulos
            return new AsistenciaStatusResponseDTO(false, null, null);
        }
    }
}