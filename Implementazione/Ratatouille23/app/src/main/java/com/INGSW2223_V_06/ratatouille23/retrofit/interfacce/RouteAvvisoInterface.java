package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce;

import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.model.TipoVisualizzazione;

public interface RouteAvvisoInterface {

    void creaAvviso(Avviso avviso, Callback callback);


    void returnAvvisiDiUnoSpecificoUtente(Callback callback);

    void cambiaStatoAvviso(long idAvviso, TipoVisualizzazione tipoVisualizzazione, Callback callback);
}
