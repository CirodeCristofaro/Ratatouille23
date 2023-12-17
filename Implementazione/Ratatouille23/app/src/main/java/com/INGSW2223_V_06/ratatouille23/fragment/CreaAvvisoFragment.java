package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.CreaAvvisoPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;


public class CreaAvvisoFragment extends Fragment {
    private final String TAG = "CreaAvvisoFragment";
    private TextView dataAvvisoCreazione;
    private NavigationView navigationView;
    private Button buttonLogout;
    private ImageButton buttonBackImage;
    private MaterialButton buttonInvia;
    private TextInputEditText oggetto;
    private TextInputEditText messaggio;
    private CircularProgressIndicator loadingCirculatorCreazioneAvviso;
    private TextView textLoadingCirculatorCreazioneAvviso;

    public CreaAvvisoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);

        buttonBackImage = view.findViewById(R.id.backPulsanteArrow);
        oggetto = view.findViewById(R.id.oggetto);
        messaggio = view.findViewById(R.id.messaggio);
        buttonInvia = view.findViewById(R.id.buttonInviaMessaggio);
        dataAvvisoCreazione = view.findViewById(R.id.dataAvvisoCreazione);
        loadingCirculatorCreazioneAvviso = view.findViewById(R.id.loadingCirculatorCreazioneAvviso);
        textLoadingCirculatorCreazioneAvviso =
                view.findViewById(R.id.textLoadingCirculatorCreazioneAvviso);
        new CreaAvvisoPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_crea_avviso, container, false);
    }

    public TextView getDataAvvisoCreazione() {
        return dataAvvisoCreazione;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public ImageButton getButtonBackImage() {
        return buttonBackImage;
    }

    public MaterialButton getButtonInvia() {
        return buttonInvia;
    }


    public TextInputEditText getOggetto() {
        return oggetto;
    }


    public TextView getTextLoadingCirculatorCreazioneAvviso() {
        return textLoadingCirculatorCreazioneAvviso;
    }

    public TextInputEditText getMessaggio() {
        return messaggio;
    }

    public CircularProgressIndicator getLoadingCirculatorCreazioneAvviso() {
        return loadingCirculatorCreazioneAvviso;
    }

    @Override
    public String toString() {
        return "CreaAvvisoFragment";
    }
}