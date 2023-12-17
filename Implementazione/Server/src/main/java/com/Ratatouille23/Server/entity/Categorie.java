package com.Ratatouille23.Server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;


    @Column(unique = true)
    private String nomeCategoria;


    private int ordine;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu")
    @JsonIgnoreProperties("categoria")
    @JsonIgnore
    private Menu menu;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("categoria")
    private List<Elementi> elementi;

    @Override
    public String toString() {
        return "Categorie [idCategoria=" + idCategoria + ", nomeCategoria=" + nomeCategoria + ", ordine=" + ordine
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return Objects.equals(nomeCategoria, categorie.nomeCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeCategoria);
    }
}
