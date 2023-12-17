package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findBynomeCategoria(String nome);

    List<Categorie> findByOrderByOrdineDesc();

    boolean existsByNomeCategoriaIgnoreCase(String categoria);

    Optional<Categorie> findOneBynomeCategoriaIgnoreCase(String categoria);

}
