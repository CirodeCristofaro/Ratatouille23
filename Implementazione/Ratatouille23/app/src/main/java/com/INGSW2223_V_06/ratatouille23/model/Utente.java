package com.INGSW2223_V_06.ratatouille23.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Utente implements Serializable {
    @Expose
    @SerializedName("idUtente")
    private Long id;

    @Expose
    @SerializedName("nome")
    private String nome;

    @Expose
    @SerializedName("cognome")
    private String cognome;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("tipoUtente")
    private TipoUtente tipoUtente;

    @Expose
    @SerializedName("primoAccesso")
    private String primoAccesso;


    public Utente() {
    }

    public Utente(Long id, String nome, String cognome, String email, String password, TipoUtente tipoUtente, String primoAccesso) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.tipoUtente = tipoUtente;
        this.primoAccesso = primoAccesso;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tipoUtente=" + tipoUtente +
                ", primoAccesso='" + primoAccesso + '\'' +
                //", utenteCheGestisceIlTavolo=" + utenteCheGestisceIlTavolo +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(TipoUtente tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

    public String getPrimoAccesso() {
        return primoAccesso;
    }

    public void setPrimoAccesso(String primoAccesso) {
        this.primoAccesso = primoAccesso;
    }


}
