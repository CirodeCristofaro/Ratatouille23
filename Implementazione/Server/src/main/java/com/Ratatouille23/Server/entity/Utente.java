package com.Ratatouille23.Server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;

    private String nome;

    private String cognome;

    @Column(unique = true)
    private String email;


    private String password;


    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;

    private Date primoAccesso;


    //relazione 1-* con Avviso
    @OneToMany(mappedBy = "utenteCreaAvviso")
    @JsonIgnoreProperties("utenteCreaAvviso")
    List<Avviso> creaAvviso = new ArrayList<>();


    //relazione con menu *-1
    @ManyToOne(fetch = FetchType.EAGER)//@JsonIgnore
    @JoinColumn(name = "id_menu")
    @JsonIgnoreProperties("utente")
    private Menu menuGestito;

    @ManyToMany(mappedBy = "utenteGestisceTavolo")
    @JsonIgnoreProperties("utenteGestisceTavolo")
    private List<Tavolo> utenteCheGestisceIlTavolo = new ArrayList<>();

    @Override
    public String toString() {
        return "Utente [idUtente=" + idUtente + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email
                + ", tipoUtente=" + tipoUtente + ", primoAccesso=" + primoAccesso + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(nome, utente.nome)
                && Objects.equals(cognome, utente.cognome)
                && Objects.equals(email, utente.email)
                && Objects.equals(password, utente.password)
                && tipoUtente == utente.tipoUtente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, email, password, tipoUtente);
    }
}
