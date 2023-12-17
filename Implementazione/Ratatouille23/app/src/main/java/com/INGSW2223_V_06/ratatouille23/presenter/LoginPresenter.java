package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.attivaMenuPerFragment;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.DataPrimoAccesso;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.patternEmailVerifica;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.regexPassword;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaCucinaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaSalaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.setLocalUser;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.fragment.AddettoAllaCucinaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.LoginFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.PrimoLoginFragment;
import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteResponse;
import com.INGSW2223_V_06.ratatouille23.retrofit.UtenteRichiesta;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.UtenteDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.TokenManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginPresenter {
    private final String TAG = "LoginPresenter";
    private LoginFragment loginFragment;
    private TextInputEditText email;
    private TextInputLayout emailLayout;
    private TextInputEditText password;
    private MaterialButton buttonLogin;
    private UtenteDao utenteDao;
    private CircularProgressIndicator loadingCirculatorLogin;
    private TextView textLoadingCirculatorLogin;


    public LoginPresenter(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
        inizializza();
    }

    private void inizializza() {
        utenteDao = new UtenteDao(loginFragment.getContext());
        email = loginFragment.getEmail();
        emailLayout = loginFragment.getEmailLayout();
        password = loginFragment.getPassword();
        buttonLogin = loginFragment.getLoginbutton();
        loadingCirculatorLogin = loginFragment.getLoadingCirculatorLogin();
        textLoadingCirculatorLogin = loginFragment.getTextLoadingCirculatorLogin();
        loginButtonListener();

    }

    private void loginButtonListener() {
        buttonLogin.setOnClickListener(v -> {
            if (!email.getText().toString().trim().isEmpty() || !password.getText().toString().trim().isEmpty()) {
                if (patternEmailVerifica(email.getText().toString().trim())) {
                    if (regexPassword(password.getText().toString().trim())) {
                        setVisibleLoading(0);
                        buttonLogin.setEnabled(false);
                        String passwordDaInviare = password.getText().toString().trim();
                        String emailDaInviare = email.getText().toString().trim();
                        UtenteRichiesta utenteRichiesta= new UtenteRichiesta(emailDaInviare,
                                passwordDaInviare);
                        utenteDao.login(utenteRichiesta, new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                UtenteResponse utenteResponse= (UtenteResponse) object;
                                Log.i(TAG, "onSuccess: " + object.toString());
                                setVisibleLoading(8);
                                buttonLogin.setEnabled(true);
                                TokenManager.getInstance(loginFragment.getContext())
                                        .setToken(utenteResponse.getAccessToken());
                                utenteDao.infoUtente(emailDaInviare, new Callback() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Utente utenteObj = (Utente) object;
                                        setLocalUser(loginFragment.getContext(),utenteObj);
                                        if (DataPrimoAccesso(loginFragment.getContext()) == null) {
                                            Fragment primoLoginFragment = new PrimoLoginFragment();
                                            Bundle bundle = new Bundle();
                                            Utente utente= new Utente();
                                            utente.setEmail(emailDaInviare);
                                            utente.setPassword(passwordDaInviare);
                                            bundle.putSerializable("utente", utente);
                                            primoLoginFragment.setArguments(bundle);
                                            FragmentUtils.changeFragment(primoLoginFragment, loginFragment);
                                        } else {
                                             attivaMenuPerFragment(loginFragment);
                                            if (ritornoTipoAmministratoreOSupervisoreUtente(loginFragment.requireContext())) {
                                                FragmentUtils.changeFragment(new AdminPanelFragment(),
                                                        loginFragment);
                                            } else if (ritornoTipoAddettoAllaSalaUtente(loginFragment.getContext())) {
                                                FragmentUtils.changeFragment(new AddettoSalaPanelFragment(),
                                                        loginFragment);
                                            } else if (ritornoTipoAddettoAllaCucinaUtente(loginFragment.getContext())) {
                                                FragmentUtils.changeFragment(new AddettoAllaCucinaPanelFragment(),
                                                        loginFragment);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable error) {
                                        Log.e(TAG, "onFailure: ",error );
                                        if (loginFragment.getContext() != null)
                                            Toast.makeText(loginFragment.getContext(), error.getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                    }
                                });


                            }

                            @Override
                            public void onFailure(Throwable error) {
                                setVisibleLoading(8);
                                buttonLogin.setEnabled(true);
                                if (loginFragment.getContext() != null)
                                    Toast.makeText(loginFragment.getContext(), error.getMessage(),
                                            Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        if (password.getText().toString().trim().isEmpty()) {
                            if (loginFragment.getContext() != null) {
                                Toast.makeText(loginFragment.getContext(), "La password è vuota. Si prega di inserire una password valida",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (loginFragment.getContext() != null)
                                Toast.makeText(loginFragment.getContext(), "Password non " +
                                        "conforme", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (loginFragment.getContext() != null)
                        Toast.makeText(loginFragment.getContext(), "Formato email non " +
                                "valida", Toast.LENGTH_LONG).show();
                }
            } else {
                if (loginFragment.getContext() != null)
                    Toast.makeText(loginFragment.getContext(), "Uno o più campi  non sono stati definiti",
                            Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorLogin.setVisibility(visibleLoading);
        textLoadingCirculatorLogin.setVisibility(visibleLoading);
    }
}



