package com.Ratatouille23.Server.repository;

import com.Ratatouille23.Server.entity.Categorie;
import com.Ratatouille23.Server.entity.Elementi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementiRepository extends JpaRepository<Elementi, Long> {

    Optional<Elementi> findOneBynomeElemento(String nome);

    Optional<Elementi> findOneByNomeElementoIgnoreCase(String nome);

    Optional<Elementi> findBynomeElemento(String nomeElemento);

    List<Elementi> findByCategoriaOrderByOrdineDesc(Categorie bynomeCategoria);

    boolean existsByNomeElementoIgnoreCase(String nomeElemento);


}
