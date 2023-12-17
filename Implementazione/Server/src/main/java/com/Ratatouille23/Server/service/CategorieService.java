package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Allergenici;
import com.Ratatouille23.Server.entity.Categorie;
import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.entity.Menu;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.CategorieRepository;
import com.Ratatouille23.Server.utils.ApiSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    private ElementiService elementiService;

    private MenuService menuService;

    @Autowired
    private AllergeniciService allergeniciService;

    public CategorieService(@Lazy ElementiService elementiService, @Lazy MenuService menuService) {
        this.elementiService = elementiService;
        this.menuService = menuService;
    }

    private final String nomeMenu = "Ratatouille23";

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public List<Categorie> findByOrderByOrdineDesc() {
        return categorieRepository.findByOrderByOrdineDesc();
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie findByNomeCategoria(String nomeCategoria) throws BadRequestException {
        Optional<Categorie> categorie = categorieRepository.findBynomeCategoria(nomeCategoria);
        if (categorie.isPresent()) {
            return categorie.get();
        }
        throw new BadRequestException( "Categoria non trovata");

    }


    public boolean existsByNomeCategoriaIgnoreCase(String nome)   {
        return categorieRepository.existsByNomeCategoriaIgnoreCase(nome);
    }


    public void delete(Categorie categorie) throws BadRequestException {
        try {
            categorieRepository.delete(categorie);
        } catch (IllegalArgumentException e) {
            log.error("errore 500 cancellaElemento");
            throw new BadRequestException("Elemento non più disponibile");

        }
    }


    public Categorie findOneBynomeCategoriaIgnoreCase(String nomeCategoria) throws BadRequestException {
        Optional<Categorie> categorie = categorieRepository.findOneBynomeCategoriaIgnoreCase(nomeCategoria);
        if (categorie.isPresent()) {
            return categorie.get();
        }
        throw new BadRequestException("Categoria non esistente");

    }


    public List<Categorie> findAllCategorie() {
        return findAll().stream()
                .sorted(Comparator.comparing(Categorie::getOrdine, Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

    public void ordinaCategorie(List<Categorie> categoria) throws BadRequestException {
        log.info("categorie da ordinare : {}", categoria.toString());
        for (int i = 0; i < categoria.size(); i++) {
            Categorie categoriaDb = findByNomeCategoria(categoria.get(i).getNomeCategoria());
            categoriaDb.setOrdine(i);
            save(categoriaDb);
        }
    }

    public ResponseEntity<ApiSuccess> creaCategoriaConElemento(String nomeCategoria,
                                                               Elementi elemento,
                                                               Optional<List<String>>allergenici) throws BadRequestException, NotFoundException {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            throw  new BadRequestException("Nome categoria non specificato");
        }

        if (existsByNomeCategoriaIgnoreCase(nomeCategoria)) {
            throw  new BadRequestException("Categoria già presente");
        }

        if (elementiService.controlloCampiElementoValidati(elemento)) {
            throw  new BadRequestException("Uno o più campi della elemento  non sono stati definiti");
        }

        if (elementiService.checkIfElementoExists(elemento.getNomeElemento())) {
            throw  new BadRequestException("Elemento già esistente");

        }


        if (elemento.getPrezzo().compareTo(BigDecimal.ZERO) <= 0) {
            throw  new BadRequestException("Il prezzo deve essere maggiore di zero");

        }

        Menu menu = menuService.findByNomeMenu(nomeMenu);

        Categorie nuovaCategoria = new Categorie();
        nuovaCategoria.setNomeCategoria(nomeCategoria);
        nuovaCategoria.setMenu(menu);
        List<Categorie> elementoOrdine = findAll();
        log.info("ord : {} ", elementoOrdine);
        if (elementoOrdine.size() != 0) {
            int ordine = findByOrderByOrdineDesc().get(0).getOrdine();
            log.info("ordine : {} ", ordine);
            nuovaCategoria.setOrdine(++ordine);
        }
        save(nuovaCategoria);

        if (allergenici != null && !allergenici.isEmpty()) {
            List<Allergenici> allergeniciList = new ArrayList<>();
            for (String allergenico : allergenici.get()) {
                Allergenici allergenicoOpt = allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenico);
                if (allergenicoOpt != null) {
                    log.info("Allergenico già presente :{}", allergenicoOpt.getNomeAllergenico());
                    allergeniciList.add(allergenicoOpt);
                } else {
                    log.info("Allergenico nuovo :{}", allergenico);
                    Allergenici nuovoAllergenico = new Allergenici();
                    nuovoAllergenico.setNomeAllergenico(allergenico);
                    allergeniciService.save(nuovoAllergenico);
                    allergeniciList.add(nuovoAllergenico);
                }

            }
            elemento.setAllergenici(allergeniciList);
        }
        Categorie categoria = findByNomeCategoria(nomeCategoria);
        elemento.setCategoria(categoria);
        elementiService.save(elemento);
        log.info("categoria con elemento creata con successo");

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiSuccess(201, "Categoria e Elemento creati con successo"));
    }


}
