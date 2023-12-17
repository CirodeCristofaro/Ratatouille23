package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Categorie implements Serializable {

    @Expose
    @SerializedName("idCategoria")
    private Long idCategoria;

    @Expose
    @SerializedName("nomeCategoria")
    private String nomeCategoria;

    @Expose
    @SerializedName("ordine")
    private int ordine;

    public Categorie() {
    }

    public Categorie(Long idCategoria, String nomeCategoria, int ordine) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.ordine = ordine;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idCategoria=" + idCategoria +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", ordine=" + ordine +
                '}';
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public int getOrdine() {
        return ordine;
    }

    public void setOrdine(int ordine) {
        this.ordine = ordine;
    }


}

