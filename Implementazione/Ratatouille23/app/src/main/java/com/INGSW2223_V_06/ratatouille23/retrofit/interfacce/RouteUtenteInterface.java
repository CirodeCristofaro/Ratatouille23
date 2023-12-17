package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce;

import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteRichiesta;

public interface RouteUtenteInterface {

    void reImpostaPassowrd(String password, Callback callback);

    void creaUtente(Utente utente, Callback callback);

    void login(UtenteRichiesta utenteRichiesta, Callback callback);

    void infoUtente(String email,Callback callback);

}
