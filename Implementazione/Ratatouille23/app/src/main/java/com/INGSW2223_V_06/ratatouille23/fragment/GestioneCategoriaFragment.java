package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.GestioneCategoriaPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class GestioneCategoriaFragment extends Fragment {
    private final String TAG = "GestioneCategoriaFragment";
    private RecyclerView recyclerView;

    private ImageButton backArrow;
    private ImageButton aggiungiElemento;
    private TextView sottotitoloGestioneCategoria;
    private TextView textGestioneCategoriaInvisibile;
    private NavigationView navigationView;
    private Button buttonLogout;
    private String stringaCategoria;

    private Spinner spinnerOrdinamento;
    private CircularProgressIndicator loadingCirculatorGestioneCategoria;
    private TextView textLoadingCirculatorGestioneCategoria;

    public GestioneCategoriaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        Log.i(TAG, "onViewCreated: ");
        recyclerView = view.findViewById(R.id.recyclerViewElemento);
        backArrow = view.findViewById(R.id.backPulsanteGestioneCategoria);
        aggiungiElemento = view.findViewById(R.id.plusPiatto);
        textGestioneCategoriaInvisibile = view.findViewById(R.id.textGestioneCategoriaInvisibile);
        sottotitoloGestioneCategoria = view.findViewById(R.id.sottotitoloGestioneCategoria);
        spinnerOrdinamento = view.findViewById(R.id.spinnerOrdinamento);
        loadingCirculatorGestioneCategoria =
                view.findViewById(R.id.loadingCirculatorGestioneCategoria);
        textLoadingCirculatorGestioneCategoria =
                view.findViewById(R.id.textLoadingCirculatorGestioneCategoria);
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        stringaCategoria = bundle.getString("categoria");
        sottotitoloGestioneCategoria.setText(String.format("Categoria: %s", stringaCategoria.toUpperCase()));


        new GestioneCategoriaPresenter(this, bundle.getString("categoria"));


    }

    public TextView getTextGestioneCategoriaInvisibile() {
        return textGestioneCategoriaInvisibile;
    }

    public ImageButton getBackArrow() {
        return backArrow;
    }

    public ImageButton getAggiungiElemento() {
        return aggiungiElemento;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestione_categoria, container, false);
    }

    public String getStringaCategoria() {
        return stringaCategoria;
    }

    public Spinner getSpinnerOrdinamento() {
        return spinnerOrdinamento;
    }

    public CircularProgressIndicator getLoadingCirculatorGestioneCategoria() {
        return loadingCirculatorGestioneCategoria;
    }

    public TextView getTextLoadingCirculatorGestioneCategoria() {
        return textLoadingCirculatorGestioneCategoria;
    }

    @Override
    public String toString() {
        return "GestioneCategoriaFragment";
    }

}