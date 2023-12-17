package com.Ratatouille23.Server.entity;

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
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long idMenu;

    @Column(unique = true)
    private String nomeMenu;

    @OneToMany(mappedBy = "menuGestito", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("menuGestito")
    private List<Utente> utente;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("menu")
    private List<Categorie> categoria;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(nomeMenu, menu.nomeMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeMenu);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "idMenu=" + idMenu +
                ", nomeMenu='" + nomeMenu + '\'' +
                '}';
    }
}
