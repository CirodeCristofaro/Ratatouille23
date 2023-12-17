package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.util.Log;
import android.widget.Button;

import androidx.cardview.widget.CardView;

import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaAvvisoFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreazioneUtenzaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.navigation.NavigationView;

public class AdminPanelPresenter {
    private final String TAG = "AdminPanelPresenter";
    private AdminPanelFragment adminPanelFragment;
    private NavigationView navigationView;
    private Button buttonLogout;
    private CardView cardViewNotifica;
    private CardView cardViewGestioneMenu;
    private CardView cardViewCreaAvviso;
    private CardView cardViewAggiungiPersonale;

    public AdminPanelPresenter(AdminPanelFragment adminPanelFragment) {
        this.adminPanelFragment = adminPanelFragment;
        Log.i(TAG, "AdminPanelPresenter: ");
        inizializza();
    }

    private void inizializza() {
        navigationView = adminPanelFragment.getNavigationView();
        buttonLogout = adminPanelFragment.getButtonLogout();
        NavigationViewAscoltoCLick(navigationView, adminPanelFragment);
        LogoutButtonAsscoltoClick(buttonLogout, adminPanelFragment);

        cardViewNotifica = adminPanelFragment.getCardViewNotifica();
        cardViewAggiungiPersonale = adminPanelFragment.getCardViewAggiungiPersonale();
        cardViewCreaAvviso = adminPanelFragment.getCardViewCreaAvviso();
        cardViewGestioneMenu = adminPanelFragment.getCardViewGestioneMenu();
        cardViewGestioneMenu.setOnClickListener(view -> {
            FragmentUtils.changeFragment(new GestioneMenuFragment(), adminPanelFragment);
        });
        cardViewNotifica.setOnClickListener(view -> {
            FragmentUtils.changeFragment(new ElencoAvvisiFragment(), adminPanelFragment);
        });

        cardViewAggiungiPersonale.setOnClickListener(view -> {
            FragmentUtils.changeFragment(new CreazioneUtenzaFragment(), adminPanelFragment);
        });

        cardViewCreaAvviso.setOnClickListener(view -> {
            FragmentUtils.changeFragment(new CreaAvvisoFragment(), adminPanelFragment);
        });
    }
}
