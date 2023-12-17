package com.Ratatouille23.Server.controller;

import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.entity.TavoloOrdinaElemento;
import com.Ratatouille23.Server.handler.exception.InternalServerException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.service.ElementiService;
import com.Ratatouille23.Server.service.TavoloOrdinaElementoService;
import com.Ratatouille23.Server.service.TavoloService;
import com.Ratatouille23.Server.utils.ApiSuccess;
import com.Ratatouille23.Server.utils.DtoTavoloOrdinato;
import com.Ratatouille23.Server.utils.TavoloWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tavolo")
public class TavoloRestController {

    @Autowired
    private ElementiService elementiService;

    @Autowired
    private TavoloService tavoloService;

    @Autowired
    private TavoloOrdinaElementoService tavoloOrdinaElementoService;


    /**
     * Ritorna una lista di elemento in disponibili
     *
     * @return Lista di elemento disponibili
     */
    @GetMapping("/elementiDisponibili")
    @PreAuthorize("hasAuthority('AddettoAllaSala')")
    public List<Elementi> elementiDisponibili() {
        return elementiService.findAllElementiVisibili();
    }


    /**
     * Gestisce l'ordinazione di un tavolo
     *
     * @param tavoloWrapper l'oggetto TavoloWrapper contiene le informazioni sull'ordinazione del tavolo
     * @return ResponseEntity contiene il messaggio e il codice di successo dell'operazione
     * @throws  InternalServerException se si verifa un errore durante la creazione dell ordine
     */
    @PostMapping("/ordinazioneTavolo")
    @PreAuthorize("hasAuthority('AddettoAllaSala')")
    public ResponseEntity<ApiSuccess> ordinazioneTavolo(@RequestBody TavoloWrapper tavoloWrapper) throws InternalServerException, NotFoundException {
        return tavoloService.ordinazioneTavolo(tavoloWrapper);
    }

    /**
     * Elenco dei tavoli occupati
     *
     * @return Lista dei tavoli occupati
     */
    @GetMapping("/elencoTavoliOccupati")
    @PreAuthorize("hasAuthority('AddettoAllaSala')")
    public List<DtoTavoloOrdinato> elencoTavoliOccupati() {
        return tavoloService.elencoTavoliOccupati();

    }

    /**
     * Ritorna le inforazioni di uno specifico tavolo
     *
     * @param idTavolo id del tavolo da cercare
     * @return Lista delle elementi ordinate e la quantita
     */
    @GetMapping("/ritornoInformazioniDelTavolo")
    @PreAuthorize("hasAuthority('AddettoAllaSala')")
    public List<TavoloOrdinaElemento> ritornoInformazioniDelTavolo(@RequestParam("idTavolo") Long idTavolo) {
        log.info("idTavolo : {} , tavolo : {}", idTavolo, tavoloOrdinaElementoService.findAllInfoTavolo(idTavolo));
        return tavoloOrdinaElementoService.findAllInfoTavolo(idTavolo);
    }

    /**
     * Aggiorna le elementi di un tavolo
     *
     * @param tavoloWrapper l'oggetto TavoloWrapper che contiene le informazioni del tavolo
     * @return ResponseEntity contiene il messaggio e il codice di successo dell'operazione
     * @throws NotFoundException se si verifica un errore durante l'aggiornamento degli elementi del tavolo
     */
    @PatchMapping("/aggiornaElementiAlTavolo")
    @PreAuthorize("hasAuthority('AddettoAllaSala')")
    public ResponseEntity<ApiSuccess> aggiornaElementiAlTavolo(@RequestBody TavoloWrapper tavoloWrapper) throws  NotFoundException {
        return tavoloOrdinaElementoService.aggiornaElementiAlTavolo(tavoloWrapper);
    }

}
