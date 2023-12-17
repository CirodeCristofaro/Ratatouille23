package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TavoloOrdinaElemento implements Serializable {

    @Expose
    @SerializedName("idTavoloOrdinaElemento")
    private Long idTavoloOrdinaElemento;

    @Expose
    @SerializedName("tavolo")
    private Tavolo tavolo;

    @Expose
    @SerializedName("elementiOrdinati")
    private Elementi elementiOrdinati;

    @Expose
    @SerializedName("quantita")
    private int quantita;

    public TavoloOrdinaElemento() {
    }

    public TavoloOrdinaElemento(Long idTavoloOrdinaElemento, Tavolo tavolo, Elementi elementiOrdinati, int quantita) {
        this.idTavoloOrdinaElemento = idTavoloOrdinaElemento;
        this.tavolo = tavolo;
        this.elementiOrdinati = elementiOrdinati;
        this.quantita = quantita;
    }

    public Long getIdTavoloOrdinaElemento() {
        return idTavoloOrdinaElemento;
    }

    public void setIdTavoloOrdinaElemento(Long idTavoloOrdinaElemento) {
        this.idTavoloOrdinaElemento = idTavoloOrdinaElemento;
    }

    public Tavolo getTavolo() {
        return tavolo;
    }

    public void setTavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    public Elementi getElementoOrdinate() {
        return elementiOrdinati;
    }

    public void setElementoOrdinate(Elementi elementiOrdinate) {
        this.elementiOrdinati = elementiOrdinate;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "TavoloOrdinaElemento{" +
                "idTavoloOrdinaElemento=" + idTavoloOrdinaElemento +
                ", tavolo=" + tavolo +
                ", ElementoOrdinate=" + elementiOrdinati +
                ", quantita=" + quantita +
                '}';
    }
}
