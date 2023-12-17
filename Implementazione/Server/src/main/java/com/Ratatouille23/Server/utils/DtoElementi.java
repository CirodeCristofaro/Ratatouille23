package com.Ratatouille23.Server.utils;

import com.Ratatouille23.Server.entity.Elementi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DtoElementi {
    private Elementi elementiOrdinata;
    private BigDecimal costo;
    private int quantita;


}
