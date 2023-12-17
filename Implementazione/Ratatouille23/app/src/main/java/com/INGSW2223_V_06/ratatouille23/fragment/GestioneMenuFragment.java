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
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.GestioneMenuPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class GestioneMenuFragment extends Fragment {
    private final String TAG = "GestioneMenuFragment";

    private RecyclerView recyclerView;
    private ImageButton backPulsanteGesioneMenu;
    private ImageButton aggiungiCategoria;
    private TextView textGestionemenuInvisibile;
    private NavigationView navigationView;
    private Button buttonLogout;

    private CircularProgressIndicator loadingCirculatorGestioneMenu;
    private TextView textLoadingCirculatorGestioneMenu;

    public GestioneMenuFragment() {
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
        recyclerView = view.findViewById(R.id.recyclerView);
        backPulsanteGesioneMenu = view.findViewById(R.id.backPulsanteGesioneMenu);
        aggiungiCategoria = view.findViewById(R.id.plusCateogie);
        textGestionemenuInvisibile = view.findViewById(R.id.textGestionemenuInvisibile);
        loadingCirculatorGestioneMenu = view.findViewById(R.id.loadingCirculatorGestioneMenu);

        textLoadingCirculatorGestioneMenu = view.findViewById(R.id.textLoadingCirculatorGestioneMenu);
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);

        new GestioneMenuPresenter(this);

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ImageButton getBackPulsanteGesioneMenu() {
        return backPulsanteGesioneMenu;
    }

    public ImageButton getAggiungiCategoria() {
        return aggiungiCategoria;
    }

    public TextView getTextGestionemenuInvisibile() {
        return textGestionemenuInvisibile;
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

    public CircularProgressIndicator getLoadingCirculatorGestioneMenu() {
        return loadingCirculatorGestioneMenu;
    }

    public TextView getTextLoadingCirculatorGestioneMenu() {
        return textLoadingCirculatorGestioneMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestione_menu, container, false);
    }

    @Override
    public String toString() {
        return "GestioneMenuFragment";
    }
}