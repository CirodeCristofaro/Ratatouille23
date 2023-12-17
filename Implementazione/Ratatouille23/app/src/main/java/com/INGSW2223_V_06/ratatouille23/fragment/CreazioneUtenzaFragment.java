package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.CreazioneUtenzaPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;


public class CreazioneUtenzaFragment extends Fragment {
    private final String TAG = "CreazioneUtenzaFragment";

    private TextInputEditText nomePersonale;
    private TextInputEditText cognomePersonale;
    private TextInputEditText emailPersonale;
    private TextInputEditText passwordPersonale;
    private Spinner spinner;
    private MaterialButton creaButton;
    private ImageButton arrowBack;
    private NavigationView navigationView;
    private Button buttonLogout;
    private CircularProgressIndicator loadingCirculatorCreazioneUtenza;
    private TextView textLoadingCirculatorCreazioneUtenza;

    public CreazioneUtenzaFragment() {
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
        nomePersonale = view.findViewById(R.id.nomeCreazionePersonale);
        cognomePersonale = view.findViewById(R.id.cognomeCreazionePersonale);
        emailPersonale = view.findViewById(R.id.emailCreazionePersonale);
        passwordPersonale = view.findViewById(R.id.passwordCreazionePersonale);
        loadingCirculatorCreazioneUtenza = view.findViewById(R.id.loadingCirculatorCreazioneUtenza);
        textLoadingCirculatorCreazioneUtenza = view.findViewById(R.id.textLoadingCirculatorCreazioneUtenza);
        spinner = view.findViewById(R.id.spinnerRuoli);
        creaButton = view.findViewById(R.id.buttonCreaPersonale);
        arrowBack = view.findViewById(R.id.backPulsanteArrowCreazionePersonale);


        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);
        new CreazioneUtenzaPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_creazione_utenza, container, false);
    }

    public TextInputEditText getNomePersonale() {
        return nomePersonale;
    }

    public TextInputEditText getCognomePersonale() {
        return cognomePersonale;
    }

    public TextInputEditText getEmailPersonale() {
        return emailPersonale;
    }

    public TextInputEditText getPasswordPersonale() {
        return passwordPersonale;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public ImageButton getArrowBack() {
        return arrowBack;
    }

    public MaterialButton getCreaButton() {
        return creaButton;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public CircularProgressIndicator getLoadingCirculatorCreazioneUtenza() {
        return loadingCirculatorCreazioneUtenza;
    }

    public TextView getTextLoadingCirculatorCreazioneUtenza() {
        return textLoadingCirculatorCreazioneUtenza;
    }

    @Override
    public String toString() {
        return "CreazioneUtenzaFragment";
    }
}