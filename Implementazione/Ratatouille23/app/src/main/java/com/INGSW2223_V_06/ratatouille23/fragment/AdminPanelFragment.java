package com.INGSW2223_V_06.ratatouille23.fragment;

import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.tipoUtenteStringa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.model.TipoUtente;
import com.INGSW2223_V_06.ratatouille23.presenter.AdminPanelPresenter;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils;
import com.google.android.material.navigation.NavigationView;


public class AdminPanelFragment extends Fragment {
    private final String TAG = "AdminPanelFragment";
    private NavigationView navigationView;
    private Button buttonLogout;
    private CardView cardViewNotifica;
    private CardView cardViewGestioneMenu;
    private CardView cardViewCreaAvviso;
    private CardView cardViewAggiungiPersonale;

    private TextView pannelloInfo;

    public AdminPanelFragment() {
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
        cardViewAggiungiPersonale = view.findViewById(R.id.cardViewAggiungiPersonale);
        cardViewNotifica = view.findViewById(R.id.cardNotifica);
        pannelloInfo = view.findViewById(R.id.pannelloInfo);
        if (tipoUtenteStringa(requireContext()).equals(TipoUtente.Supervisore.toString())) {
            cardViewAggiungiPersonale.setVisibility(View.GONE);
            pannelloInfo.setText("Pannello Supervisore");
        }

        cardViewCreaAvviso = view.findViewById(R.id.cardViewCreaAvviso);
        cardViewGestioneMenu = view.findViewById(R.id.cardViewGestioneMenu);
        NavMenuUtils.attivaMenuPerFragment(AdminPanelFragment.this);
        new AdminPanelPresenter(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_panel, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: " + TAG);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        onViewCreated(requireView(), null);
    }


    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public CardView getCardViewNotifica() {
        return cardViewNotifica;
    }

    public CardView getCardViewGestioneMenu() {
        return cardViewGestioneMenu;
    }

    public CardView getCardViewCreaAvviso() {
        return cardViewCreaAvviso;
    }

    public CardView getCardViewAggiungiPersonale() {
        return cardViewAggiungiPersonale;
    }

    @Override
    public String toString() {
        return "AdminPanelFragment";
    }
}