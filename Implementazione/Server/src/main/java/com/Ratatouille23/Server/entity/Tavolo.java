package com.Ratatouille23.Server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Tavolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTavolo;

    @Column(unique = true)
    private int numeroTavolo;


    @ManyToMany
    @JoinTable(name = "gestisce",
            joinColumns = @JoinColumn(name = "tavolo_id_tavolo"),
            inverseJoinColumns = @JoinColumn(name = "utentes_id_utente"))
    @JsonIgnore
    @JsonIgnoreProperties("utenteGestisceTavolo")
    private List<Utente> utenteGestisceTavolo = new ArrayList<>();


    @OneToMany(mappedBy = "tavolo")//@JsonIgnore
    @JsonIgnoreProperties("tavolo")
    private List<TavoloOrdinaElemento> tavoloOrdinaElemento = new ArrayList<>();

    @Override
    public String toString() {
        return "Tavolo{" +
                "idTavolo=" + idTavolo +
                ", numeroTavolo=" + numeroTavolo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tavolo tavolo = (Tavolo) o;
        return numeroTavolo == tavolo.numeroTavolo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroTavolo);
    }
}
