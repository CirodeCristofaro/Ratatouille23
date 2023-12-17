package com.Ratatouille23.Server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TavoloOrdinaElemento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTavoloOrdinaElemento")
    private Long idTavoloOrdinaElemento;

    @ManyToOne
    @JoinColumn(name = "tavolo_id_tavolo")   // @JsonIgnore
    @JsonIgnoreProperties("tavoloOrdinaElemento")
    private Tavolo tavolo;


    @ManyToOne
    @JoinColumn(name = "elementi_id_elemento")
    @JsonIgnoreProperties("tavoloOrdinaElemento")
    private Elementi elementiOrdinati;

    private int quantita;


    @Override
    public String toString() {
        return "TavoloOrdinaElemento{" +
                "idTavoloOrdinaElemento=" + idTavoloOrdinaElemento +
                ", quantita=" + quantita +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TavoloOrdinaElemento that = (TavoloOrdinaElemento) o;
        return Objects.equals(tavolo, that.tavolo) && Objects.equals(elementiOrdinati, that.elementiOrdinati);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tavolo, elementiOrdinati);
    }
}
