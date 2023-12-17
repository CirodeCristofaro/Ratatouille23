package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.VisualizzaAvvisoFragment;
import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.model.TipoVisualizzazione;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.AvvisoDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VisualizzaAvvisoPresenter {
    private final String TAG = "VisualizzaAvvisoPresenter";
    private VisualizzaAvvisoFragment visualizzaAvvisoFragment;
    private NavigationView navigationView;
    private Button buttonLogout;
    private MaterialButton buttonNascondi;
    private TextView oggetto;
    private TextView contentuto;
    private TextView dataAvvisoSingolo;
    private Avviso avviso;
    private ImageButton backArrow;
    private AvvisoDao avvisoDao;

    private TextView mittenteText;

    public VisualizzaAvvisoPresenter(VisualizzaAvvisoFragment visualizzaAvvisoFragment) {
        this.visualizzaAvvisoFragment = visualizzaAvvisoFragment;
        inizializza();
    }

    private void inizializza() {
        Log.i(TAG, "inizializza: ");
        buttonNascondi = visualizzaAvvisoFragment.getButtonNascondi();
        oggetto = visualizzaAvvisoFragment.getOggetto();

        contentuto = visualizzaAvvisoFragment.getContentuto();
        dataAvvisoSingolo = visualizzaAvvisoFragment.getDataAvvisoSingolo();
        mittenteText = visualizzaAvvisoFragment.getMittenteText();

        navigationView = visualizzaAvvisoFragment.getNavigationView();
        buttonLogout = visualizzaAvvisoFragment.getButtonLogout();
        backArrow = visualizzaAvvisoFragment.getBackArrow();
        NavigationViewAscoltoCLick(navigationView, visualizzaAvvisoFragment);
        LogoutButtonAsscoltoClick(buttonLogout, visualizzaAvvisoFragment);
        avviso = visualizzaAvvisoFragment.getAvviso();

        avvisoDao = new AvvisoDao(visualizzaAvvisoFragment.getContext());

        oggetto.setText(String.format("Oggetto: %s", avviso.getOggetto()));
        contentuto.setText(avviso.getContenuto());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ITALY);

        try {
            Date dataParse = formatter.parse(avviso.getData());
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);
            String dataFormattata = formatter.format(dataParse);
            dataAvvisoSingolo.setText(String.format("Data: %s", dataFormattata));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        mittenteText.setText(String.format("Mittente: %s",
                avviso.getUtenteCreaAvviso().getNome() + " " + avviso.getUtenteCreaAvviso().getCognome()));
        buttonNascondiListener();

        backArrow.setOnClickListener(view1 -> {
            FragmentUtils.changeFragment(new ElencoAvvisiFragment(), visualizzaAvvisoFragment);
        });
    }

    private void buttonNascondiListener() {
        buttonNascondi.setOnClickListener(view -> {
            avvisoDao.cambiaStatoAvviso(avviso.getIdAvviso(),
                    TipoVisualizzazione.nascosto, new Callback() {
                        @Override
                        public void onSuccess(Object object) {
                            FragmentUtils.changeFragment(new ElencoAvvisiFragment(),
                                    visualizzaAvvisoFragment);
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            if (visualizzaAvvisoFragment.getContext() != null)
                                Toast.makeText(visualizzaAvvisoFragment.getContext(), error.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
