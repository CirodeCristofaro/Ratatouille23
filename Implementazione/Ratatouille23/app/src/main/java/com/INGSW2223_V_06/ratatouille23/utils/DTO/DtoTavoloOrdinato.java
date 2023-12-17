package com.INGSW2223_V_06.ratatouille23.utils.DTO;

import java.math.BigDecimal;

public class DtoTavoloOrdinato {
    private Long idTavoloOrdinato;
    private int numeroTavolo;
    private BigDecimal totaleCosto;
    private int totaleElementi;

    public DtoTavoloOrdinato(Long idTavoloOrdinato, int numeroTavolo, BigDecimal totaleCosto, int totaleElementi) {
        this.idTavoloOrdinato = idTavoloOrdinato;
        this.numeroTavolo = numeroTavolo;
        this.totaleCosto = totaleCosto;
        this.totaleElementi = totaleElementi;
    }

    public DtoTavoloOrdinato() {
    }

    public Long getIdTavoloOrdinato() {
        return idTavoloOrdinato;
    }

    public void setIdTavoloOrdinato(Long idTavoloOrdinato) {
        this.idTavoloOrdinato = idTavoloOrdinato;
    }

    public int getNumeroTavolo() {
        return numeroTavolo;
    }

    public void setNumeroTavolo(int numeroTavolo) {
        this.numeroTavolo = numeroTavolo;
    }

    public BigDecimal getTotaleCosto() {
        return totaleCosto;
    }

    public void setTotaleCosto(BigDecimal totaleCosto) {
        this.totaleCosto = totaleCosto;
    }

    public int getTotaleElementi() {
        return totaleElementi;
    }

    public void setTotaleElementi(int totaleElementi) {
        this.totaleElementi = totaleElementi;
    }

    @Override
    public String toString() {
        return "DtoTavoloOrdinato{" +
                "idTavoloOrdinato=" + idTavoloOrdinato +
                ", numeroTavolo=" + numeroTavolo +
                ", totaleCosto=" + totaleCosto +
                ", totaleElemento=" + totaleElementi +
                '}';
    }
}
