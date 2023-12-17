package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.TipoVisualizzazione;
import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.entity.VisualizzaAvviso;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.VisualizzaAvvisoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VisualizzaAvvisoService {

    @Autowired
    private VisualizzaAvvisoRepository repository;
    @Autowired
    private UtenteService utenteService;

    public List<VisualizzaAvviso> returnAvvisiDiUnoSpecificoUtente(String email) {
         return repository.findAll().stream()
                .filter(visualizzaAvviso ->
                        visualizzaAvviso.getUtenteCheRiceveLAvviso().getEmail().equals(email) &&
                                (visualizzaAvviso.getVisualizzazione() == null
                                        || visualizzaAvviso.getVisualizzazione().equals(TipoVisualizzazione.visualizzato)))
                .sorted(Comparator.comparing(visualizza -> visualizza.getAvvisoRicevuto().getData(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<VisualizzaAvviso> findAll() {
        return repository.findAll();
    }

    public void saveVisualizzaAvviso(VisualizzaAvviso visualizzaAvviso) {
         repository.save(visualizzaAvviso);
    }

    public void aggiornaStatoAvviso(String email, Long idAvviso,
                                    TipoVisualizzazione tipoVisualizzazione) throws  NotFoundException {
        log.info("email : {} , id : {}", email, idAvviso);
        Utente utente = utenteService.findByEmail(email);
        List<VisualizzaAvviso> visualizzaAvvisoList = findAll();
        for (VisualizzaAvviso visualizzaAvviso : visualizzaAvvisoList) {
            if (visualizzaAvviso.getAvvisoRicevuto().getIdAvviso().equals(idAvviso) && visualizzaAvviso.getUtenteCheRiceveLAvviso().equals(utente)) {
                visualizzaAvviso.setVisualizzazione(tipoVisualizzazione);
                log.info("cambio stato : {}", visualizzaAvviso);
                saveVisualizzaAvviso(visualizzaAvviso);

            }
        }

    }
}
