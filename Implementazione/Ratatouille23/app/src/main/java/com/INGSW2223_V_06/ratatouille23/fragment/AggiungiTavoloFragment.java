package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.AggiungiTavoloPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;


public class AggiungiTavoloFragment extends Fragment {
    private final String TAG = "AggiungiTavoloFragment";
    private TextInputEditText numeroTavolo;
    private ImageButton backPulsanteArrowAggiungiElementoDelTavolo;
    private AutoCompleteTextView elementoTavolo;
    private RecyclerView recyclerViewElementoDelTavolo;
    private NavigationView navigationView;
    private Button buttonLogout;
    private MaterialButton salvaButton;
    private CircularProgressIndicator loadingCirculatorAggiungiTavolo;

    private TextView textLoadingCirculatorAggiungiTavolo;

    public AggiungiTavoloFragment() {

        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        backPulsanteArrowAggiungiElementoDelTavolo =
                view.findViewById(R.id.backPulsanteArrowAggiungiElementoDelTavolo);
        numeroTavolo = view.findViewById(R.id.numeroTavolo);

        elementoTavolo =
                view.findViewById(R.id.autoCompleteTextViewElementoTavolo);
        recyclerViewElementoDelTavolo =
                view.findViewById(R.id.recyclerViewElementoDelTavolo);
        salvaButton = view.findViewById(R.id.buttonSalvaNuovoTavolo);


        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);
        loadingCirculatorAggiungiTavolo = view.findViewById(R.id.loadingCirculatorAggiungiTavolo);
        textLoadingCirculatorAggiungiTavolo =
                view.findViewById(R.id.textLoadingCirculatorAggiungiTavolo);
        new AggiungiTavoloPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aggiungi_tavolo, container, false);
    }

    public TextInputEditText getNumeroTavolo() {
        return numeroTavolo;
    }


    public AutoCompleteTextView getElementoTavolo() {
        return elementoTavolo;
    }

    public RecyclerView getRecyclerViewElementoDelTavolo() {
        return recyclerViewElementoDelTavolo;
    }

    public ImageButton getBackPulsanteArrowAggiungiElementoDelTavolo() {
        return backPulsanteArrowAggiungiElementoDelTavolo;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public MaterialButton getSalvaButton() {
        return salvaButton;
    }

    public CircularProgressIndicator getLoadingCirculatorAggiungiTavolo() {
        return loadingCirculatorAggiungiTavolo;
    }

    public TextView getTextLoadingCirculatorAggiungiTavolo() {
        return textLoadingCirculatorAggiungiTavolo;
    }
}