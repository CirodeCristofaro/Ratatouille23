package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VisualizzaAvviso implements Serializable {
    @Expose
    @SerializedName("idVisualizzaAvviso")
    private Long idVisualizzaAvviso;

    @Expose
    @SerializedName("visualizzazione")
    private TipoVisualizzazione visualizzazione;


    @Expose
    @SerializedName("avvisoRicevuto")
    private Avviso avvisoRicevuto;

    public VisualizzaAvviso() {
    }

    public Long getIdVisualizzaAvviso() {
        return idVisualizzaAvviso;
    }

    public void setIdVisualizzaAvviso(Long idVisualizzaAvviso) {
        this.idVisualizzaAvviso = idVisualizzaAvviso;
    }

    public TipoVisualizzazione getVisualizzazione() {
        return visualizzazione;
    }

    public void setVisualizzazione(TipoVisualizzazione visualizzazione) {
        this.visualizzazione = visualizzazione;
    }

    public Avviso getAvvisoRicevuto() {
        return avvisoRicevuto;
    }

    public void setAvvisoRicevuto(Avviso avvisoRicevuto) {
        this.avvisoRicevuto = avvisoRicevuto;
    }

    public VisualizzaAvviso(Long idVisualizzaAvviso, TipoVisualizzazione visualizzazione, Avviso avvisoRicevuto) {
        this.idVisualizzaAvviso = idVisualizzaAvviso;
        this.visualizzazione = visualizzazione;
        this.avvisoRicevuto = avvisoRicevuto;
    }

    @Override
    public String toString() {
        return "VisualizzaAvviso{" +
                "idVisualizzaAvviso=" + idVisualizzaAvviso +
                ", visualizzazione=" + visualizzazione +
                ", avvisoRicevuto=" + avvisoRicevuto +
                '}';
    }
}
