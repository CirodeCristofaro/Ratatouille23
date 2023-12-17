package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.patternEmailVerifica;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.regexPassword;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreazioneUtenzaFragment;
import com.INGSW2223_V_06.ratatouille23.model.TipoUtente;
import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.UtenteDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

public class CreazioneUtenzaPresenter {
    private final String TAG = "CreazioneUtenzaPresenter";
    private CreazioneUtenzaFragment creazioneUtenzaFragment;
    private TextInputEditText nomePersonaleText;
    private TextInputEditText cognomePersonaleText;
    private TextInputEditText emailPersonaleText;
    private TextInputEditText passwordPersonaleText;
    private Spinner spinner;
    private Utente utente;
    private MaterialButton creaButton;
    private ImageButton arrowBack;

    private NavigationView navigationView;
    private Button buttonLogout;
    private CircularProgressIndicator loadingCirculatorCreazioneUtenza;
    private TextView textLoadingCirculatorCreazioneUtenza;
    private static UtenteDao utenteDao;


    public CreazioneUtenzaPresenter(CreazioneUtenzaFragment creazioneUtenzaFragment) {
        this.creazioneUtenzaFragment = creazioneUtenzaFragment;
        inizializza();

    }

    private void inizializza() {
        utente = new Utente();
        Log.i(TAG, "inizializza: ");
        nomePersonaleText = creazioneUtenzaFragment.getNomePersonale();
        cognomePersonaleText = creazioneUtenzaFragment.getCognomePersonale();
        emailPersonaleText = creazioneUtenzaFragment.getEmailPersonale();
        passwordPersonaleText = creazioneUtenzaFragment.getPasswordPersonale();
        creaButton = creazioneUtenzaFragment.getCreaButton();
        loadingCirculatorCreazioneUtenza =
                creazioneUtenzaFragment.getLoadingCirculatorCreazioneUtenza();
        navigationView = creazioneUtenzaFragment.getNavigationView();
        buttonLogout = creazioneUtenzaFragment.getButtonLogout();
        NavigationViewAscoltoCLick(navigationView, creazioneUtenzaFragment);
        textLoadingCirculatorCreazioneUtenza =
                creazioneUtenzaFragment.getTextLoadingCirculatorCreazioneUtenza();
        LogoutButtonAsscoltoClick(buttonLogout, creazioneUtenzaFragment);
        utenteDao = new UtenteDao(creazioneUtenzaFragment.getContext());
        spinner = creazioneUtenzaFragment.getSpinner();
        arrowBack = creazioneUtenzaFragment.getArrowBack();
        arrowBack.setOnClickListener(v -> {
            if (ritornoTipoAmministratoreOSupervisoreUtente(creazioneUtenzaFragment.getContext())) {
                FragmentUtils.changeFragment(new AdminPanelFragment(), creazioneUtenzaFragment);
            }
        });
        spinnerListener();
        creaButtonUtenteListener();
    }

    private void creaButtonUtenteListener() {
        creaButton.setOnClickListener(view -> {
            String nomePersonale = nomePersonaleText.getText().toString();
            String cognomePersonale = cognomePersonaleText.getText().toString();
            String emailPersonale = emailPersonaleText.getText().toString().trim();
            String passwordPersonale = passwordPersonaleText.getText().toString();
            if (nomePersonale.isEmpty() || cognomePersonale.isEmpty() ||
                    emailPersonale.isEmpty() || passwordPersonale.isEmpty() || utente.getTipoUtente() == null ) {
                if (creazioneUtenzaFragment.getContext() != null)
                    Toast.makeText(creazioneUtenzaFragment.getContext(), "Uno o più campi non sono stati definiti", Toast.LENGTH_LONG).show();
            } else {
                if (patternEmailVerifica(emailPersonale)) {
                    if (regexPassword(passwordPersonale)) {
                       // if (utente.getTipoUtente() != null) {

                            utente.setNome(nomePersonale);
                            utente.setCognome(cognomePersonale);
                            utente.setEmail(emailPersonale);
                            utente.setPassword(passwordPersonale);
                            setButtonEnable(false);
                            setVisibleLoading(0);
                            utenteDao.creaUtente(utente, new Callback() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (creazioneUtenzaFragment.getContext() != null) {
                                        Toast.makeText(creazioneUtenzaFragment.getContext(), object.toString(), Toast.LENGTH_LONG).show();
                                        nomePersonaleText.setText(null);
                                        cognomePersonaleText.setText(null);
                                        emailPersonaleText.setText(null);
                                        passwordPersonaleText.setText(null);
                                        setButtonEnable(true);
                                        spinnerListener();
                                        setVisibleLoading(8);
                                    }
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                    if (creazioneUtenzaFragment.getContext() != null) {
                                        Toast.makeText(creazioneUtenzaFragment.getContext(), error.getMessage()
                                                , Toast.LENGTH_LONG).show();
                                        setButtonEnable(true);
                                        setVisibleLoading(8);
                                    }

                                }
                            });
                      /*  } else {
                            if (creazioneUtenzaFragment.getContext() != null)
                                Toast.makeText(creazioneUtenzaFragment.getContext(),
                                        "Selezionare un ruolo utente valido.", Toast.LENGTH_LONG).show();
                        }*/
                    } else {
                        // caratteri ecc
                        if (creazioneUtenzaFragment.getContext() != null)
                            Toast.makeText(creazioneUtenzaFragment.getContext(), "Password non" +
                                    "conforme ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (creazioneUtenzaFragment.getContext() != null)
                        Toast.makeText(creazioneUtenzaFragment.getContext(), "Formato email " +
                                "non valido", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void spinnerListener() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(creazioneUtenzaFragment.getContext(),
                R.array.tipoUtente, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i) != null && !adapterView.getItemAtPosition(i).equals("")) {
                    utente.setTipoUtente(TipoUtente.valueOf(adapterView.getItemAtPosition(i).toString().replaceAll("\\s", "")));

                } else {
                    utente.setTipoUtente(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorCreazioneUtenza.setVisibility(visibleLoading);
        textLoadingCirculatorCreazioneUtenza.setVisibility(visibleLoading);
    }

    private void setButtonEnable(boolean enable) {
        creaButton.setEnabled(enable);
        arrowBack.setEnabled(enable);
    }

}
