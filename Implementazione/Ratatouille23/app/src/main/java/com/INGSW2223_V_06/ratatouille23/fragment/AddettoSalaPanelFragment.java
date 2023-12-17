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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.AddettoSalaPanelPresenter;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class AddettoSalaPanelFragment extends Fragment {
    private final String TAG = "AddettoSalaPanelFragment";
    private NavigationView navigationView;
    private Button buttonLogout;
    private TextView textTavoliInvisibile;
    private ImageButton campanellaImageButton, aggiungiTavolo;
    private Toolbar toolbar;
    private RecyclerView recyclerViewTavoli;
    private CircularProgressIndicator loadingCirculatorHomeAddettoAllaSala;
    private TextView textLoadingCirculatorHomeAddettoAllaSala;

    public AddettoSalaPanelFragment() {
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

        toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);
        NavMenuUtils.attivaMenuPerFragment(AddettoSalaPanelFragment.this);
        campanellaImageButton = view.findViewById(R.id.notificaCampanellaButtone);
        textLoadingCirculatorHomeAddettoAllaSala =
                view.findViewById(R.id.textLoadingCirculatorHomeAddettoAllaSala);
        textTavoliInvisibile = view.findViewById(R.id.textTavoliInvisibile);
        recyclerViewTavoli = view.findViewById(R.id.recyclerViewTavoli);
        aggiungiTavolo = view.findViewById(R.id.aggiungiTavolo);
        loadingCirculatorHomeAddettoAllaSala =
                view.findViewById(R.id.loadingCirculatorHomeAddettoAllaSala);

        new AddettoSalaPanelPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addetti_sala_panel, container, false);
    }


    public TextView getTextLoadingCirculatorHomeAddettoAllaSala() {
        return textLoadingCirculatorHomeAddettoAllaSala;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }


    public ImageButton getCampanellaImageButton() {
        return campanellaImageButton;
    }


    public TextView getTextTavoliInvisibile() {
        return textTavoliInvisibile;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public RecyclerView getRecyclerViewTavoli() {
        return recyclerViewTavoli;
    }

    public ImageButton getAggiungiTavolo() {
        return aggiungiTavolo;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        onViewCreated(requireView(), null);
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String toString() {
        return "AddettoSalaPanelFragment";
    }

    public CircularProgressIndicator getLoadingCirculatorHomeAddettoAllaSala() {
        return loadingCirculatorHomeAddettoAllaSala;
    }
}