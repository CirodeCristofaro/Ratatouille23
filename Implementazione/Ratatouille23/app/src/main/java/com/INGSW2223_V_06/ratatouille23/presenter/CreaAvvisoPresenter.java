package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaAvvisoFragment;
import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.AvvisoDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreaAvvisoPresenter {
    private final String TAG = "CreaAvvisoPresenter";
    private CreaAvvisoFragment creaAvvisoFragment;
    private NavigationView navigationView;
    private Button buttonLogout;
    private ImageButton buttonBackImage;
    private MaterialButton buttonInvia;
    private TextInputEditText oggetto;
    private TextInputEditText messaggio;
    private TextView dataAvvisoCreazione;
    private AvvisoDao avvisoDao;
    private CircularProgressIndicator loadingCirculatorCreazioneAvviso;
    private TextView textLoadingCirculatorCreazioneAvviso;

    public CreaAvvisoPresenter(CreaAvvisoFragment creaAvvisoFragment) {
        this.creaAvvisoFragment = creaAvvisoFragment;
        inizializza();
    }

    private void inizializza() {
        navigationView = creaAvvisoFragment.getNavigationView();
        buttonLogout = creaAvvisoFragment.getButtonLogout();
        buttonBackImage = creaAvvisoFragment.getButtonBackImage();
        oggetto = creaAvvisoFragment.getOggetto();
        dataAvvisoCreazione = creaAvvisoFragment.getDataAvvisoCreazione();
        messaggio = creaAvvisoFragment.getMessaggio();
        buttonInvia = creaAvvisoFragment.getButtonInvia();
        avvisoDao = new AvvisoDao(creaAvvisoFragment.getContext());
        loadingCirculatorCreazioneAvviso = creaAvvisoFragment.getLoadingCirculatorCreazioneAvviso();
        Date data = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        String dataFormattata = formatter.format(data);
        dataAvvisoCreazione.setText(String.format("Data: %s", dataFormattata));
        textLoadingCirculatorCreazioneAvviso =
                creaAvvisoFragment.getTextLoadingCirculatorCreazioneAvviso();
        NavigationViewAscoltoCLick(navigationView, creaAvvisoFragment);
        LogoutButtonAsscoltoClick(buttonLogout, creaAvvisoFragment);

        inviaAvviso();

        buttonBackImageListener();
    }

    private void buttonBackImageListener() {
        buttonBackImage.setOnClickListener(view1 -> {
            if (ritornoTipoAmministratoreOSupervisoreUtente(creaAvvisoFragment.getContext())) {
                FragmentUtils.changeFragment(new AdminPanelFragment(), creaAvvisoFragment);
            }
        });
    }

    private void inviaAvviso() {
        buttonInvia.setOnClickListener(view -> {
            Log.d(TAG, "pulsante cliccato");
            String oggettoAvviso = oggetto.getText().toString().trim();
            String messaggioAvviso=messaggio.getText().toString().trim();
            if (!oggettoAvviso.isEmpty() && !messaggioAvviso.isEmpty()) {
                setVisibleLoading(0);
                setButtonEnable(false);

                Avviso avviso = new Avviso(oggetto.getText().toString(), messaggio.getText().toString());
                Date data = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ITALY);
                String dataFormattata = formatter.format(data);

                avviso.setData(dataFormattata);
                avvisoDao.creaAvviso(avviso, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        if (creaAvvisoFragment.getContext() != null) {
                            setVisibleLoading(8);

                            setButtonEnable(true);

                            Toast.makeText(creaAvvisoFragment.getContext(), object.toString(),
                                    Toast.LENGTH_SHORT).show();
                            oggetto.setText(null);
                            messaggio.setText(null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.e(TAG, "onFailure: ", error);
                        if (creaAvvisoFragment.getContext() != null) {
                            Toast.makeText(creaAvvisoFragment.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            setVisibleLoading(8);
                            setButtonEnable(true);
                        }
                    }
                });
            } else {
                if (creaAvvisoFragment.getContext() != null)
                    Toast.makeText(creaAvvisoFragment.getContext(), "Uno o più campi dell avviso non sono stati definiti", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorCreazioneAvviso.setVisibility(visibleLoading);
        textLoadingCirculatorCreazioneAvviso.setVisibility(visibleLoading);
    }


    private void setButtonEnable(boolean enable) {

        buttonInvia.setEnabled(enable);
        buttonBackImage.setEnabled(enable);
        oggetto.setEnabled(enable);
        messaggio.setEnabled(enable);
    }


}
