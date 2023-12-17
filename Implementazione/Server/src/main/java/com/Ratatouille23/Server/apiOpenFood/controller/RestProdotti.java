package com.Ratatouille23.Server.apiOpenFood.controller;

import com.Ratatouille23.Server.apiOpenFood.entity.ProductSearchResult;
import com.Ratatouille23.Server.apiOpenFood.entity.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RestProdotti {


    public List<Products> ricercaProdotti(@PathParam("query") String query) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://it.openfoodfacts.org/cgi/search.pl?action=process&search_terms=" + query + "&json=true";

        ResponseEntity<ProductSearchResult> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ProductSearchResult.class
        );

        ProductSearchResult productSearchResult = response.getBody();
        List<Products> productsArrayList = new ArrayList<>();
        for (int i = 0; i < productSearchResult.getProducts().size(); i++) {
            if (productSearchResult.getProducts().get(i).getProduct_name() != null) {
                Products elemento = productSearchResult.getProducts().get(i);
                String nomeElemento = elemento.getProduct_name().toLowerCase();
                boolean eDuplicato = false;
                for (int j = 0; j < i; j++) {
                    Products elementoEsistente = productSearchResult.getProducts().get(j);
                    if (elementoEsistente.getProduct_name() != null) {
                        String nomeElementoEsistente = elementoEsistente.getProduct_name().toLowerCase();
                        if (nomeElemento.equals(nomeElementoEsistente)) {
                            eDuplicato = true;
                            break;
                        }
                    }
                }
                if (!eDuplicato) {
                    productsArrayList.add(elemento);
                }

            }

        }
        return productsArrayList;

    }


    //https://it.openfoodfacts.org/category/acque per avere tutte le acque
    //bevande-alcoliche
    //birre
    //vini
    public ProductSearchResult bevande(@PathParam("query") String query) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://it.openfoodfacts.org/category/" + query + ".json";

        ResponseEntity<ProductSearchResult> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ProductSearchResult.class
        );

        return response.getBody();
    }

}
