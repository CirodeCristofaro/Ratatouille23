package com.Ratatouille23.Server.controller.whiteBox;

import com.Ratatouille23.Server.entity.TipoUtente;
import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.handler.exception.InternalServerException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.UtenteRepository;
import com.Ratatouille23.Server.service.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ReImpostaPasswordWhiteBox {

    @InjectMocks
    UtenteService utenteService;
    @Mock
    UtenteRepository utenteRepository;
    private Utente utente;
    private String email;
    private String password;
    @Mock
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    public void init(){
        utente= new Utente();
        email="ingsw23@gmail.com";
        utente=new Utente();
        utente.setEmail(email);
        utente.setNome("ciro");
        utente.setCognome("de Cristofaro");

        utente.setPassword(passwordEncoder.encode("Password72!"));
        utente.setTipoUtente(TipoUtente.Amministratore);
        utente.setPrimoAccesso(new Date());
        password="Password7!";
    }

    @Test
    public void test_reImpostaPassword_path_1(){
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            utenteService.reImpostaPassword(email,password);
        });
        assertEquals("Utente non trovato", exception.getMessage());

    }
    @Test
    public void test_reImpostaPassword_path_1_2(){
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.of(utente));

        InternalServerException exception = assertThrows(InternalServerException.class, () -> {
            utenteService.reImpostaPassword(email,password);
        });
        assertEquals("Errore: primo accesso già effettuato in precedenza", exception.getMessage());

    }

    @Test
    public void test_reImpostaPassword_path_1_2_3(){
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.of(utente));
        utente.setPrimoAccesso(null);
        password="PasswordNonValida!";
        InternalServerException exception = assertThrows(InternalServerException.class, () -> {
            utenteService.reImpostaPassword(email,password);
        });
        assertEquals("Formato password non valido", exception.getMessage());
    }

    @Test
    public void test_reImpostaPassword_path_1_2_3_4(){
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.of(utente));
        utente.setPrimoAccesso(null);
        utente.setPassword(passwordEncoder.encode(password));
        when(passwordEncoder.matches(password,utente.getPassword())).thenReturn(true);
        InternalServerException exception = assertThrows(InternalServerException.class, () -> {
            utenteService.reImpostaPassword(email,password);
        });

        assertEquals("La password deve essere diversa da quella inserita dall'Amministratore", exception.getMessage());

    }
    @Test
    public void test_reImpostaPassword_path_1_2_3_4_5() throws InternalServerException, NotFoundException {
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.of(utente));
        utente.setPrimoAccesso(null);
        ResponseEntity<Utente> response=utenteService.reImpostaPassword(email,password);
       assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
