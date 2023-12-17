package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.ElencoAvvisiFragment;
import com.INGSW2223_V_06.ratatouille23.recyclerView.MyAdapterElencoTavoli;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.TavoloDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavoloOrdinato;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class AddettoSalaPanelPresenter {
    private final String TAG = "AddettoSalaPanelPresenter";
    private AddettoSalaPanelFragment addettoSalaPanelFragment;
    private NavigationView navigationView;
    private Button buttonLogout;
    private TextView textTavoliInvisibile;
    private ImageButton campanellaImageButton;
    private ImageButton aggiungiTavolo;

    private RecyclerView recyclerViewTavoli;

    private TavoloDao tavoloDao;

    private CircularProgressIndicator loadingCirculatorHomeAddettoAllaSala;
    private TextView textLoadingCirculatorHomeAddettoAllaSala;

    public AddettoSalaPanelPresenter(AddettoSalaPanelFragment addettoSalaPanelFragment) {
        this.addettoSalaPanelFragment = addettoSalaPanelFragment;
        inizializza();

    }

    public void inizializza() {
        navigationView = addettoSalaPanelFragment.getNavigationView();
        buttonLogout = addettoSalaPanelFragment.getButtonLogout();
        NavigationViewAscoltoCLick(navigationView, addettoSalaPanelFragment);
        LogoutButtonAsscoltoClick(buttonLogout, addettoSalaPanelFragment);
        textLoadingCirculatorHomeAddettoAllaSala =
                addettoSalaPanelFragment.getTextLoadingCirculatorHomeAddettoAllaSala();
        campanellaImageButton = addettoSalaPanelFragment.getCampanellaImageButton();


        recyclerViewTavoli = addettoSalaPanelFragment.getRecyclerViewTavoli();
        textTavoliInvisibile = addettoSalaPanelFragment.getTextTavoliInvisibile();

        aggiungiTavolo = addettoSalaPanelFragment.getAggiungiTavolo();

        loadingCirculatorHomeAddettoAllaSala =
                addettoSalaPanelFragment.getLoadingCirculatorHomeAddettoAllaSala();
        tavoloDao = new TavoloDao(addettoSalaPanelFragment.getContext());

        aggiungiTavoloListener();
        listaTavoliRecycler();

        campanellaImageButton.setOnClickListener(v -> {
            FragmentUtils.changeFragment(new ElencoAvvisiFragment(), addettoSalaPanelFragment);
        });
    }

    private void listaTavoliRecycler() {
        Log.i(TAG, "listaTavoliRecycler: ");
        setVisibleLoading(0);
        tavoloDao.elencoTavoliOccupati(new Callback() {
            @Override
            public void onSuccess(Object object) {
                setVisibleLoading(8);
                ArrayList<DtoTavoloOrdinato> dtoTavoloOrdinatoes = (ArrayList<DtoTavoloOrdinato>) object;
                Log.i(TAG, "onSuccess: " + dtoTavoloOrdinatoes);
                MyAdapterElencoTavoli myAdpter =
                        new MyAdapterElencoTavoli(addettoSalaPanelFragment.getActivity(), dtoTavoloOrdinatoes
                                , addettoSalaPanelFragment);
                recyclerViewTavoli.setLayoutManager(new GridLayoutManager(addettoSalaPanelFragment.getContext(), 2));
                recyclerViewTavoli.setAdapter(myAdpter);
                textTavoliInvisibile.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable error) {
                if (addettoSalaPanelFragment.getContext() != null) {
                    textTavoliInvisibile.setVisibility(View.VISIBLE);
                    setVisibleLoading(8);
                    Toast.makeText(addettoSalaPanelFragment.getContext(), error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void aggiungiTavoloListener() {
        aggiungiTavolo.setOnClickListener(view -> {
            FragmentUtils.changeFragment(new AggiungiTavoloFragment(), addettoSalaPanelFragment);
        });
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorHomeAddettoAllaSala.setVisibility(visibleLoading);
        textLoadingCirculatorHomeAddettoAllaSala.setVisibility(visibleLoading);
    }
}
