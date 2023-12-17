package com.INGSW2223_V_06.ratatouille23.retrofit.dao;

import android.content.Context;
import android.util.Log;

import com.INGSW2223_V_06.ratatouille23.model.Categorie;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.retrofit.ApiSuccess;
import com.INGSW2223_V_06.ratatouille23.retrofit.ChiamateApi;
import com.INGSW2223_V_06.ratatouille23.retrofit.RetrofitRequestGenerator;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.RouteMenuInterface;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.retrofit.RouteMenu;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.ProductOpenFood;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;


public class MenuDao implements RouteMenuInterface {
    private RouteMenu routeMenu;
    private final String TAG = "MenuDao";

    //Costruttore
    public MenuDao(Context context) {
        this.routeMenu =
                RetrofitRequestGenerator.retrofitInstance(ChiamateApi.menu, context).create(RouteMenu.class);

    }

    @Override
    public void getCategorie(Callback callback) {
        Log.i(TAG, "getCategorie: ");
        routeMenu.getCategorie().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Categorie>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, " onSubscribe getCategorie: ");
            }

            @Override
            public void onSuccess(@NonNull List<Categorie> categories) {
                Log.i(TAG, " onSuccess getCategorie: ");
                callback.onSuccess(categories);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError getCategorie 1:   ", e);
                Log.i(TAG, " onError getCategorie: " + e.getMessage());
                callback.onFailure(e);
            }
        });

    }

    @Override
    public void allElementi(String elemento, Callback callback) {
        Log.i(TAG, "getallElemento: ");
        routeMenu.tuttiGliElementi(elemento).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Elementi>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, " onSubscribe getallElemento: ");
            }

            @Override
            public void onSuccess(@NonNull List<Elementi> elementis) {
                Log.i(TAG, " onSuccess getallElemento: " + elementis.toString());
                callback.onSuccess(elementis);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, " onError getallElemento: " + e.getMessage());
                callback.onFailure(e);
            }
        });

    }


    @Override
    public void ordinaCategorie(List<Categorie> categoria, Callback callback) {
        routeMenu.ordinaCategorie(categoria).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Response<Void>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe ordinaCategorie: ");
            }

            @Override
            public void onSuccess(@NonNull Response<Void> voidResponse) {
                Log.i(TAG, "onSuccess ordinaCategorie: ");
                callback.onSuccess(voidResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError ordinaCategorie: ");
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void ordinaElementi(List<Elementi> elementi, Callback callback) {
        routeMenu.ordinaElementi(elementi).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Response<Void>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe ordinaElemento: ");
            }

            @Override
            public void onSuccess(@NonNull Response<Void> voidResponse) {
                Log.i(TAG, "onSuccess ordinaElemento: ");
                callback.onSuccess(voidResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError ordinaElemento: ");
                callback.onFailure(e);
            }
        });
    }

    @Override
    public void autocomplete(String cerca, Callback callback) {
        Log.i(TAG, "autocomplete: ");
        routeMenu.autocomplete(cerca).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<ProductOpenFood>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe autocomplete: ");
            }

            @Override
            public void onSuccess(@NonNull List<ProductOpenFood> Elementos) {
                Log.i(TAG, "onSuccess autocomplete: " + Elementos);
                callback.onSuccess(Elementos);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError autocomplete: " + e.getMessage());
            }
        });

    }


    @Override
    public void creaCategoriaConElemento(String nomeCategoria, Elementi elementi, List<String> allergenici, Callback callback) {
        Log.i(TAG, "creaCategoriaConelemento: ");
        routeMenu.creaCategoriaConElemento(nomeCategoria, elementi, allergenici)
                .subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(
                        new SingleObserver<ApiSuccess>() {

                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.i(TAG, "onSubscribe: creaCategoriaConelemento");
                            }

                            @Override
                            public void onSuccess(@NonNull ApiSuccess apiSuccess) {
                                callback.onSuccess(apiSuccess.getMessage());
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                callback.onFailure(e);
                            }
                        }
                );

    }


    @Override
    public void cancellaElemento(String elemento, Callback callback) {
        Log.i(TAG, "cencallaelemento: ");
        routeMenu.cancellaElemento(elemento).subscribeOn(Schedulers.newThread())
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
                        Log.i(TAG, "onError: " + e);
                        callback.onFailure(e);
                    }
                });
    }



    @Override
    public void aggiungiElemento(Elementi nuovaElementi, String categoria,
                                 List<String> allergenici,
                                 Callback callback) {
        Log.i(TAG, "aggiungiElemento: ");
        routeMenu.aggiungiElemento(nuovaElementi, categoria, allergenici)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiSuccess>() {
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
                        Log.i(TAG, "onError: " + e);
                        callback.onFailure(e);
                    }
                });
    }


}
