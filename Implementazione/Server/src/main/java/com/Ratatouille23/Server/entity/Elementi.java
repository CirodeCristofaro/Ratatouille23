package com.Ratatouille23.Server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Elementi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idElemento;

    @Column(unique = true)
    private String nomeElemento;

    private BigDecimal prezzo;

    @Column(length = 999999)
    private String descrizione;

    private int ordine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("elementi")
    private Categorie categoria;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "appartengono", joinColumns = {
            @JoinColumn(name = "id_Elemento", referencedColumnName = "idElemento")}, inverseJoinColumns = {
            @JoinColumn(name = "id_Allergenico", referencedColumnName = "idAllergenico")})
    @JsonIgnoreProperties("ingredientiAllergenici")
    List<Allergenici> allergenici = new ArrayList<>();


    @OneToMany(mappedBy = "elementiOrdinati", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("elementiOrdinati")
    private List<TavoloOrdinaElemento> tavoloOrdinaElemento = new ArrayList<>();

    @Override
    public String toString() {
        return "Elementi{" +
                "idElemento=" + idElemento +
                ", nomeElemento='" + nomeElemento + '\'' +
                ", prezzo=" + prezzo +
                ", descrizione='" + descrizione + '\'' +
                ", ordine=" + ordine +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elementi elementi = (Elementi) o;
        return Objects.equals(nomeElemento, elementi.nomeElemento) && Objects.equals(prezzo, elementi.prezzo) && Objects.equals(descrizione, elementi.descrizione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeElemento, prezzo, descrizione);
    }
}
