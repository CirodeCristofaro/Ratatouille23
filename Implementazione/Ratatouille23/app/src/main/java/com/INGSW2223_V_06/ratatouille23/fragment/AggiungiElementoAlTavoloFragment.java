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
import com.INGSW2223_V_06.ratatouille23.presenter.AggiungiElementoAlTavoloPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class AggiungiElementoAlTavoloFragment extends Fragment {
    private final String TAG = "AggiungiElementoAlTavoloFragment";
    private TextView gestioneTavoloN;
    private ImageButton backPulsanteArrowAggiungiElementoAlTavolo;

    private AutoCompleteTextView autoCompleteTextViewElementoAlTavolo;
    private RecyclerView recyclerViewElementoAlTavolo;

    private NavigationView navigationView;
    private Button buttonLogout;

    private MaterialButton buttonAggiornaTavolo;
    private String idTavoloBundle;
    private CircularProgressIndicator loadingCirculatorAggiungiElementoAlTavolo;
    private TextView textLoadingCirculatorAggiungiElementoAlTavolo;

    public AggiungiElementoAlTavoloFragment() {
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
        return inflater.inflate(R.layout.fragment_aggiungi_elemento_al_tavolo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        gestioneTavoloN = view.findViewById(R.id.gestioneTavoloN);


        autoCompleteTextViewElementoAlTavolo =
                view.findViewById(R.id.autoCompleteTextViewElementoAlTavolo);
        recyclerViewElementoAlTavolo =
                view.findViewById(R.id.recyclerViewElementiAlTavolo);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        idTavoloBundle = bundle.getString("idTavolo");
        textLoadingCirculatorAggiungiElementoAlTavolo =
                view.findViewById(R.id.textLoadingCirculatorAggiungiElementoAlTavolo);
        backPulsanteArrowAggiungiElementoAlTavolo =
                view.findViewById(R.id.backPulsanteArrowAggiungiElementoAlTavolo);
        loadingCirculatorAggiungiElementoAlTavolo =
                view.findViewById(R.id.loadingCirculatorAggiungiElementoAlTavolo);
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);
        buttonAggiornaTavolo = view.findViewById(R.id.buttonAggiornaTavolo);
        new AggiungiElementoAlTavoloPresenter(this);
    }

    public ImageButton getBackPulsanteArrowAggiungiElementoAlTavolo() {
        return backPulsanteArrowAggiungiElementoAlTavolo;
    }

    public MaterialButton getButtonAggiornaTavolo() {
        return buttonAggiornaTavolo;
    }

    public String getIdTavoloBundle() {
        return idTavoloBundle;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }


    public TextView getGestioneTavoloN() {
        return gestioneTavoloN;
    }


    public AutoCompleteTextView getAutoCompleteTextViewElementoAlTavolo() {
        return autoCompleteTextViewElementoAlTavolo;
    }

    public RecyclerView getRecyclerViewElementoAlTavolo() {
        return recyclerViewElementoAlTavolo;
    }

    public CircularProgressIndicator getLoadingCirculatorAggiungiElementoAlTavolo() {
        return loadingCirculatorAggiungiElementoAlTavolo;
    }

    public TextView getTextLoadingCirculatorAggiungiElementoAlTavolo() {
        return textLoadingCirculatorAggiungiElementoAlTavolo;
    }
}