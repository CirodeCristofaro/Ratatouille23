package com.INGSW2223_V_06.ratatouille23.retrofit.dao;

import android.content.Context;
import android.util.Log;

import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.model.TipoVisualizzazione;
import com.INGSW2223_V_06.ratatouille23.model.VisualizzaAvviso;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.retrofit.ChiamateApi;
import com.INGSW2223_V_06.ratatouille23.retrofit.RetrofitRequestGenerator;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.RouteAvvisoInterface;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit.RouteAvviso;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class AvvisoDao implements RouteAvvisoInterface {
    private RouteAvviso routeAvviso;
    private final String TAG = "AvvisoDao";


    public AvvisoDao(Context context) {
        this.routeAvviso =
                RetrofitRequestGenerator.retrofitInstance(ChiamateApi.avviso, context).create(RouteAvviso.class);

    }

    @Override
    public void creaAvviso(Avviso avviso, Callback callback) {
        Log.i(TAG, "AvvisoDao: ");
        routeAvviso.creaAvviso(avviso).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiSuccess>() {
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
                        Log.i(TAG, "onError AvvisoDao  " + e.getMessage() + " " + e.getLocalizedMessage());
                        callback.onFailure(e);
                    }
                });
    }


    @Override
    public void returnAvvisiDiUnoSpecificoUtente(Callback callback) {
        routeAvviso.returnAvvisiDiUnoSpecificoUtente().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<VisualizzaAvviso>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe returnAvvisiDiUnoSpecificoUtente");
                    }

                    @Override
                    public void onSuccess(@NonNull ArrayList<VisualizzaAvviso> avvisi) {
                        Log.i(TAG, "onSuccess returnAvvisiDiUnoSpecificoUtente");
                        callback.onSuccess(avvisi);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError returnAvvisiDiUnoSpecificoUtente " + e.getMessage());
                        callback.onFailure(e);
                    }
                });

    }

    @Override
    public void cambiaStatoAvviso(long idAvviso, TipoVisualizzazione tipoVisualizzazione, Callback callback) {
        routeAvviso.aggiornaStatoAvviso(idAvviso, tipoVisualizzazione).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe cambia Stato Avviso");
                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> unused) {
                        Log.i(TAG, "onSuccess cambia Stato Avviso");
                        callback.onSuccess(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError cambia Stato Avviso " + e.getMessage());
                        callback.onFailure(e);

                    }
                });
    }
}