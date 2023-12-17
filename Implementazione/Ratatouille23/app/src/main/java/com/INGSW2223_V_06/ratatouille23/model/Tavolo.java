package com.INGSW2223_V_06.ratatouille23.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tavolo implements Serializable {
    @Expose
    @SerializedName("idTavolo")
    private Long idTavolo;
    @Expose
    @SerializedName("numeroTavolo")
    private int numeroTavolo;

    public Tavolo() {
    }

    public Tavolo(Long idTavolo, int numeroTavolo) {
        this.idTavolo = idTavolo;
        this.numeroTavolo = numeroTavolo;
    }

    public Long getIdTavolo() {
        return idTavolo;
    }

    public void setIdTavolo(Long idTavolo) {
        this.idTavolo = idTavolo;
    }

    public int getNumeroTavolo() {
        return numeroTavolo;
    }

    public void setNumeroTavolo(int numeroTavolo) {
        this.numeroTavolo = numeroTavolo;
    }


    @Override
    public String toString() {
        return "Tavolo{" +
                "idTavolo=" + idTavolo +
                ", nTavolo=" + numeroTavolo +

                '}';
    }
}
