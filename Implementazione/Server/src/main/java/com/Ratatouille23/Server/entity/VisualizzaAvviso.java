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
public class VisualizzaAvviso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVisualizzaAvviso;

    private TipoVisualizzazione visualizzazione;

    @ManyToOne
    @JsonIgnoreProperties("creaAvviso")
    private Utente utenteCheRiceveLAvviso;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("avviso_ricevuto_id_avviso")
    @JoinColumn(name = "avviso_ricevuto_id_avviso")
    private Avviso avvisoRicevuto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisualizzaAvviso that = (VisualizzaAvviso) o;
        return visualizzazione == that.visualizzazione &&
                Objects.equals(avvisoRicevuto, that.avvisoRicevuto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visualizzazione, avvisoRicevuto);
    }

    @Override
    public String toString() {
        return "VisualizzaAvviso{" +
                "idVisualizzaAvviso=" + idVisualizzaAvviso +
                ", visualizzazione=" + visualizzazione +
                '}';
    }
}
