package com.INGSW2223_V_06.ratatouille23.presenter;


import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.attivaMenuPerFragment;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.regexPassword;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaCucinaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaSalaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.setLocalUser;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.INGSW2223_V_06.ratatouille23.fragment.AddettoAllaCucinaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.PrimoLoginFragment;
import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.UtenteDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

public class PrimoLoginPresenter {
    private final String TAG = "PrimoLoginPresenter";
    private TextInputEditText password;
    private TextInputEditText repassword;
    private MaterialButton cambiabutton;
    private PrimoLoginFragment primoLoginFragment;
    private Utente utenteBundle;
    private UtenteDao utenteDao;
    private CircularProgressIndicator loadingCirculatorCambiaPasswordPrimoLogin;
    private TextView textLoadingCirculatoCambiaPasswordPrimoLogin;

    public PrimoLoginPresenter(PrimoLoginFragment primoLoginFragment) {
        this.primoLoginFragment = primoLoginFragment;
        inizializza();
    }

    private void inizializza() {
        password = primoLoginFragment.getPassword();
        repassword = primoLoginFragment.getRepassword();
        cambiabutton = primoLoginFragment.getCambiabutton();
        utenteBundle = primoLoginFragment.getUtenteBundle();
        //   Log.d(TAG, utenteBundle.toString());
        utenteDao = new UtenteDao(primoLoginFragment.getContext());
        loadingCirculatorCambiaPasswordPrimoLogin =
                primoLoginFragment.getLoadingCirculatorCambiaPasswordPrimoLogin();
        textLoadingCirculatoCambiaPasswordPrimoLogin =
                primoLoginFragment.getTextLoadingCirculatoCambiaPasswordPrimoLogin();
        cambiabuttonListener();
    }

    private void cambiabuttonListener() {
        cambiabutton.setOnClickListener(v -> {
            if (!password.getText().toString().isEmpty()
                    && !repassword.getText().toString().isEmpty()) {
                if (regexPassword(password.getText().toString())
                        && regexPassword(repassword.getText().toString())) {
                    if (!password.getText().toString().equals(utenteBundle.getPassword())) {
                        if (password.getText().toString().equals(repassword.getText().toString())) {
                            Log.i(TAG, "cambiabuttonListener: " + password.getText().toString().trim());
                            setVisibleLoading(0);
                            cambiabutton.setEnabled(false);
                            utenteDao.reImpostaPassowrd(password.getText().toString().trim(),
                                    new Callback() {
                                        @Override
                                        public void onSuccess(Object object) {
                                            cambiabutton.setEnabled(true);
                                            setVisibleLoading(8);
                                            setLocalUser(primoLoginFragment.getContext(), (Utente) object);
                                            attivaMenuPerFragment(primoLoginFragment);
                                            if (ritornoTipoAmministratoreOSupervisoreUtente(primoLoginFragment.getContext())) {
                                                FragmentUtils.changeFragment(new AdminPanelFragment(), primoLoginFragment);
                                            } else if (ritornoTipoAddettoAllaSalaUtente(primoLoginFragment.getContext())) {
                                                FragmentUtils.changeFragment(new AddettoSalaPanelFragment(),
                                                        primoLoginFragment);
                                            } else if (ritornoTipoAddettoAllaCucinaUtente(primoLoginFragment.getContext())) {
                                                FragmentUtils.changeFragment(new AddettoAllaCucinaPanelFragment(),
                                                        primoLoginFragment);
                                            }

                                        }

                                        @Override
                                        public void onFailure(Throwable error) {
                                            setVisibleLoading(8);
                                            cambiabutton.setEnabled(true);
                                            if (primoLoginFragment.getContext() != null) {
                                                Toast.makeText(primoLoginFragment.getContext(), "Errore nel cambiare la password. Si è verificato un problema durante l'operazione",
                                                        Toast.LENGTH_LONG).show();
                                                Log.e(TAG, error.getMessage() + " " + error.toString());
                                            }
                                        }
                                    });
                        } else {
                            if (primoLoginFragment.getContext() != null)
                                Toast.makeText(primoLoginFragment.getContext(), "Le password inserite non corrispondono.", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        if (primoLoginFragment.getContext() != null)
                            Toast.makeText(primoLoginFragment.getContext(), "La password deve essere diversa da quella inserita dall'Amministratore.",
                                    Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (primoLoginFragment.getContext() != null)
                        Toast.makeText(primoLoginFragment.getContext(), "La password non conforme allo standard ",
                                Toast.LENGTH_LONG).show();
                }
            } else {
                if (primoLoginFragment.getContext() != null)
                    Toast.makeText(primoLoginFragment.getContext(), "Uno o più campi non sono stati definiti",
                            Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorCambiaPasswordPrimoLogin.setVisibility(visibleLoading);
        textLoadingCirculatoCambiaPasswordPrimoLogin.setVisibility(visibleLoading);
    }
}
