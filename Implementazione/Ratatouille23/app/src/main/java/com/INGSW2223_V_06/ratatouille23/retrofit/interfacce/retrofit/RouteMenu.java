package com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit;

import com.INGSW2223_V_06.ratatouille23.model.Categorie;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.ProductOpenFood;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RouteMenu {

    @GET("tutteLeCategorie")
    Single<List<Categorie>> getCategorie();

    @GET("tuttiGliElementi")
    Single<List<Elementi>> tuttiGliElementi(@Query("categoria") String categoria);

    @POST("ordinaCategorie")
    Single<Response<Void>> ordinaCategorie(@Body List<Categorie> categoria);

    @POST("ordinaElementi")
    Single<Response<Void>> ordinaElementi(@Body List<Elementi> elementi);

    @GET("autocompleteOpenFood")
    Single<List<ProductOpenFood>> autocomplete(@Query("cerca") String cerca);


    @POST("creaCategoriaConElemento")
    Single<ApiSuccess> creaCategoriaConElemento(@Query("nomeCategoria") String nomeCategoria, @Body Elementi elementi,
                                                @Query("allergenici")List<String> allergenici);


    @DELETE("cancellaElemento")
    Single<ApiSuccess> cancellaElemento(@Query("elemento") String elemento);


    @POST("aggiungiElemento")
    Single<ApiSuccess> aggiungiElemento(@Body Elementi nuovaElemento,
                                        @Query("categoria")String categoria,
                                        @Query("allergenici")List<String>allergenici);

}
