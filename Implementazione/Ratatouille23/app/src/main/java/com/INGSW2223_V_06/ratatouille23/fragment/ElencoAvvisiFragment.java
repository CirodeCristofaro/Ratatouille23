package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.INGSW2223_V_06.ratatouille23.presenter.ElencoAvvisiPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class ElencoAvvisiFragment extends Fragment {
    private final String TAG = "ElencoAvvisiFragment";
    private RecyclerView recyclerView;

    private NavigationView navigationView;
    private Button buttonLogout;

    private TextView textViewInvisibile;
    private ImageButton BackPulsanteVisualizzaElenco;

    private CircularProgressIndicator loadingCirculatorElencoAvvisi;
    private TextView textLoadingCirculatorElencoAvvisi;

    public ElencoAvvisiFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elenco_avvisi_rv, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        recyclerView = view.findViewById(R.id.rvAvvisi);

        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);

        loadingCirculatorElencoAvvisi = view.findViewById(R.id.loadingCirculatorElencoAvvisi);
        BackPulsanteVisualizzaElenco = view.findViewById(R.id.backPulsanteVisualizzaElenco);
        textViewInvisibile = view.findViewById(R.id.textViewInvisibile);
        textLoadingCirculatorElencoAvvisi = view.findViewById(R.id.textLoadingCirculatorElencoAvvisi);

        new ElencoAvvisiPresenter(this);
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

    public TextView getTextViewInvisibile() {
        return textViewInvisibile;
    }

    public ImageButton getBackPulsanteVisualizzaElenco() {
        return BackPulsanteVisualizzaElenco;
    }

    public CircularProgressIndicator getLoadingCirculatorElencoAvvisi() {
        return loadingCirculatorElencoAvvisi;
    }

    public TextView getTextLoadingCirculatorElencoAvvisi() {
        return textLoadingCirculatorElencoAvvisi;
    }

    @Override
    public void onResume() {
        Log.i("on", "onResume");
        super.onResume();
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        textViewInvisibile = this.getActivity().findViewById(R.id.textViewInvisibile);
        new ElencoAvvisiPresenter(this);
    }

    @Override
    public String toString() {
        return "ElencoAvvisiFragment";
    }
}