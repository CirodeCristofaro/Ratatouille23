package com.Ratatouille23.Server.controller;

import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.handler.exception.InternalServerException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.security.response.UtenteResponse;
import com.Ratatouille23.Server.security.response.UtenteRichiesta;
import com.Ratatouille23.Server.service.UtenteService;
import com.Ratatouille23.Server.utils.ApiSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/account")
public class UtenteRestController {

    @Autowired
    private UtenteService repositoryUtente;


    /**
     * Reimposta la passsword dell'utente
     *
     * @param authentication L'oggetto Authentication che rappresenta l'utente autenticato
     * @param nuovaPassword  la nuova password da inserire
     * @return ResponseEntity contiene l'oggetto Utente con la password reimpostata
     * @throws InternalServerException se si verifica un errore durante l'aggiornamento della password
     * @throws  NotFoundException se si verifica un errore durante la ricerca dell'utente a cui cambiare la password
     */
    @PatchMapping("/reimpostapassword")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore') OR hasAuthority('AddettoAllaSala') OR hasAuthority('AddettoAllaCucina')")
    public ResponseEntity<Utente> reImpostaPassowrd(Authentication authentication, @RequestParam("password") String nuovaPassword) throws InternalServerException, NotFoundException {
        return repositoryUtente.reImpostaPassword(authentication.getName(), nuovaPassword);
    }


    /**
     * Gestisce le richieste di login degli utente
     *
     * @param utenteRichiesta l'oggetto UtenteRichiesta contiene le informazioni di autenticazione email-password
     * @return UtenteResponse contiene le informazioni dell'utente autenticato tra cui il token e altre informazioni
     */
    @PostMapping("/login")
    public UtenteResponse login(@RequestBody final UtenteRichiesta utenteRichiesta) throws NotFoundException {
        return repositoryUtente.login(utenteRichiesta.getEmail(), utenteRichiesta.getPassword());
    }

    /**
     * Permette di creare nuove utenze del personale
     *
     * @param utente l'oggetto Utente contiene informazioni dell'utente da creare
     * @return ResponseEntity  contiene il messaggio e il codice di successo dell'operazione
     * @throws InternalServerException se si verifica un errore durante la creazione dell'utente
     * @throws  NotFoundException se si verifa un errore durante la ricerca del menu a cui associarlo
     */
    @PostMapping("/creaUtenza")
    @PreAuthorize("hasAuthority('Amministratore')")
    public ResponseEntity<ApiSuccess> creaUtenza(@RequestBody Utente utente) throws InternalServerException, NotFoundException {
        return repositoryUtente.creaUtente(utente);
    }


    @GetMapping("/infoUtente")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore') OR hasAuthority('AddettoAllaSala') OR hasAuthority('AddettoAllaCucina')")
    public ResponseEntity<Utente> infoUtente(@RequestParam("email") String email) throws NotFoundException {
        return repositoryUtente.informazioniUtente(email);
    }

}
