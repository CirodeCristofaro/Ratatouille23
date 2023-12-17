package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce;

import com.INGSW2223_V_06.ratatouille23.model.Categorie;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;

import java.util.List;

public interface RouteMenuInterface {

    void getCategorie(Callback callback);

    void allElementi(String elemento, Callback callback);


    void ordinaCategorie(List<Categorie> categoria, Callback callback);

    void ordinaElementi(List<Elementi> elementi, Callback callback);

    void autocomplete(String cerca, Callback callback);


    void creaCategoriaConElemento(String nomeCategoria, Elementi elementi, List<String> allergenici, Callback callback);



    void cancellaElemento(String elemento, Callback callback);


    void aggiungiElemento(Elementi nuovaElementi, String categoria,
                          List<String> allergenici,
                          Callback callback);



}
