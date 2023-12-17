package com.INGSW2223_V_06.ratatouille23.retrofit.dao;

import android.content.Context;
import android.util.Log;

import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.retrofit.ChiamateApi;
import com.INGSW2223_V_06.ratatouille23.retrofit.RetrofitRequestGenerator;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteResponse;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteRichiesta;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.RouteUtenteInterface;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit.RouteUtente;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UtenteDao implements RouteUtenteInterface {

    private RouteUtente routeUtente;
    private final String TAG = "getLocalUserInfo";


    //Costruttore
    public UtenteDao(Context context) {
        this.routeUtente = RetrofitRequestGenerator.retrofitInstance(ChiamateApi.utente, context)
                .create(RouteUtente.class);
    }


    @Override
    public void reImpostaPassowrd(String password, Callback callback) {
        Log.i(TAG, "reimposta Passowrd Utente");
        routeUtente.reImpostaPassowrd(password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Utente>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe DAO: reimpostapassword started. " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull Utente utente) {
                        Log.i(TAG, "onSuccess DAO: reimpostapassword started.");
                        callback.onSuccess(utente);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError DAO: reimpostapassword started. " + e);
                        Log.i(TAG, e.toString());
                        Log.e(TAG, e.getMessage());
                        callback.onFailure(e);

                    }
                });
    }


    @Override
    public void creaUtente(Utente utente, Callback callback) {
        Log.i(TAG, "Crea Utente");
        routeUtente.creaUtenza(utente)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiSuccess>() {
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
                        Log.e(TAG, e.getMessage());
                        callback.onFailure(e);
                    }
                });

    }

    @Override
    public void login(UtenteRichiesta utenteRichiesta, Callback callback) {
        routeUtente.login(utenteRichiesta).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UtenteResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull UtenteResponse utenteResponse) {
                        Log.i(TAG, "onSuccess: " + utenteResponse.toString());
                        callback.onSuccess(utenteResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.toString());
                        callback.onFailure(e);
                    }
                });
    }

    @Override
    public void infoUtente(String email, Callback callback) {
        routeUtente.infoUtente(email).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Utente>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Utente utente) {
                        Log.i(TAG, "onSuccess: " + utente.toString());
                        callback.onSuccess(utente);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.toString());
                        callback.onFailure(e);
                    }
                });
    }


}
