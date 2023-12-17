package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit;

import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.model.TipoVisualizzazione;
import com.INGSW2223_V_06.ratatouille23.model.VisualizzaAvviso;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RouteAvviso {

    @POST("creaAvviso")
    Single<ApiSuccess> creaAvviso(@Body Avviso avviso);

    @GET("returnAvvisiDiUnoSpecificoUtente")
    Single<ArrayList<VisualizzaAvviso>> returnAvvisiDiUnoSpecificoUtente();

    @PATCH("aggiornaStatoAvviso")
    Single<Response<Void>> aggiornaStatoAvviso(@Query("idAvviso") Long idAvviso,
                                               @Query("tipoVisualizzazione") TipoVisualizzazione tipoVisualizzazione);

}
