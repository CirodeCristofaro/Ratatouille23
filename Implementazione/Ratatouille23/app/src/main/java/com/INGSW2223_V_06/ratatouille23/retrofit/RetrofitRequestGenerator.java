package com.INGSW2223_V_06.ratatouille23.retrofit;

import android.content.Context;
import android.util.Log;

import com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.TokenManager;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitRequestGenerator {
    public static String CURRENT_URL = null;
    public static Retrofit retrofit = null;
    private static final String IP = "http://13.51.234.40:8080";
    private static final String TAG = "RetrofitRequestGenerator";
    private static TokenManager tokenManager;

    public RetrofitRequestGenerator() {
    }


    public static Retrofit retrofitInstance(ChiamateApi api, Context context) throws IllegalArgumentException {
        tokenManager = TokenManager.getInstance(context);
        if (api == null || context == null) {
            throw new IllegalArgumentException();
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder newRequestBuilder = request.newBuilder()
                            .addHeader("Authorization", "Bearer " + tokenManager.getToken());
                    return chain.proceed(newRequestBuilder.build());
                })
                .addInterceptor(new ErrorHandlingInterceptor(context))
                .build();

        String URL_BODY = getApiUrl(api);
        String NEW_URL = IP + URL_BODY;
        if (CURRENT_URL == null || !CURRENT_URL.equals(NEW_URL)) {
            CURRENT_URL = NEW_URL;
            retrofit = new Retrofit.Builder().baseUrl(NEW_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(client)
                    .build();
            Log.d(TAG, "retrofitInstance: " + getRetrofit() + " token: " + tokenManager.getToken());
        }

        return retrofit;

    }

    public static String getRetrofit() {
        return retrofit.baseUrl().toString();
    }

    public static String getApiUrl(ChiamateApi api) {
        switch (api) {
            case utente:
                return "/api/account/";
            case avviso:
                return "/api/avviso/";
            case menu:
                return "/api/menu/";
            case tavolo:
                return "/api/tavolo/";
            default:
                return "/undefinited/";
        }

    }
}
