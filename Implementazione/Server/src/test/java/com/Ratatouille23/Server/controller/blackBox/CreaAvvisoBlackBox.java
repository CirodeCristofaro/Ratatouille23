package com.Ratatouille23.Server.controller.blackBox;

import com.Ratatouille23.Server.entity.Avviso;
import com.Ratatouille23.Server.entity.TipoUtente;
import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.AvvisoRepository;
import com.Ratatouille23.Server.repository.UtenteRepository;
import com.Ratatouille23.Server.service.AvvisoService;
import com.Ratatouille23.Server.service.UtenteService;
import com.Ratatouille23.Server.service.VisualizzaAvvisoService;
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CreaAvvisoBlackBox {

    @InjectMocks
    private AvvisoService avvisoService;

    @Mock
    private AvvisoRepository repository;

    @Mock
    private UtenteService utenteService;
    @Mock
    private VisualizzaAvvisoService visualizzaAvvisoService;

    @Mock
    private UtenteRepository utenteRepository;

    private Utente utente = new Utente();
    private String email;
    private Avviso avviso = new Avviso();

    @BeforeEach
    public void init() {

        utente.setNome("Ciro");
        utente.setCognome("de Cristofaro");
        utente.setEmail("prova@gmail.com");
        utente.setTipoUtente(TipoUtente.Amministratore);
    }

    @Test
    public void test_CreaAvviso_O1_C1_D1_E1() {  //ok
        avviso.setOggetto("");
        avviso.setContenuto("");
        avviso.setData(null);
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }
    @Test
    public void test_CreaAvviso_O1_C1_D1_E2() {
        avviso.setOggetto("");
        avviso.setContenuto("");
        avviso.setData(null);
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }
    @Test
    public void test_CreaAvviso_O1_C1_D1_E3() {
        avviso.setOggetto("");
        avviso.setContenuto("");
        avviso.setData(null);
        email = "emailsbagliata.com@";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }




    @Test
    public void test_CreaAvviso_O1_C1_D2_E1() {
        avviso.setOggetto("");
        avviso.setContenuto("");
        avviso.setData(new Date());
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O1_C1_D2_E2() { //ok
        avviso.setOggetto("");
        avviso.setContenuto("");
        avviso.setData(new Date());
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }


    @Test
    public void test_CreaAvviso_O1_C2_D1_E1() {
        avviso.setOggetto("");
        avviso.setContenuto("Contenuto");
        avviso.setData(null);
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O1_C2_D1_E2() { //ok
        avviso.setOggetto("");
        avviso.setContenuto("Contenuto");
        avviso.setData(null);
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O1_C2_D2_E1() {
        avviso.setOggetto("");
        avviso.setContenuto("Contenuto");
        avviso.setData(new Date());
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O1_C2_D2_E2() {//ok
        avviso.setOggetto("");
        avviso.setContenuto("Contenuto");
        avviso.setData(new Date());
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C1_D1_E1() {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("");
        avviso.setData(null);
        email = "";
        assertThrows( BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C1_D1_E2() { //ok
        avviso.setOggetto("oggetto");
        avviso.setContenuto("");
        avviso.setData(null);
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C1_D2_E1() {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("");
        avviso.setData(new Date());
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C1_D2_E2() { //ok
        avviso.setOggetto("oggetto");
        avviso.setContenuto("");
        avviso.setData(new Date());
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }


    @Test
    public void test_CreaAvviso_O2_C2_D1_E1() {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("contenuto");
        avviso.setData(null);
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C2_D1_E2() {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("contenuto");
        avviso.setData(null);
        email = "prova@gmail.com";

        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C2_D2_E1() {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("contenuto");
        avviso.setData(new Date());
        email = "";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C2_D2_E3() {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("contenuto");
        avviso.setData(new Date());
        email = "E-mailsbliat.com1";
        assertThrows(BadRequestException.class, () -> {
            avvisoService.creaAvviso(avviso, email);
        });
    }

    @Test
    public void test_CreaAvviso_O2_C2_D2_E2() throws  BadRequestException, NotFoundException {
        avviso.setOggetto("oggetto");
        avviso.setContenuto("contenuto");
        avviso.setData(new Date());
        email = "prova@gmail.com";
        ResponseEntity<ApiSuccess> response = avvisoService.creaAvviso(avviso, email);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals("Avviso Creato con Successo", response.getBody().getMessage());
    }

}
