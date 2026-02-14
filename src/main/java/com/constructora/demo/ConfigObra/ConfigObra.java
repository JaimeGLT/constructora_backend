package com.constructora.demo.ConfigObra;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "config_obra")
@Data
public class ConfigObra {

    @Id
    private Integer id; // Aquí no hay GeneratedValue porque es un ID fijo (ej. 1)

    @Column(name = "latitud_centro", nullable = false)
    private Double latitudCentro;

    @Column(name = "longitud_centro", nullable = false)
    private Double longitudCentro;

    @Column(name = "radio_permitido_metros")
    private Integer radioPermitidoMetros;
}