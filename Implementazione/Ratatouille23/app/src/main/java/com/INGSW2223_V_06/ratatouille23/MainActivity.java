package com.INGSW2223_V_06.ratatouille23;

import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.DataPrimoAccesso;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.deleteLocalUser;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaCucinaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaSalaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.INGSW2223_V_06.ratatouille23.fragment.AddettoAllaCucinaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaElementoFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.LoginFragment;
import com.INGSW2223_V_06.ratatouille23.model.TipoUtente;
import com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.TokenManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";


    public String fragmentAttule = "LoginFragment";
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav);
        frameLayout = findViewById(R.id.frameLayoutActivityMain);
        inizializzaSchermata();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        String categoria;
        Bundle bundleCategoria;
        Fragment gestioneCategoriaFragment = new GestioneCategoriaFragment();
        Log.i(TAG, "cliccato back " + fragmentAttule);
        switch (fragmentAttule) {
            case "GestioneMenuFragment":
            case "CreazioneUtenzaFragment":
            case "CreaAvvisoFragment":
                changeFragment(new AdminPanelFragment());
                break;
            case "ElencoAvvisiFragment":
                if (ritornoTipoAmministratoreOSupervisoreUtente(this)) {
                    changeFragment(new AdminPanelFragment());
                } else if (ritornoTipoAddettoAllaSalaUtente(this)) {
                    changeFragment(new AddettoSalaPanelFragment());
                } else if (ritornoTipoAddettoAllaCucinaUtente(this)) {
                    changeFragment(new AddettoAllaCucinaPanelFragment());
                }
                break;
            case "AggiungiTavoloFragment":
            case "AggiungiElementoAlTavoloFragment":
                changeFragment(new AddettoSalaPanelFragment());
                break;
            case "CreaCategoriaFragment":
            case "GestioneCategoriaFragment":
                changeFragment(new GestioneMenuFragment());
                break;
            case "VisualizzaAvvisoFragment":
                changeFragment(new ElencoAvvisiFragment());
                break;
            case "CreaElementoFragment":
                CreaElementoFragment creaElementoFragment =
                        (CreaElementoFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayoutActivityMain);
                assert creaElementoFragment != null;
                categoria = creaElementoFragment.getCategoria();
                bundleCategoria = new Bundle();
                bundleCategoria.putString("categoria", categoria);
                gestioneCategoriaFragment.setArguments(bundleCategoria);
                changeFragment(gestioneCategoriaFragment);
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    private void changeFragment(Fragment fragmentToChange) {
        fragmentAttule = fragmentToChange.toString();
        Log.i(TAG, "attuale " + fragmentAttule);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutActivityMain, fragmentToChange);
        fragmentTransaction.commit();
    }


    private void inizializzaSchermata() {
        SharedPreferences myPrefs = this.getSharedPreferences("utente_locale", MODE_PRIVATE);
        String email = myPrefs.getString("email", null);
        if (email == null || email.isEmpty() || DataPrimoAccesso(this)==null) {
            deleteLocalUser(this);
            TokenManager.deleteToken();
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            changeFragment(new LoginFragment());
        } else {
            String tipoUtente = myPrefs.getString("TipoUtente", null);
            if (tipoUtente.equals(TipoUtente.Supervisore.toString()) || tipoUtente.equals(TipoUtente.Amministratore.toString())) {
                changeFragment(new AdminPanelFragment());
            } else if (tipoUtente.equals(TipoUtente.AddettoAllaSala.toString())) {
                changeFragment(new AddettoSalaPanelFragment());
            } else if (tipoUtente.equals(TipoUtente.AddettoAllaCucina.toString())) {
                changeFragment(new AddettoAllaCucinaPanelFragment());
            }
        }
    }
}