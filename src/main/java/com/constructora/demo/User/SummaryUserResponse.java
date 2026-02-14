package com.constructora.demo.User;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryUserResponse {
    long empleadosTotales;
    long empleadosActivos;
    BigDecimal nominaDiaria;
}
