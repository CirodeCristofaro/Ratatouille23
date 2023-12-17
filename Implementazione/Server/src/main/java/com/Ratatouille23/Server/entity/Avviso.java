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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avviso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvviso;

    private String oggetto;

    private String contenuto;


    private Date data;

    //relazione utente 1 - * avviso
    @ManyToOne
    @JoinColumn(name = "utente_id")
    @JsonIgnoreProperties("creaAvviso")
    private Utente utenteCreaAvviso;

    @OneToMany(mappedBy = "avvisoRicevuto", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("avvisoRicevuto")
    private List<VisualizzaAvviso> visualizzAvviso = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avviso avviso = (Avviso) o;
        return Objects.equals(oggetto, avviso.oggetto) && Objects.equals(contenuto, avviso.contenuto) && Objects.equals(data, avviso.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oggetto, contenuto, data);
    }

    @Override
    public String toString() {
        return "Avviso{" +
                "idAvviso=" + idAvviso +
                ", oggetto='" + oggetto + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", data=" + data +
                '}';
    }
}
