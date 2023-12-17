package com.INGSW2223_V_06.ratatouille23.retrofit;

import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.deleteLocalUser;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.messageTokenScaduto;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.LoginFragment;
import com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.TokenManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ErrorHandlingInterceptor implements Interceptor {


    private static final String TAG = "ErrorHandlingInterceptor";

    private Context context;

    public ErrorHandlingInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        try {
            Response response = chain.proceed(request);
            Log.i(TAG, response.toString());
            if (response.code() == 401) {
                if (context instanceof MainActivity &&
                        ((MainActivity) context).fragmentAttule.equals("LoginFragment")) {
                    TokenManager.deleteToken();
                    JSONObject jObjError = new JSONObject(response.body().string());
                    String errorMessage = jObjError.getString("message");
                    if (!errorMessage.isEmpty()) {
                        Log.i(TAG, "intercept: body  " + errorMessage);
                        throw new RuntimeException(errorMessage);
                    }
                } else {
                    messageTokenScaduto(context);
                    deleteLocalUser(context);
                    TokenManager.deleteToken();
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayoutActivityMain, new LoginFragment()).commit();
                }
            }
            if (response.code() >= 400) {
                assert response.body() != null;
                JSONObject jObjError = new JSONObject(response.body().string());
                String errorMessage = jObjError.getString("message");
                if (!errorMessage.isEmpty()) {
                          Log.i(TAG, "intercept: body  " + errorMessage);
                    throw new RuntimeException(errorMessage);
                }
            }
            return response;
        } catch (ConnectException | SocketTimeoutException connectException) {
            throw new RuntimeException("Server al momento non disponibile");
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
