package com.INGSW2223_V_06.ratatouille23.fragment;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils;
import com.google.android.material.navigation.NavigationView;

public class AddettoAllaCucinaPanelFragment extends Fragment {

    private final String TAG = "AddettoAllaCucinaPanelFragment";
    private NavigationView navigationView;
    private Button buttonLogout;
    private Toolbar toolbar;


    public AddettoAllaCucinaPanelFragment() {
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
        navigationView = view.findViewById(R.id.nav);
        buttonLogout = view.findViewById(R.id.logout);
        NavMenuUtils.attivaMenuPerFragment(AddettoAllaCucinaPanelFragment.this);
        NavigationViewAscoltoCLick(navigationView, AddettoAllaCucinaPanelFragment.this);
        LogoutButtonAsscoltoClick(buttonLogout, AddettoAllaCucinaPanelFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addetto_alla_cucina_panel, container, false);
    }

    @Override
    public String toString() {
        return "AddettoAllaCucinaPanelFragment";
    }
}