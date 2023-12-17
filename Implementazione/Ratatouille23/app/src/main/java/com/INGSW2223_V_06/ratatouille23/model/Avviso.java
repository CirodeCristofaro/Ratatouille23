package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Avviso implements Serializable {
    @Expose
    @SerializedName("idAvviso")
    private Long idAvviso;

    @Expose
    @SerializedName("oggetto")
    private String oggetto;

    @Expose
    @SerializedName("contenuto")
    private String contenuto;

    @Expose
    @SerializedName("data")
    private String data;

    @Expose
    @SerializedName("utenteCreaAvviso")
    private Utente utenteCreaAvviso;

    public Avviso() {
    }

    public Utente getUtenteCreaAvviso() {
        return utenteCreaAvviso;
    }

    public void setUtenteCreaAvviso(Utente utenteCreaAvviso) {
        this.utenteCreaAvviso = utenteCreaAvviso;
    }

    public Long getIdAvviso() {
        return idAvviso;
    }

    public void setIdAvviso(Long idAvviso) {
        this.idAvviso = idAvviso;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Avviso(String oggetto, String contenuto) {
        this.oggetto = oggetto;
        this.contenuto = contenuto;
    }

    @Override
    public String toString() {
        return "Avviso{" +
                "oggetto='" + oggetto + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
