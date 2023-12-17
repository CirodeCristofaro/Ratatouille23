package com.Ratatouille23.Server.controller;

import com.Ratatouille23.Server.apiOpenFood.controller.RestProdotti;
import com.Ratatouille23.Server.apiOpenFood.entity.Products;
import com.Ratatouille23.Server.entity.Categorie;
import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.service.CategorieService;
import com.Ratatouille23.Server.service.ElementiService;
import com.Ratatouille23.Server.utils.ApiSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/menu")
public class MenuRestController {


    @Autowired
    private ElementiService elementiService;
    @Autowired
    private CategorieService categorieService;


    /***
     * Ritorna la lista di tutte le categorie del menu
     *
     * @return lista di categorie del menu
     *
     * */
    @GetMapping("/tutteLeCategorie")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public List<Categorie> tutteLeCategorie() {
        return categorieService.findAllCategorie();
    }


    /**
     * Oridna le categorie
     *
     * @param categorie la lista delle categorie da ordinare
     * @throws BadRequestException se si verifica un errore durante l'ordinamento delle categorie
     **/
    @PostMapping("/ordinaCategorie")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public void ordinaCategorie(@RequestBody List<Categorie> categorie) throws BadRequestException {

        categorieService.ordinaCategorie(categorie);

    }


    /**
     * Crea una nuova Categoria con una elemento
     *
     * @param nomeCategoria contiene il nome della nuova categoria da creare
     * @param elemento      L'oggetto Elementi che contiene le informazioni dell elemento da inserire nella nuova categoria
     * @param allergenici   (opzionale)Lista degli allergenici dell elemento
     * @return ResponseEntity contiene il messaggio e il codice di successo dell'operazione
     * @throws BadRequestException se si verifica un errore durante la creazione della nuova Categoria o dell elemento
     * @throws NotFoundException se si verifica un errore all associazione della categoria al menù
     */
    @PostMapping("/creaCategoriaConElemento")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public ResponseEntity<ApiSuccess> creaCategoriaConElemento(@RequestParam("nomeCategoria") String nomeCategoria,
                                                               @RequestBody Elementi elemento,
                                                               @RequestParam("allergenici") Optional<List<String>> allergenici) throws BadRequestException, NotFoundException {

        return categorieService.creaCategoriaConElemento(nomeCategoria, elemento, allergenici);
    }


    /**
     * Ritorna una lista di Elementi di una specifica Categoria
     *
     * @param categoria nome della categoria
     * @return lista delle elementi di quella categoria
     */
    @GetMapping("/tuttiGliElementi")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public List<Elementi> tuttiGliElementi(@RequestParam("categoria") String categoria) {
        return elementiService.findAllElementi(categoria);
    }


    /**
     * Aggiorna l'indice delle elementi Specificate
     *
     * @param elementi Lista delle elementi da ordinare
     */
    @PostMapping("/ordinaElementi")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public void ordinaElementi(@RequestBody List<Elementi> elementi)   {
        elementiService.ordinaElementi(elementi);
    }

    /**
     * Ritorna la lista dei prodotti utilizzando l'Api Open Food
     *
     * @param cerca contiene la stringa del prodotto da cercare
     * @return la lista dei prodotti corrispondente alla ricerca
     */
    @GetMapping("/autocompleteOpenFood")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public List<Products> autocompleteOpenFood(@RequestParam("cerca") String cerca) {
        RestProdotti restProdotti = new RestProdotti();
        return restProdotti.ricercaProdotti(cerca);

    }

    /**
     * Cancella la elemento specificata dal menu
     *
     * @param elemento nome della elemento da cancellare dal menu
     * @return ResponseEntity contiene il messaggio e il codice di successo dell'operazione
     * @throws BadRequestException se si verifica un errore durante l'eliminazione dell elemento
     **/
    @DeleteMapping("/cancellaElemento")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public ResponseEntity<ApiSuccess> cancellaElemento(@RequestParam("elemento") String elemento) throws BadRequestException {
        return elementiService.cancellaElemento(elemento);
    }


    /**
     * Aggiunge la elemento a una specifica categoria
     *
     * @param nuovaElemento contiene le nuove informazioni dell elemento da aggiungere a quella categoria
     * @param categoria     contiene il nome della categoria in cui verrà inserità la nuova elemento
     * @param allergenici   (opzionale) contiene la lista degli allergenici della elemento
     * @return ResponseEntity contiene il messaggio e il codice di successo dell'operazione
     * @throws BadRequestException se si verifica un errore durante la creazione e/o associazione dell elemento alla categoria
     */
    @PostMapping("/aggiungiElemento")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    public ResponseEntity<ApiSuccess> aggiungiElemento(@RequestBody Elementi nuovaElemento,
                                                       @RequestParam("categoria") String categoria,
                                                       @RequestParam("allergenici") Optional<List<String>> allergenici) throws BadRequestException {
        return elementiService.aggiungiElemento(nuovaElemento, categoria, allergenici);
    }


}

