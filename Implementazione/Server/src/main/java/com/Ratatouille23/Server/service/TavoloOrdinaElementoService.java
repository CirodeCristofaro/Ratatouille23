package com.Ratatouille23.Server.service;

import com.Ratatouille23.Server.entity.TavoloOrdinaElemento;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.repository.TavoloOrdinaElementoRepository;
import com.Ratatouille23.Server.utils.ApiSuccess;
import com.Ratatouille23.Server.utils.DtoElementi;
import com.Ratatouille23.Server.utils.TavoloWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TavoloOrdinaElementoService {

    @Autowired
    private TavoloOrdinaElementoRepository repository;

    @Autowired
    private  ElementiService elementiService;

    public TavoloOrdinaElemento save(TavoloOrdinaElemento tavoloOrdinaElemento) {
        return repository.save(tavoloOrdinaElemento);
    }

    public List<TavoloOrdinaElemento> findAllInfoTavolo(Long idTavolo) {
        return repository.findAll().stream().filter(tavolo -> tavolo.getTavolo().getIdTavolo().equals(idTavolo)).collect(Collectors.toList());
    }


    public TavoloOrdinaElemento findByTavoloAndElementoOrdinata(TavoloOrdinaElemento tavolo, DtoElementi elemento) {
        return repository.findByTavoloAndElementiOrdinati(tavolo.getTavolo(), elemento.getElementiOrdinata());
    }


    public void deleteElementoEsistente(TavoloOrdinaElemento tavoloOrdinaElemento) throws  NotFoundException {
        if (tavoloOrdinaElemento != null) {
            try {
                repository.delete(tavoloOrdinaElemento);
            } catch (IllegalArgumentException e) {

                throw new NotFoundException( "Elemento Non Trovato");
            }
        }
        throw new NotFoundException( "Elemento Non Trovato");
    }


    public ResponseEntity<ApiSuccess> aggiornaElementiAlTavolo(TavoloWrapper tavoloWrapper) throws  NotFoundException {
        log.info("tavoloWrapper : {} ", tavoloWrapper);

        List<TavoloOrdinaElemento> tavoloOrdinaElementoList = findAllInfoTavolo(tavoloWrapper.getTavolo().getIdTavolo());
        for (TavoloOrdinaElemento tavoloOrdinaElemento : tavoloOrdinaElementoList) {
            for (DtoElementi elementi : tavoloWrapper.getDtoElementi()) {
                TavoloOrdinaElemento tavoloOrdinaElementoEsistente = findByTavoloAndElementoOrdinata(tavoloOrdinaElemento, elementi);
                if (tavoloOrdinaElementoEsistente != null) {
                    if (elementi.getQuantita() == 0) {
                        deleteElementoEsistente(tavoloOrdinaElementoEsistente);
                    } else {
                        tavoloOrdinaElementoEsistente.setQuantita(elementi.getQuantita());
                        save(tavoloOrdinaElementoEsistente);
                    }

                } else {
                    if(elementiService.checkIfElementoExists(elementi.getElementiOrdinata().getNomeElemento())){
                        TavoloOrdinaElemento nuovaElemento = new TavoloOrdinaElemento();
                        nuovaElemento.setTavolo(tavoloOrdinaElemento.getTavolo());
                        nuovaElemento.setQuantita(elementi.getQuantita());
                        nuovaElemento.setElementiOrdinati(elementi.getElementiOrdinata());
                        save(nuovaElemento);
                    }

                }
            }
        }
        return ResponseEntity.status(200).
                body(new ApiSuccess(200, "Ordinazione aggiornata con successo"));
    }
}
