package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaCucinaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAddettoAllaSalaUtente;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoTipoAmministratoreOSupervisoreUtente;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.fragment.AddettoAllaCucinaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.model.VisualizzaAvviso;
import com.INGSW2223_V_06.ratatouille23.recyclerView.MyAdapterElencoAvvisi;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.AvvisoDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class ElencoAvvisiPresenter {
    private final String TAG = "ElencoAvvisiPresenter";
    public RecyclerView recyclerView;
    private ElencoAvvisiFragment elencoAvvisiFragment;
    private NavigationView navigationView;
    private Button buttonLogout;
    private ArrayList<VisualizzaAvviso> avvisi;
    private static AvvisoDao avvisoDao;
    private TextView textViewInvisibile;
    private ImageButton BackPulsanteVisualizzaElenco;
    private TextView textLoadingCirculatorElencoAvvisi;

    private CircularProgressIndicator loadingCirculatorElencoAvvisi;


    public ElencoAvvisiPresenter(ElencoAvvisiFragment elencoAvvisiFragment) {
        this.elencoAvvisiFragment = elencoAvvisiFragment;
        inizializza();
    }

    private void inizializza() {
        recyclerView = elencoAvvisiFragment.getRecyclerView();
        navigationView = elencoAvvisiFragment.getNavigationView();
        buttonLogout = elencoAvvisiFragment.getButtonLogout();
        textViewInvisibile = elencoAvvisiFragment.getTextViewInvisibile();
        BackPulsanteVisualizzaElenco = elencoAvvisiFragment.getBackPulsanteVisualizzaElenco();
        loadingCirculatorElencoAvvisi = elencoAvvisiFragment.getLoadingCirculatorElencoAvvisi();
        textLoadingCirculatorElencoAvvisi =
                elencoAvvisiFragment.getTextLoadingCirculatorElencoAvvisi();
        avvisoDao = new AvvisoDao(elencoAvvisiFragment.getContext());
        NavigationViewAscoltoCLick(navigationView, elencoAvvisiFragment);
        LogoutButtonAsscoltoClick(buttonLogout, elencoAvvisiFragment);
        visualizzaListaAvvisi();
        BackPulsanteVisualizzaElenco.setOnClickListener(view1 -> {
            if (ritornoTipoAmministratoreOSupervisoreUtente(elencoAvvisiFragment.getContext())) {
                FragmentUtils.changeFragment(new AdminPanelFragment(), elencoAvvisiFragment);
            } else if (ritornoTipoAddettoAllaSalaUtente(elencoAvvisiFragment.getContext())) {
                FragmentUtils.changeFragment(new AddettoSalaPanelFragment(), elencoAvvisiFragment);
            } else if (ritornoTipoAddettoAllaCucinaUtente(elencoAvvisiFragment.getContext())) {
                FragmentUtils.changeFragment(new AddettoAllaCucinaPanelFragment(), elencoAvvisiFragment);
            }

        });
    }

    private void visualizzaListaAvvisi() {
        recyclerView.setHasFixedSize(true);
        loadingCirculatorElencoAvvisi.setVisibility(View.VISIBLE);
        textLoadingCirculatorElencoAvvisi.setVisibility(View.VISIBLE);
        avvisoDao.returnAvvisiDiUnoSpecificoUtente(new Callback() {
            @Override
            public void onSuccess(Object object) {
                setVisibleLoading(8);
                avvisi = (ArrayList<VisualizzaAvviso>) object;
                assert avvisi != null;
                if (!avvisi.isEmpty()) {
                    textViewInvisibile.setVisibility(View.GONE);
                    MyAdapterElencoAvvisi myAdpter = new MyAdapterElencoAvvisi(avvisi, elencoAvvisiFragment.getActivity(), elencoAvvisiFragment);
                    LinearLayoutManager manager = new LinearLayoutManager(elencoAvvisiFragment.getContext());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(myAdpter);
                    myAdpter.setOnItemClickListener(position -> {
                        avvisi.remove(position);
                        myAdpter.notifyItemRemoved(position);
                        if (avvisi.isEmpty()) {
                            textViewInvisibile.setVisibility(View.VISIBLE);
                        } else {
                            textViewInvisibile.setVisibility(View.GONE);
                        }
                    });
                    myAdpter.notifyDataSetChanged();
                } else {
                    textViewInvisibile.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e(TAG, "onFailure: ", error);
                if (elencoAvvisiFragment.getContext() != null) {
                    setVisibleLoading(8);
                    Toast.makeText(elencoAvvisiFragment.getContext(), error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    if (error.getMessage().contains("Server al momento non disponibile")) {
                        textViewInvisibile.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorElencoAvvisi.setVisibility(visibleLoading);
        textLoadingCirculatorElencoAvvisi.setVisibility(visibleLoading);
    }
}
