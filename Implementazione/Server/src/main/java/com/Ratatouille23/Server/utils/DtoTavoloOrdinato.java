package com.Ratatouille23.Server.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DtoTavoloOrdinato {
    private Long idTavoloOrdinato;
    private int numeroTavolo;
    private BigDecimal totaleCosto;
    private int totaleElementi;
}
