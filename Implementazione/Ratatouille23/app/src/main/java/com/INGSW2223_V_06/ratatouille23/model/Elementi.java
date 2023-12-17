package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Elementi implements Serializable {
    @Expose
    @SerializedName("idElemento")
    private Long idElemento;

    @Expose
    @SerializedName("nomeElemento")
    private String nomeElemento;

    @Expose
    @SerializedName("prezzo")
    private BigDecimal prezzo;

    @Expose
    @SerializedName("descrizione")
    private String descrizione;

    @Expose
    @SerializedName("ordine")
    private int ordine;


    public Elementi() {
    }

    public Elementi(String nomeElemento, BigDecimal prezzo, String descrizione) {
        this.nomeElemento = nomeElemento;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
    }

    public Elementi(Long idElemento, String nomeElemento, BigDecimal prezzo, String descrizione,
                    int ordine) {
        this.idElemento = idElemento;
        this.nomeElemento = nomeElemento;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.ordine = ordine;
    }


    public Long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Long idElemento) {
        this.idElemento = idElemento;
    }

    public String getNomeElemento() {
        return nomeElemento;
    }

    public void setNomeElemento(String nomeElemento) {
        this.nomeElemento = nomeElemento;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }



    @Override
    public String toString() {
        return "Elementi{" +
                "idElemento=" + idElemento +
                ", nomeElemento='" + nomeElemento + '\'' +
                ", costo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", ordine=" + ordine +
                '}';
    }
}
