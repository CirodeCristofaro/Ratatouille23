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
@AllArgsConstructor
@NoArgsConstructor
public class Allergenici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAllergenico;

    @Column(unique = true)
    private String nomeAllergenico;


    @ManyToMany(mappedBy = "allergenici", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("allergenici")
    private List<Elementi> ingredientiAllergenici;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergenici that = (Allergenici) o;
        return Objects.equals(nomeAllergenico, that.nomeAllergenico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeAllergenico);
    }

    @Override
    public String toString() {
        return "Allergenici{" +
                "idAllergenico=" + idAllergenico +
                ", nomeAllergenico='" + nomeAllergenico + '\'' +
                '}';
    }
}
