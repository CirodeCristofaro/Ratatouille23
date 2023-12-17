package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Avviso;
import com.Ratatouille23.Server.entity.Utente;
import com.Ratatouille23.Server.entity.VisualizzaAvviso;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.AvvisoRepository;
import com.Ratatouille23.Server.utils.ApiSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.Ratatouille23.Server.utils.Utils.patternEmail;

@Service
@Slf4j
public class AvvisoService {

    @Autowired
    private AvvisoRepository repository;

    private UtenteService utenteService;

    private VisualizzaAvvisoService visualizzaAvvisoService;

    @Autowired
    public AvvisoService(@Lazy VisualizzaAvvisoService avvisoService, @Lazy UtenteService utenteService) {
        this.visualizzaAvvisoService = avvisoService;
        this.utenteService = utenteService;
    }

    public Avviso saveAvviso(Avviso avviso) {
        return repository.save(avviso);
    }


    public boolean controlloEsistenzaAvvisi(Avviso avviso)  {
        return repository.findAll().stream()
                .anyMatch(avvisi -> avvisi.getOggetto().equalsIgnoreCase(avviso.getOggetto())
                        && avvisi.getContenuto().equalsIgnoreCase(avviso.getContenuto())
                        && confrontaData(avviso.getData(), avvisi.getData()));
    }

    public List<Avviso> findAll() {
        return repository.findAll();
    }


    private boolean confrontaData(Date dataInviata, Date dataDaConfrontare) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String dataFormattata1 = simpleDateFormat.format(dataInviata);
        String dataFormattata2 = simpleDateFormat.format(dataDaConfrontare);

        if (dataFormattata1.equals(dataFormattata2)) {
            log.info("Date uguali");
            return true;
        }
        return false;
    }


    public ResponseEntity<ApiSuccess> creaAvviso(Avviso avviso, String email) throws BadRequestException, NotFoundException {

        if (email.trim().isEmpty() || !patternEmail(email)) { //1
            throw  new BadRequestException("email non specificata o formato non valida");
        }
        Utente controllaEsistenza = utenteService.findByEmail(email); //2

        if (controlloCampiAvviso(avviso)) { //3
            throw  new BadRequestException("Uno o più campi dell avviso non sono stati definiti"
                    );
        }
        if (controlloEsistenzaAvvisi(avviso)) { //4
            throw new BadRequestException("Avviso già  esistente per la data odierna");
        }

        avviso.setUtenteCreaAvviso(controllaEsistenza);
        saveAvviso(avviso);
        List<Utente> listaDiUtenti = utenteService.findAll();
        listaDiUtenti.removeIf(utenti -> utenti.getEmail().equalsIgnoreCase(controllaEsistenza.getEmail()));
        log.info("lista di utente che ricevono l'avviso : {}", listaDiUtenti);
        for (Utente utentiCheRicevanoLEmail : listaDiUtenti) {//5
            VisualizzaAvviso invioAvviso = new VisualizzaAvviso(); //6
            invioAvviso.setAvvisoRicevuto(avviso);
            invioAvviso.setUtenteCheRiceveLAvviso(utentiCheRicevanoLEmail);
            visualizzaAvvisoService.saveVisualizzaAvviso(invioAvviso);
        }
        log.info("response 201 creazione avviso");//7
        return ResponseEntity.status(201)
                .contentType(MediaType.APPLICATION_JSON).
                body(new ApiSuccess(201, "Avviso Creato con Successo"));
    }

    private boolean controlloCampiAvviso(Avviso avviso) {
        return avviso == null || (avviso.getContenuto() == null
                || avviso.getContenuto().trim().isEmpty()) ||
                (avviso.getOggetto() == null || avviso.getOggetto().trim().isEmpty())
                || avviso.getData() == null;
    }

}
