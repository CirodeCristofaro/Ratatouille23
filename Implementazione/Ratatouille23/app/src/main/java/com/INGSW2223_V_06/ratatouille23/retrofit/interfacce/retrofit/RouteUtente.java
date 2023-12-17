package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit;

import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteResponse;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteRichiesta;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RouteUtente {

    @PATCH("reimpostapassword")
    Single<Utente> reImpostaPassowrd(@Query("password") String password);

    @POST("creaUtenza")
    Single<ApiSuccess> creaUtenza(@Body Utente utente);
    @POST("login")
    Single<UtenteResponse> login(@Body UtenteRichiesta credenziali);

    @GET("infoUtente")
    Single<Utente> infoUtente(@Query("email") String email);
}

