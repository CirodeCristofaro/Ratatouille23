package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce;

import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavolo;

public interface RouteTavoloInterface {

    void elementiDisponibile(Callback callback);

    void ordinazioneTavolo(DtoTavolo dtoTavolo,
                           Callback callback);

    void elencoTavoliOccupati(Callback callback);

    void ritornoInformazioniDelTavolo(Long idTavolo, Callback callback);

    void aggiornaElementoAlTavolo(DtoTavolo dtoTavolo, Callback callback);
}
