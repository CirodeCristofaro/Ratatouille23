package com.INGSW2223_V_06.ratatouille23.retrofit.dao;

import android.content.Context;
import android.util.Log;

import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.model.TavoloOrdinaElemento;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.retrofit.ChiamateApi;
import com.INGSW2223_V_06.ratatouille23.retrofit.RetrofitRequestGenerator;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.RouteTavoloInterface;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit.RouteTavolo;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavolo;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavoloOrdinato;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TavoloDao implements RouteTavoloInterface {

    private RouteTavolo routeTavolo;
    private final String TAG = "TavoloDao";

    //Costruttore
    public TavoloDao(Context context) {
        this.routeTavolo =
                RetrofitRequestGenerator.retrofitInstance(ChiamateApi.tavolo, context).create(RouteTavolo.class);

    }

    @Override
    public void elementiDisponibile(Callback callback) {
        Log.i(TAG, "ElementoDisponibile: ");
        routeTavolo.elementoDisponibili().subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Elementi>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(@NonNull List<Elementi> elementis) {
                        Log.i(TAG, "onSuccess: " + elementis);
                        callback.onSuccess(elementis);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: ");
                        callback.onFailure(e);
                    }
                });
    }

    @Override
    public void ordinazioneTavolo(DtoTavolo dtoTavolo,
                                  Callback callback) {
        Log.i(TAG, "ordinazioneTavolo:  ");
        routeTavolo.ordinazioneTavolo(dtoTavolo)
                .subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<ApiSuccess>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ApiSuccess apiSuccess) {
                        Log.i(TAG, "onSuccess: " + apiSuccess);
                        callback.onSuccess(apiSuccess.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        callback.onFailure(e);
                    }
                });
    }

    @Override
    public void elencoTavoliOccupati(Callback callback) {
        Log.i(TAG, "elencoTavoliOccupati: ");
        routeTavolo.elencoTavoliOccupati().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<DtoTavoloOrdinato>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull List<DtoTavoloOrdinato> dtoTavoloOrdinatoes) {
                Log.i(TAG, "onSuccess: " + dtoTavoloOrdinatoes);
                callback.onSuccess(dtoTavoloOrdinatoes);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: ", e);
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void ritornoInformazioniDelTavolo(Long idTavolo, Callback callback) {
        Log.i(TAG, "ritornoInformazioniDelTavolo: ");
        routeTavolo.ritornoInformazioniDelTavolo(idTavolo).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<TavoloOrdinaElemento>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess(@NonNull List<TavoloOrdinaElemento> tavoloOrdinaElementos) {
                Log.i(TAG, "onSuccess: " + tavoloOrdinaElementos);
                callback.onSuccess(tavoloOrdinaElementos);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: ");
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void aggiornaElementoAlTavolo(DtoTavolo dtoTavolo, Callback callback) {
        Log.i(TAG, "aggiornaElementoAlTavolo: ");
        routeTavolo.aggiornaElementoAlTavolo(dtoTavolo)
                .subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<ApiSuccess>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(@NonNull ApiSuccess apiSuccess) {
                        Log.i(TAG, "onSuccess: " + apiSuccess);
                        callback.onSuccess(apiSuccess.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onFailure(e);
                    }
                });
    }
}
