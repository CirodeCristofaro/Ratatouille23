package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit;

import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.model.TavoloOrdinaElemento;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavolo;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavoloOrdinato;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RouteTavolo {
    @GET("elementiDisponibili")
    Single<List<Elementi>> elementoDisponibili();

    @POST("ordinazioneTavolo")
    Single<ApiSuccess> ordinazioneTavolo(@Body DtoTavolo dtoTavolo);

    @GET("elencoTavoliOccupati")
    Single<List<DtoTavoloOrdinato>> elencoTavoliOccupati();

    @GET("ritornoInformazioniDelTavolo")
    Single<List<TavoloOrdinaElemento>> ritornoInformazioniDelTavolo(@Query("idTavolo") Long idTavolo);

    @PATCH("aggiornaElementiAlTavolo")
    Single<ApiSuccess> aggiornaElementoAlTavolo(@Body DtoTavolo dtoTavolo);

}
