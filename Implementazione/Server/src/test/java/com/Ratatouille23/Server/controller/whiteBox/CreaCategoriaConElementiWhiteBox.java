package com.Ratatouille23.Server.controller.whiteBox;

import com.Ratatouille23.Server.entity.Allergenici;
import com.Ratatouille23.Server.entity.Categorie;
import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.entity.Menu;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.CategorieRepository;
import com.Ratatouille23.Server.repository.ElementiRepository;
import com.Ratatouille23.Server.repository.MenuRepository;
import com.Ratatouille23.Server.service.AllergeniciService;
import com.Ratatouille23.Server.service.CategorieService;
import com.Ratatouille23.Server.service.ElementiService;
import com.Ratatouille23.Server.service.MenuService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CreaCategoriaConElementiWhiteBox {

    @InjectMocks
    private CategorieService categorieService;
    @Mock
    private ElementiService elementiService;
    @Mock
    private MenuService menuService ;
    @Mock
    private  MenuRepository menuRepository;
    @Mock
    private CategorieRepository categorieRepository;
    @Mock
    private AllergeniciService allergeniciService;
    @Mock
    private ElementiRepository elementiRepository;
    private Elementi elementi;
    private Categorie categoria;
    private List<String> allergenici;

    private Menu menu;

    @BeforeEach
    public void init() {
        menu = new Menu();
        menu.setIdMenu(1L);
        menu.setNomeMenu("Ratatouille23");


        elementi = new Elementi();
        elementi.setNomeElemento("elementi corretta");
        elementi.setDescrizione("descrizione corretta");
        elementi.setPrezzo(BigDecimal.TEN);

        categoria = new Categorie();
        categoria.setNomeCategoria("Categoria giusta");
        categoria.setMenu(menu);
        categoria.setOrdine(1);

        allergenici = new ArrayList<>();
        allergenici.add("Allergenico1");
        allergenici.add("Allergenico2");

    }

    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1() {

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            categorieService.creaCategoriaConElemento("", elementi, null);
        });
        ////assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Nome categoria non specificato", exception.getMessage());


    }

    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2() {
        when(categorieRepository.existsByNomeCategoriaIgnoreCase(categoria.getNomeCategoria())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, null);
        });

        assertEquals("Categoria già presente", exception.getMessage());


    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3() throws BadRequestException {
        elementi = null;
        when(categorieService.existsByNomeCategoriaIgnoreCase(anyString())).thenReturn(false);
        when(elementiService.controlloCampiElementoValidati(elementi)).thenReturn(true);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, null);
        });

        assertEquals("Uno o più campi della elemento  non sono stati definiti", exception.getMessage());


    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4() {
        when(elementiService.checkIfElementoExists(elementi.getNomeElemento())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, null);
        });

        assertEquals("Elemento già esistente", exception.getMessage());


    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5() {
        elementi.setPrezzo(BigDecimal.valueOf(-150));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, null);
        });

        assertEquals("Il prezzo deve essere maggiore di zero", exception.getMessage());


    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6() throws NotFoundException {

        String nomeCategoria = "Categoria di test";
        String nomeMenu = "Ratatouille23";
        when(menuService.findByNomeMenu(nomeMenu)).thenThrow( new NotFoundException( "Menu non trovato"));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            categorieService.creaCategoriaConElemento(nomeCategoria, elementi, null);
        });

        assertEquals("Menu non trovato", exception.getMessage());

    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6_7_8_9() throws BadRequestException, NotFoundException {
      when(menuService.findByNomeMenu(menu.getNomeMenu())).thenReturn(menu);

        when(categorieRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(categoria);
        }});

        when(categorieRepository.findByOrderByOrdineDesc()).thenReturn(new ArrayList<>() {{
            add(categoria);
        }});
        when(categorieRepository.findBynomeCategoria(categoria.getNomeCategoria())).thenReturn(Optional.of(categoria));
        ResponseEntity<ApiSuccess> response = categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, null);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Categoria e Elemento creati con successo", response.getBody().getMessage());

    }

    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6_7_9() throws BadRequestException, NotFoundException {
        when(menuService.findByNomeMenu(menu.getNomeMenu())).thenReturn(menu);

        when(categorieRepository.findBynomeCategoria(categoria.getNomeCategoria())).thenReturn(Optional.of(categoria));
        ResponseEntity<ApiSuccess> response = categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, null);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Categoria e Elemento creati con successo", response.getBody().getMessage());

    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6_7_8_9_10_11_12_14() throws BadRequestException, NotFoundException {

        when(menuService.findByNomeMenu(menu.getNomeMenu())).thenReturn(menu);
        when(categorieRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(categoria);
        }});

        when(categorieRepository.findByOrderByOrdineDesc()).thenReturn(new ArrayList<>() {{
            add(categoria);
        }});
        when(categorieRepository.findBynomeCategoria(categoria.getNomeCategoria())).thenReturn(Optional.of(categoria));

        when(allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenici.get(0))).thenReturn(new Allergenici() {{
            setIdAllergenico(1L);
            setNomeAllergenico(allergenici.get(0));
        }});
        when(allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenici.get(1))).thenReturn(new Allergenici() {{
            setIdAllergenico(2L);
            setNomeAllergenico(allergenici.get(1));
        }});

        ResponseEntity<ApiSuccess> response = categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, Optional.of(allergenici));


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Categoria e Elemento creati con successo", response.getBody().getMessage());

    }

    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6_7_8_9_10_11_13_14() throws BadRequestException, NotFoundException {

        when(menuService.findByNomeMenu(menu.getNomeMenu())).thenReturn(menu);
        when(categorieRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(categoria);
        }});

        when(categorieRepository.findByOrderByOrdineDesc()).thenReturn(new ArrayList<>() {{
            add(categoria);
        }});
        when(categorieRepository.findBynomeCategoria(categoria.getNomeCategoria())).thenReturn(Optional.of(categoria));
        ResponseEntity<ApiSuccess> response = categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, Optional.of(allergenici));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Categoria e Elemento creati con successo", response.getBody().getMessage());

    }


    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6_7_8_10_11_12_14() throws BadRequestException, NotFoundException {
        when(menuService.findByNomeMenu(menu.getNomeMenu())).thenReturn(menu);

        when(categorieRepository.findBynomeCategoria(categoria.getNomeCategoria())).thenReturn(Optional.of(categoria));
        when(allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenici.get(0))).thenReturn(new Allergenici() {{
            setIdAllergenico(1L);
            setNomeAllergenico(allergenici.get(0));
        }});
        when(allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenici.get(1))).thenReturn(new Allergenici() {{
            setIdAllergenico(2L);
            setNomeAllergenico(allergenici.get(1));
        }});

        ResponseEntity<ApiSuccess> response = categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, Optional.of(allergenici));


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Categoria e Elemento creati con successo", response.getBody().getMessage());

    }

    @Test
    public void test_CreaCategoriaConElemento_WhiteBox_Path_1_2_3_4_5_6_7_8_10_11_13_14() throws NotFoundException, BadRequestException {
        when(menuService.findByNomeMenu(menu.getNomeMenu())).thenReturn(menu);
        when(categorieRepository.findBynomeCategoria(categoria.getNomeCategoria())).thenReturn(Optional.of(categoria));
        when(allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenici.get(0))).thenReturn(null);
        when(allergeniciService.findOneByNomeAllergenicoIgnoreCase(allergenici.get(1))).thenReturn(null);


        ResponseEntity<ApiSuccess> response = categorieService.creaCategoriaConElemento(categoria.getNomeCategoria(), elementi, Optional.of(allergenici));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Categoria e Elemento creati con successo", response.getBody().getMessage());

    }
}
