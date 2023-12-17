package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Allergenici implements Serializable {
    @Expose
    @SerializedName("idAllergenico")
    private Long idAllergenico;

    @Expose
    @SerializedName("nomeAllergenico")
    private String nomeAllergenico;

    public Long getIdAllergenico() {
        return idAllergenico;
    }

    public void setIdAllergenico(Long idAllergenico) {
        this.idAllergenico = idAllergenico;
    }

    public String getNomeAllergenico() {
        return nomeAllergenico;
    }

    public void setNomeAllergenico(String nomeAllergenico) {
        this.nomeAllergenico = nomeAllergenico;
    }

    public Allergenici(Long idAllergenico, String nomeAllergenico) {
        this.idAllergenico = idAllergenico;
        this.nomeAllergenico = nomeAllergenico;
    }

    public Allergenici() {
    }

    @Override
    public String toString() {
        return "Allergenici{" +
                "idAllergenici=" + idAllergenico +
                ", nomeAllergenici='" + nomeAllergenico + '\'' +
                '}';
    }
}
