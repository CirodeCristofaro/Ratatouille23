package com.INGSW2223_V_06.ratatouille23.utils.DTO;

import com.INGSW2223_V_06.ratatouille23.model.Elementi;

import java.io.Serializable;
import java.math.BigDecimal;

public class DtoElementi implements Serializable {
    //classe utilizzata per tenere traccia degli  elementi
    // che un
    // tavolo
    // ordina durante la creazione

    private Elementi elementiOrdinata;
    private BigDecimal costo;

    private int quantita;

    public DtoElementi() {
    }

    public DtoElementi(Elementi elementiOrdinata, BigDecimal costo, int quantita) {
        this.elementiOrdinata = elementiOrdinata;
        this.costo = costo;
        this.quantita = quantita;
    }

    public Elementi getElementoOrdinata() {
        return elementiOrdinata;
    }

    public void setElementoOrdinata(Elementi elementiOrdinata) {
        this.elementiOrdinata = elementiOrdinata;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "DtoElementi{" +
                "nomeElemento='" + elementiOrdinata.getNomeElemento() + '\'' +
                ", costo=" + costo +
                ", quantita=" + quantita +
                '}';
    }
}
