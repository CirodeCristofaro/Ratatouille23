package com.Ratatouille23.Server.controller.blackBox;

import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.repository.ElementiRepository;
import com.Ratatouille23.Server.service.AllergeniciService;
import com.Ratatouille23.Server.service.CategorieService;
import com.Ratatouille23.Server.service.ElementiService;
import com.Ratatouille23.Server.utils.ApiSuccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AggiungiElementiBlackBox {
    @InjectMocks
    private ElementiService elementiService;
    @Mock
    private ElementiRepository elementiRepository;
    @Mock
    private  CategorieService categorieService;
    @Mock
    private AllergeniciService allergeniciService;


    private Elementi elementi;
    private List<String> allergeniciList;
    private String categoria;

    @BeforeEach
    public void init() {
        elementi = new Elementi();
        allergeniciList = new ArrayList<>();
        allergeniciList.add("Allergenico1");
        allergeniciList.add("Allergenico2");

    }

    @Test
    public void testAggiungiElemento_N1_D1_P1_C1_A1() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        allergeniciList = new ArrayList<>();
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

   @Test
    public void testAggiungiElemento_N1_D1_P1_C1_A2() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        categoria = "";

       assertThrows(BadRequestException.class , () -> {
                   elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
               }
       );
    }


    @Test
    public void testAggiungiElemento_N1_D1_P1_C2_A1() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        allergeniciList = new ArrayList<>();
        categoria = "primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N1_D1_P1_C2_A2() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        categoria = "primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N1_D1_P2_C1_A1(){
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="";
        allergeniciList = new ArrayList<>();
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }
    @Test
    public void testAggiungiElemento_N1_D1_P2_C1_A2(){
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N1_D1_P2_C2_A1(){
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="primi";
        allergeniciList = new ArrayList<>();
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }
    @Test
    public void testAggiungiElemento_N1_D1_P2_C2_A2(){
        elementi.setNomeElemento("");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N1_D2_P1_C1_A1() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        allergeniciList = new ArrayList<>();
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N1_D2_P1_C1_A2() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }



    @Test
    public void testAggiungiElemento_N1_D2_P1_C2_A1() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.ZERO);
        categoria="primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria,null);
                }
        );

    }

    @Test
    public void testAggiungiElemento_N1_D2_P1_C2_A2() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(-1));
        categoria="primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria,Optional.of(allergeniciList));
                }
        );

    }

    @Test
    public void testAggiungiElemento_N1_D2_P2_C1_A1() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="";
        allergeniciList=new ArrayList<>();
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria,Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N1_D2_P2_C1_A2() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria,Optional.of(allergeniciList));
                }
        );

    }
    @Test
    public void testAggiungiElemento_N1_D2_P2_C2_A1() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(1));
        categoria="primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria,null);
                }
        );

    }
    @Test
    public void testAggiungiElemento_N1_D2_P2_C2_A2() {
        elementi.setNomeElemento("");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(5));
        categoria = "primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N2_D1_P1_C1_A1() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        allergeniciList = new ArrayList<>();
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N2_D1_P1_C1_A2() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        allergeniciList = new ArrayList<>();
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N2_D1_P1_C2_A1(){
        elementi.setNomeElemento("elemento");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.ZERO);
        categoria="primi";
        allergeniciList=new ArrayList<>();
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }
    @Test
    public void testAggiungiElemento_N2_D1_P1_C2_A2() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        categoria = "primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }


    @Test
    public void testAggiungiElemento_N2_D1_P2_C1_A1() {
        elementi.setNomeElemento("elemnto");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.TEN);
        categoria="";
        allergeniciList=new ArrayList<>();
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }

    @Test
    public void testAggiungiElemento_N2_D1_P2_C1_A2() {
        elementi.setNomeElemento("elemnto");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.TEN);
        categoria="";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }
    @Test
    public void testAggiungiElemento_N2_D1_P2_C2_A1() {
        elementi.setNomeElemento("elemnto");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.TEN);
        categoria="primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, null);
                }
        );
    }


    @Test
    public void testAggiungiElemento_N2_D1_P2_C2_A2() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("");
        elementi.setPrezzo(BigDecimal.valueOf(5));
        categoria = "primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );

    }

    @Test
    public void testAggiungiElemento_N2_D2_P1_C1_A1() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        allergeniciList = new ArrayList<>();
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }


    @Test
    public void testAggiungiElemento_N2_D2_P1_C1_A2() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        categoria = "";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }


    @Test
    public void testAggiungiElemento_N2_D2_P1_C2_A1(){
        elementi.setNomeElemento("elemento");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.ZERO);
        categoria="primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria,null);
                }
        );
    }

    @Test
    public void testAggiungiElemento_N2_D2_P1_C2_A2() {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(-150));
        categoria = "primi";
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }


    @Test
    public void testAggiungiElemento_N2_D2_P2_C1_A1(){
        elementi.setNomeElemento("elemento");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.TEN);
        categoria="";
        allergeniciList=new ArrayList<>();
        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }
    @Test
    public void testAggiungiElemento_N2_D2_P2_C1_A2(){
        elementi.setNomeElemento("elemento");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.TEN);
        categoria="";

        assertThrows(BadRequestException.class , () -> {
                    elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
                }
        );
    }


    @Test
    public void testAggiungiElemento_N2_D2_P2_C2_A2() throws BadRequestException {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(5));
        categoria = "primi";
        ResponseEntity<ApiSuccess> response = elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Elemento aggiunto con successo", response.getBody().getMessage());

    }
    @Test
    public void testAggiungiElemento_N2_D2_P2_C2_A1() throws BadRequestException {
        elementi.setNomeElemento("elementi");
        elementi.setDescrizione("descrizione");
        elementi.setPrezzo(BigDecimal.valueOf(5));
        categoria = "primi";
        allergeniciList=new ArrayList<>();
        ResponseEntity<ApiSuccess> response = elementiService.aggiungiElemento(elementi, categoria, Optional.of(allergeniciList));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Elemento aggiunto con successo", response.getBody().getMessage());

    }


}
