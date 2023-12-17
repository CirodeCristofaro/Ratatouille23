package com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils;

import static android.content.Context.MODE_PRIVATE;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.aggiungiSpazioTipoUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.deleteLocalUser;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaCucinaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaSalaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoAllaCucinaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaAvvisoFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreazioneUtenzaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.LoginFragment;
import com.INGSW2223_V_06.ratatouille23.model.TipoUtente;
import com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.TokenManager;
import com.google.android.material.navigation.NavigationView;

public class NavMenuUtils {
    private static DrawerLayout drawerLayout;
    private static Toolbar toolbar;
    private static ActionBarDrawerToggle toggle;
    private static NavigationView navigationView;


    public static void attivaMenuPerFragment(Fragment fragment) {

        SharedPreferences myPrefs = fragment.requireContext().getSharedPreferences("utente_locale", MODE_PRIVATE);
        Activity activity = fragment.getActivity();
        String tipoUtente = myPrefs.getString("TipoUtente", null);
        assert activity != null;
        drawerLayout = activity.findViewById(R.id.drawerLayout);
        toolbar = activity.findViewById(R.id.toolbar);
        toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close);
        navigationView = activity.findViewById(R.id.nav);

        if (tipoUtente.equals(TipoUtente.Amministratore.toString())) {
            SetItemVisibilePerAdmin(navigationView);
        } else if (tipoUtente.equals(TipoUtente.AddettoAllaSala.toString())) {
            SetItemVisibilePerAddettiAllaSala(navigationView);
        } else if (tipoUtente.equals(TipoUtente.Supervisore.toString())) {
            SetItemVisibilePerSupervisori(navigationView);
        } else {
            SetItemVisibilePerAddettoAllaCucina(navigationView);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nomeHeader);
        navUsername.setText(myPrefs.getString("nome", null));
        TextView navCognome = headerView.findViewById(R.id.cognomeHeader);
        navCognome.setText(myPrefs.getString("cognome", null));
        TextView navRuolo = headerView.findViewById(R.id.ruoloHeader);
        navRuolo.setText(String.format("Ruolo: %s", aggiungiSpazioTipoUtente(myPrefs.getString("TipoUtente", null))));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private static void disattivaVisibilitaElementiMenu(Menu menu, @IdRes int id, boolean visible) {
        menu.findItem(id).setVisible(visible);
    }

    private static void SetItemVisibilePerAdmin(NavigationView navigationView) {
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiPersonale, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaCategoria, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.gestioneMenu, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaAvviso, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiTavolo, false);

    }

    private static void SetItemVisibilePerSupervisori(NavigationView navigationView) {
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiPersonale, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaCategoria, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.gestioneMenu, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaAvviso, true);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiTavolo, false);

    }

    private static void SetItemVisibilePerAddettoAllaCucina(NavigationView navigationView) {
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiPersonale, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaCategoria, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.gestioneMenu, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaAvviso, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiTavolo, false);

    }

    private static void SetItemVisibilePerAddettiAllaSala(NavigationView navigationView) {
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiPersonale, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaCategoria, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.gestioneMenu, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.creaAvviso, false);
        disattivaVisibilitaElementiMenu(navigationView.getMenu(), R.id.aggiungiTavolo, true);
    }

    public static void NavigationViewAscoltoCLick(NavigationView navigationView, Fragment fragment) {
        Activity activity = fragment.getActivity();
        assert activity != null;
        drawerLayout = activity.findViewById(R.id.drawerLayout);
        toolbar = activity.findViewById(R.id.toolbar);
        toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.notifiche:
                    FragmentUtils.changeFragment(new ElencoAvvisiFragment(), fragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.creaCategoria:
                    FragmentUtils.changeFragment(new CreaCategoriaFragment(), fragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.aggiungiPersonale:
                    FragmentUtils.changeFragment(new CreazioneUtenzaFragment(), fragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.gestioneMenu:
                    FragmentUtils.changeFragment(new GestioneMenuFragment(), fragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.creaAvviso:
                    FragmentUtils.changeFragment(new CreaAvvisoFragment(), fragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.home:
                    if (ritornoTipoAmministratoreOSupervisoreUtente(fragment.requireContext())) {
                        FragmentUtils.changeFragment(new AdminPanelFragment(), fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (ritornoTipoAddettoAllaSalaUtente(fragment.requireContext())) {
                        FragmentUtils.changeFragment(new AddettoSalaPanelFragment(), fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (ritornoTipoAddettoAllaCucinaUtente(fragment.requireContext())) {
                        FragmentUtils.changeFragment(new AddettoAllaCucinaPanelFragment(), fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    break;

                case R.id.aggiungiTavolo:
                    FragmentUtils.changeFragment(new AggiungiTavoloFragment(), fragment);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });
    }

    public static void LogoutButtonAsscoltoClick(Button button, Fragment fragment) {

        button.setOnClickListener(view -> {
            assert fragment.getContext() != null;
            TokenManager.deleteToken();
            deleteLocalUser(fragment.getContext());
            drawerLayout.closeDrawers();
            FragmentUtils.changeFragment(new LoginFragment(), fragment);
        });

    }

}
