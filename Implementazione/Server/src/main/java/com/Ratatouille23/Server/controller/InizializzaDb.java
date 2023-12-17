package com.Ratatouille23.Server.controller;

import com.Ratatouille23.Server.apiOpenFood.controller.RestProdotti;
import com.Ratatouille23.Server.apiOpenFood.entity.ProductSearchResult;
import com.Ratatouille23.Server.entity.*;
import com.Ratatouille23.Server.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class InizializzaDb {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private MenuService menuService;



    @Autowired
    private AllergeniciService allergeniciService;



    @Autowired
    private CategorieService categorieService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ElementiService elementiService;

    @GetMapping("/init")
    private void inizializzaDb() {

        if (utenteService.findAll().isEmpty() && categorieService.findAllCategorie().isEmpty()
                && allergeniciService.findAll().isEmpty() && !menuService.menuExists("Ratatouille23")
                && elementiService.findAll().isEmpty()) { //db vuoto
            log.info("inizializzazione db ");
            Utente utente = new Utente();
            Menu menu = new Menu();
            menu.setNomeMenu("Ratatouille23");
            menu = menuService.save(menu);
            utente.setCognome("Ciro");
            utente.setNome("de Cristofaro");
            utente.setEmail("ciro@gmail.com");
            utente.setPassword(passwordEncoder.encode("Ingsw2023!"));
            utente.setTipoUtente(TipoUtente.Amministratore);
            utente.setMenuGestito(menu);
            utenteService.saveUtente(utente);
            categoriaBevande(menu);
            log.info("fine inizializzazione db ");
        }

    }

    private void categoriaBevande(Menu menu) {
        String[] categoriePredefinite = {"acque", "birre", "vini"};
        for (int i = 0; i < categoriePredefinite.length; i++) {
            Categorie categorie = new Categorie();
            if (categoriePredefinite[i].equals("acque")) {
                categorie.setNomeCategoria("acqua");
            } else {
                categorie.setNomeCategoria(categoriePredefinite[i]);
            }
            categorie.setOrdine(i);
            categorie.setMenu(menu);
            menu.setCategoria(new ArrayList<>() {
                {
                    add(categorie);
                }
            });
            RestProdotti restProdotti = new RestProdotti();
            ProductSearchResult searchResult = restProdotti.bevande(categoriePredefinite[i]);

            int finalI = i;
            List<Elementi> elementiDefault = searchResult.getProducts().stream().filter(bevanda -> bevanda.getProduct_name() != null)
                    .map(elemento -> new Elementi() {
                        {

                            setNomeElemento(elemento.getProduct_name().toLowerCase());
                            setCategoria(categorie);
                            if (elemento.getIngredients_text() != null) {
                                setDescrizione(elemento.getIngredients_text());
                            } else {
                                setDescrizione(elemento.getProduct_name().toLowerCase());
                            }

                            if (elemento.getAllergens_hierarchy() != null || !elemento.getAllergens_hierarchy().isEmpty()) {
                                List<Allergenici> listaAllergenici = new ArrayList<>();

                                for (String allergenicoApi : elemento.getAllergens_hierarchy()) {

                                    if (allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenicoApi.substring(3))
                                            == null) {
                                        if (!allergenicoApi.substring(3).equalsIgnoreCase("none")) {//per escludere gli
                                            // allergenici di openfood che hanno none ad esempio nell acqua
                                            Allergenici allergenico = new Allergenici();
                                            allergenico.setNomeAllergenico(allergenicoApi.substring(3));
                                            log.info("allergenico  non esistente : {} ", allergenico);
                                            allergeniciService.save(allergenico);
                                            listaAllergenici.add(allergenico);
                                        }
                                    } else {
                                        Allergenici allergenico = allergeniciService
                                                .findOneByNomeAllergenicoIgnoreCase(allergenicoApi.substring(3));
                                        log.info("allergenico  esistente : {} ", allergenico);
                                        listaAllergenici.add(allergenico);
                                    }
                                }
                                setAllergenici(listaAllergenici);

                            }

                            setPrezzo(generaPrezziRandomCategoriaDefault(categoriePredefinite[finalI]));

                        }
                    }).collect(Collectors.toList());
            List<Elementi> elementiSenzaDuplicati = new ArrayList<>();
            for (int x = 0; x < elementiDefault.size(); x++) {
                Elementi elementi = elementiDefault.get(x);
                String nomeElemento = elementi.getNomeElemento().toLowerCase();
                boolean eDuplicato = false;
                for (int j = 0; j < x; j++) {
                    Elementi elementiEsistente = elementiDefault.get(j);
                    String nomeElementoEsistente = elementiEsistente.getNomeElemento().toLowerCase();
                    if (nomeElemento.equals(nomeElementoEsistente)) {
                        eDuplicato = true;
                        break;
                    }
                }
                if (!eDuplicato) {
                    elementiSenzaDuplicati.add(elementi);
                }
            }
            elementiDefault = elementiSenzaDuplicati;

            for (int j = 0; j < elementiDefault.size(); j++) {
                elementiDefault.get(j).setOrdine(j);
            }
            categorie.setElementi(elementiDefault);

            categorieService.save(categorie);
        }
    }

    private BigDecimal generaPrezziRandomCategoriaDefault(String categoria) {
        switch (categoria) {
            case "acque":
                return BigDecimal.valueOf(Math.random() * (2.5 - 0.50) + 0.50);

            case "birre":

                return BigDecimal.valueOf(Math.random() * (5 - 15) + 15);

            case "vini":
                return BigDecimal.valueOf(Math.random() * (15 - 50) + 50);

            default:
                throw new IllegalArgumentException("Categoria non valida: " + categoria);
        }

    }


}
