package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Allergenici;
import com.Ratatouille23.Server.entity.Categorie;
import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.ElementiRepository;
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
public class ElementiService {

    @Autowired
    private ElementiRepository elementiRepository;
    private CategorieService categorieService;
    private AllergeniciService allergeniciService;

    @Autowired
    public ElementiService(CategorieService categorieService, @Lazy AllergeniciService allergeniciService) {
        this.allergeniciService = allergeniciService;
        this.categorieService = categorieService;
    }

    public List<Elementi> findAllElementiVisibili() {
        return elementiRepository.findAll();
    }

    public List<Elementi> findAll() {
        return elementiRepository.findAll();
    }

    public Elementi findOneBynomeElemento(String nomeElemento) throws NotFoundException {
        Optional<Elementi> elemento = elementiRepository.findOneBynomeElemento(nomeElemento);
        if (elemento.isPresent()) {
            return elemento.get();
        }
        throw new NotFoundException("Elemento non più disponibile");

    }


    public List<Allergenici> findAllergeniciByNomeElemento(String nomeElemento) throws NotFoundException {
        log.info("elemento : {} ", nomeElemento);
        Elementi elementiOptional = findOneBynomeElemento(nomeElemento);
        return elementiOptional.getAllergenici();
    }

    public Elementi save(Elementi elementi) {
        return elementiRepository.save(elementi);
    }

    public boolean checkIfElementoExists(String nomeElemento)   {
        return elementiRepository.existsByNomeElementoIgnoreCase(nomeElemento);
    }

    public Elementi findBynomeElemento(String nomeElemento) throws BadRequestException {
        Optional<Elementi> elementoOptional = elementiRepository.findBynomeElemento(nomeElemento);
        if (elementoOptional.isPresent()) {
            return elementoOptional.get();
        }
        throw new BadRequestException("Elemento non più disponibile");
    }

    public void delete(Elementi elementi) throws  BadRequestException {
        try {
            elementiRepository.delete(elementi);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Elemento non più disponibile");
        }
    }

    public Elementi findOneByNomeElementoIgnoreCase(String nome) throws NotFoundException {
        Optional<Elementi> elementoOptional = elementiRepository.findOneByNomeElementoIgnoreCase(nome);
        if (elementoOptional.isPresent()) {
            return elementoOptional.get();
        }
        throw new NotFoundException("Elemento non più disponibile");
    }

    public List<Elementi> findByCategoriaOrderByOrdineDesc(Categorie categorie) {
        return elementiRepository.findByCategoriaOrderByOrdineDesc(categorie);
    }


    public List<Elementi> findAllElementi(String categoria) {
        return findAll().stream().filter(elementoStream -> elementoStream.getCategoria().getNomeCategoria().equalsIgnoreCase(categoria))
                .sorted(Comparator.comparing(Elementi::getOrdine, Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

    public void ordinaElementi(List<Elementi> elementi)   {
        log.info("elementi da ordinare : {}", elementi.toString());
        for (int i = 0; i < elementi.size(); i++) {
            try {
                Elementi elementiDb = findOneBynomeElemento(elementi.get(i).getNomeElemento());
                if (elementiDb != null) {
                    elementiDb.setOrdine(i);
                    save(elementiDb);
                }
            }catch (NotFoundException exception){
                log.info("elemento da ordinare non piu disponibile :{}" ,elementi.get(i).getNomeElemento());
            }

        }
    }

    public ResponseEntity<ApiSuccess> cancellaElemento(String elemento) throws BadRequestException {
        Elementi elementiTrovata = findBynomeElemento(elemento);
        Categorie categorie = elementiTrovata.getCategoria();

        delete(elementiTrovata);
        categorie.getElementi().remove(elementiTrovata);

        Categorie categoriaCheck = categorieService.findByNomeCategoria(categorie.getNomeCategoria());

        if (categoriaCheck.getElementi().isEmpty()) {
            categorieService.delete(categorie);
        }
        log.info("Elemento da eliminare: {}  ", elementiTrovata);
        ordinaElementi(categorie.getElementi().stream()
                .sorted(Comparator.comparing(Elementi::getOrdine))
                .collect(Collectors.toList()));
        return ResponseEntity.status(200).
                contentType(MediaType.APPLICATION_JSON).
                body(new ApiSuccess(200, "Elemento eliminato con successo"));
    }


    public ResponseEntity<ApiSuccess> aggiungiElemento(Elementi nuovaElemento,
                                                       String categoria,
                                                      Optional<List<String>> allergenici) throws BadRequestException {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new BadRequestException("Categoria non specificata");
        }

        Categorie categoriaOpt = categorieService.findOneBynomeCategoriaIgnoreCase(categoria);


        if (controlloCampiElementoValidati(nuovaElemento)) {
            throw new BadRequestException("Uno o più campi della elemento non sono stati definiti");

        }

        if (checkIfElementoExists(nuovaElemento.getNomeElemento())) {
            throw new BadRequestException("Elemento già esistente");
        }

        if (nuovaElemento.getPrezzo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Il prezzo deve essere maggiore di zero");
        }

        nuovaElemento.setCategoria(categoriaOpt);
        List<Elementi> elementiOrdine = findByCategoriaOrderByOrdineDesc(categoriaOpt);
        int ordine;
        int size = elementiOrdine.size();
        log.info("ordine " + size);
        if (size != 0) {
            ordine = elementiOrdine.get(0).getOrdine();
            nuovaElemento.setOrdine(++ordine);
        }


        if (allergenici != null && !allergenici.isEmpty()) {
            List<Allergenici> allergeniciList = new ArrayList<>();

            for (String allergenico : allergenici.get()) {
                Allergenici allergenicoOpt = allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenico);
                if (allergenicoOpt != null) {
                    log.info("allergenico esistente :{}", allergenico);

                    allergeniciList.add(allergenicoOpt);
                } else {
                    log.info("allergenico non esistente :{}", allergenico);
                    Allergenici nuovoAllergenico = new Allergenici();
                    nuovoAllergenico.setNomeAllergenico(allergenico);
                    allergeniciService.save(nuovoAllergenico);
                    allergeniciList.add(nuovoAllergenico);
                }
            }
            nuovaElemento.setAllergenici(allergeniciList);
        }

        save(nuovaElemento);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiSuccess(201, "Elemento aggiunto con successo"));
    }

    public boolean controlloCampiElementoValidati(Elementi elementi) {
        return elementi == null || (elementi.getNomeElemento() == null || elementi.getNomeElemento().trim().equals(""))
                || (elementi.getDescrizione() == null || elementi.getDescrizione().trim().equals(""))
                || (elementi.getPrezzo() == null);
    }

}
