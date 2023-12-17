package com.INGSW2223_V_06.ratatouille23.utils.DTO;

import com.INGSW2223_V_06.ratatouille23.model.Tavolo;

import java.util.List;

public class DtoTavolo {

    private List<DtoElementi> dtoElementi;
    private Tavolo tavolo;


    public DtoTavolo(List<DtoElementi> dtoElementi, Tavolo tavolo) {
        this.dtoElementi = dtoElementi;
        this.tavolo = tavolo;

    }


    @Override
    public String toString() {
        return "DtoTavolo{" +
                "dtoElementi=" + dtoElementi +
                ", tavolo=" + tavolo +
                '}';
    }
}
