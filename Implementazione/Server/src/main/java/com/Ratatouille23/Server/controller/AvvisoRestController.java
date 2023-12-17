package com.Ratatouille23.Server.controller;

import com.Ratatouille23.Server.entity.Avviso;
import com.Ratatouille23.Server.entity.TipoVisualizzazione;
import com.Ratatouille23.Server.entity.VisualizzaAvviso;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.service.AvvisoService;
import com.Ratatouille23.Server.service.VisualizzaAvvisoService;
import com.Ratatouille23.Server.utils.ApiSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/avviso")
public class AvvisoRestController {


    private AvvisoService avvisoService;


    private VisualizzaAvvisoService visualizzaAvvisoService;

    @Autowired
    public AvvisoRestController(AvvisoService avvisoService, VisualizzaAvvisoService visualizzaAvvisoService) {
        this.avvisoService = avvisoService;
        this.visualizzaAvvisoService = visualizzaAvvisoService;
    }

    /**
     * Restituisce una lista di avvisi per un utente specifico.
     *
     * @param authentication L'oggetto Authentication che rappresenta l'utente autenticato.
     * @return Una lista di oggetti VisualizzaAvviso che rappresentano gli avvisi dell'utente.
     */
    @GetMapping("/returnAvvisiDiUnoSpecificoUtente")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore') OR hasAuthority('AddettoAllaSala') OR hasAuthority('AddettoAllaCucina')")
    public List<VisualizzaAvviso> returnAvvisiDiUnoSpecificoUtente(Authentication authentication) {
        return visualizzaAvvisoService.returnAvvisiDiUnoSpecificoUtente(authentication.getName());
    }


    /***
     *  Aggiorna lo stato di visualizzazione di un Avviso
     *
     * @param  authentication   L'oggetto Authentication che rappresenta l'utente autenticato a cui viene aggiornato l avvisso.
     * @param  idAvviso indice dell'avviso che deve essere aggiornatot
     * @param  tipoVisualizzazione TipodiVisualizzazione visualizzato - nascosto
     * @throws  NotFoundException quando si vuole aggiornar1e lo stato di un avviso a un email non esistente
     * */
    @PatchMapping("/aggiornaStatoAvviso")
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore') OR hasAuthority('AddettoAllaSala') OR hasAuthority('AddettoAllaCucina')")
    public void aggiornaStatoAvviso(Authentication authentication, @RequestParam("idAvviso") Long idAvviso, @RequestParam("tipoVisualizzazione") TipoVisualizzazione tipoVisualizzazione) throws NotFoundException {
        visualizzaAvvisoService.aggiornaStatoAvviso(authentication.getName(), idAvviso, tipoVisualizzazione);
    }


    /**
     * Creazione di un nuovo Avviso nel sistema
     *
     * @param avviso         l'oggetto Avviso che contiene i dati dell'avviso da creare
     * @param authentication L'oggetto Authentication che rappresenta l'utente autenticato
     * @return ResponseEntity contiene il messaggio e il codice di successo dell'operazione
     * @throws BadRequestException se si verifica un errore durante la creazione dell'avviso
     * @throws  NotFoundException quando un email non esistente cerca di creare un avviso
     **/
    @PreAuthorize("hasAuthority('Amministratore') OR hasAuthority('Supervisore')")
    @PostMapping("/creaAvviso")
    public ResponseEntity<ApiSuccess> creaAvviso(@RequestBody Avviso avviso, Authentication authentication) throws BadRequestException, NotFoundException {
        return avvisoService.creaAvviso(avviso, authentication.getName());

    }


}
