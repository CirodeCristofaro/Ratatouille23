package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.Elementi;
import com.Ratatouille23.Server.entity.Tavolo;
import com.Ratatouille23.Server.entity.TavoloOrdinaElemento;
import com.Ratatouille23.Server.handler.exception.InternalServerException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.TavoloRepository;
import com.Ratatouille23.Server.utils.ApiSuccess;
import com.Ratatouille23.Server.utils.DtoElementi;
import com.Ratatouille23.Server.utils.DtoTavoloOrdinato;
import com.Ratatouille23.Server.utils.TavoloWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TavoloService {
    @Autowired
    private TavoloRepository service;
    @Autowired
    private TavoloOrdinaElementoService tavoloOrdinaElementoService;
    private UtenteService utenteService;

    @Autowired
    private ElementiService elementiService;

    public TavoloService(@Lazy UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    public Tavolo save(Tavolo tavolo) {
        return service.save(tavolo);
    }

    public List<Tavolo> findAll() {
        return service.findAll();
    }


    public boolean existsByNumeroTavolo(int numeroTavolo)   {
        return service.existsByNumeroTavolo(numeroTavolo);
    }

    public ResponseEntity<ApiSuccess> ordinazioneTavolo(@RequestBody TavoloWrapper tavoloWrapper) throws InternalServerException, NotFoundException {

        if (existsByNumeroTavolo(tavoloWrapper.getTavolo().getNumeroTavolo())) {
            throw new InternalServerException("il tavolo risulta occupato, controllare il n° tavolo");
        }

        log.info("dtoelementi : {}", tavoloWrapper.getDtoElementi());
        for (DtoElementi elementi : tavoloWrapper.getDtoElementi()) {
            TavoloOrdinaElemento tavoloOrdinaElemento = new TavoloOrdinaElemento();
            tavoloOrdinaElemento.setTavolo(tavoloWrapper.getTavolo());
            Elementi elementoOrdinato= elementiService.findOneByNomeElementoIgnoreCase(elementi.getElementiOrdinata().getNomeElemento());
            tavoloOrdinaElemento.setQuantita(elementi.getQuantita());
            tavoloOrdinaElemento.setElementiOrdinati(elementoOrdinato);
            tavoloWrapper.getTavolo().setUtenteGestisceTavolo(utenteService.findAllAddettiAllaSala());
            log.info("Tavolo : {}", tavoloWrapper.getTavolo());
            save(tavoloWrapper.getTavolo());
            tavoloOrdinaElementoService.save(tavoloOrdinaElemento);
        }
        return ResponseEntity.status(201).
                body(new ApiSuccess(201, "Ordinazione Creata con successo"));

    }

    public List<DtoTavoloOrdinato> elencoTavoliOccupati() {
        List<Tavolo> tavoloLista = findAll();
        List<DtoTavoloOrdinato> dtoTavoloOrdinatoList = new ArrayList<>();
        for (Tavolo tavoli : tavoloLista) {
            BigDecimal totaleCosto = BigDecimal.ZERO;
            int totElementi = 0;
            for (TavoloOrdinaElemento tavoloOrdinaElemento : tavoli.getTavoloOrdinaElemento()) {
                totaleCosto = totaleCosto.add(tavoloOrdinaElemento.getElementiOrdinati().getPrezzo().multiply(BigDecimal.valueOf(tavoloOrdinaElemento.getQuantita())));
                totElementi += tavoloOrdinaElemento.getQuantita();
            }
            final BigDecimal finalTotaleCosto = totaleCosto;
            final int finalTotElementi = totElementi;
            DtoTavoloOrdinato dtoTavoloOrdinato = new DtoTavoloOrdinato();
            dtoTavoloOrdinato.setIdTavoloOrdinato(tavoli.getIdTavolo());
            dtoTavoloOrdinato.setNumeroTavolo(tavoli.getNumeroTavolo());
            dtoTavoloOrdinato.setTotaleCosto(finalTotaleCosto);
            dtoTavoloOrdinato.setTotaleElementi(finalTotElementi);
            dtoTavoloOrdinatoList.add(dtoTavoloOrdinato);
        }
        return dtoTavoloOrdinatoList;
    }

}
