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
import com.INGSW2223_V_06.ratatouille23.model.Avviso;
import com.INGSW2223_V_06.ratatouille23.presenter.VisualizzaAvvisoPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;


public class VisualizzaAvvisoFragment extends Fragment {
    private final String TAG = "VisualizzaAvvisoFragment";
    private NavigationView navigationView;
    private Button buttonLogout;

    private MaterialButton buttonNascondi;
    private TextView oggetto;
    private TextView contentuto;
    private TextView dataAvvisoSingolo;
    private Avviso avviso;
    private ImageButton backArrow;

    private TextView mittenteText;

    public VisualizzaAvvisoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visualizza_avviso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;

        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);

        buttonNascondi = view.findViewById(R.id.nascondiMessaggio);
        oggetto = view.findViewById(R.id.oggettoAvvisoVisual);
        contentuto = view.findViewById(R.id.cotenutoTextAvviso);
        dataAvvisoSingolo = view.findViewById(R.id.dataAvvisoSingolo);
        backArrow = view.findViewById(R.id.backPulsanteArrowVisualizzaMessaggio);
        mittenteText = view.findViewById(R.id.mittente);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        avviso = (Avviso) bundle.getSerializable("avvisoDaVisualizzare");

        new VisualizzaAvvisoPresenter(this);
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public TextView getDataAvvisoSingolo() {
        return dataAvvisoSingolo;
    }

    public MaterialButton getButtonNascondi() {
        return buttonNascondi;
    }

    public TextView getOggetto() {
        return oggetto;
    }


    public TextView getContentuto() {
        return contentuto;
    }

    public Avviso getAvviso() {
        return avviso;
    }

    public ImageButton getBackArrow() {
        return backArrow;
    }

    public TextView getMittenteText() {
        return mittenteText;
    }

    public void setMittenteText(TextView mittenteText) {
        this.mittenteText = mittenteText;
    }

    @Override
    public String toString() {
        return "VisualizzaAvvisoFragment";
    }
}