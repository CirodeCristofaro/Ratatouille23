package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.fragment.AdminPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.model.Categorie;
import com.INGSW2223_V_06.ratatouille23.recyclerView.MyAdapterElencoCategorie;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.MenuDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.Collections;

public class GestioneMenuPresenter {
    private final String TAG = "GestioneMenuPresenter";
    private GestioneMenuFragment gestioneMenuFragment;
    private RecyclerView recyclerView;
    private MenuDao menuDao;
    private ArrayList<Categorie> categorieList;
    private ImageButton backPulsanteGesioneMenu, aggiungiCategoria;
    private TextView textGestionemenuInvisibile;
    private MyAdapterElencoCategorie myAdpter;
    private NavigationView navigationView;
    private Button buttonLogout;
    private CircularProgressIndicator loadingCirculatorGestioneMenu;
    private TextView textLoadingCirculatorGestioneMenu;
    private ItemTouchHelper.SimpleCallback simpleCallback;

    public GestioneMenuPresenter(GestioneMenuFragment gestioneMenuFragment) {
        this.gestioneMenuFragment = gestioneMenuFragment;
        inizializza();
    }

    private void inizializza() {
        recyclerView = gestioneMenuFragment.getRecyclerView();
        backPulsanteGesioneMenu = gestioneMenuFragment.getBackPulsanteGesioneMenu();
        aggiungiCategoria = gestioneMenuFragment.getAggiungiCategoria();
        textGestionemenuInvisibile = gestioneMenuFragment.getTextGestionemenuInvisibile();
        loadingCirculatorGestioneMenu = gestioneMenuFragment.getLoadingCirculatorGestioneMenu();
        navigationView = gestioneMenuFragment.getNavigationView();
        buttonLogout = gestioneMenuFragment.getButtonLogout();
        NavigationViewAscoltoCLick(navigationView, gestioneMenuFragment);
        LogoutButtonAsscoltoClick(buttonLogout, gestioneMenuFragment);
        textLoadingCirculatorGestioneMenu =
                gestioneMenuFragment.getTextLoadingCirculatorGestioneMenu();
        menuDao = new MenuDao(gestioneMenuFragment.getContext());
        backPulsanteGesioneMenu.setOnClickListener(view12 ->
                FragmentUtils.changeFragment(new AdminPanelFragment(), gestioneMenuFragment));


        visualizzaCategorie();
        moveCategorie();
        aggiungiCategoriaFragment();
    }

    private  void aggiungiCategoriaFragment(){
        aggiungiCategoria.setOnClickListener(view1 -> {
            FragmentUtils.changeFragment(new CreaCategoriaFragment(), gestioneMenuFragment);
        });
    }
    private void visualizzaCategorie() {
        recyclerView.setHasFixedSize(true);
        setVisibleLoading(0);
        menuDao.getCategorie(new Callback() {

            @Override
            public void onSuccess(Object object) {
                setVisibleLoading(8);
                categorieList = (ArrayList<Categorie>) object;
                if (categorieList.size() != 0) {
                    Log.i(TAG, "getCategorie " + categorieList.toString());
                    myAdpter = new MyAdapterElencoCategorie(categorieList, gestioneMenuFragment.getActivity(), gestioneMenuFragment);
                    recyclerView.setLayoutManager(new GridLayoutManager(gestioneMenuFragment.getContext(), 3));
                    recyclerView.setAdapter(myAdpter);
                    myAdpter.notifyDataSetChanged();
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                } else {
                    textGestionemenuInvisibile.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable error) {
                setVisibleLoading(8);
                Log.e(TAG, "onFailure: ", error);
                if (gestioneMenuFragment.getContext() != null) {
                    Toast.makeText(gestioneMenuFragment.getContext(), error.getMessage(),
                            Toast.LENGTH_LONG).show();
                    if (error.getMessage().contains("Server al momento non disponibile")) {
                        textGestionemenuInvisibile.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }


    private void moveCategorie() {
        simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int dallaPosizione = viewHolder.getAdapterPosition();
                int allaPosizione = target.getAdapterPosition();
                Collections.swap(categorieList, dallaPosizione, allaPosizione);
                Log.i(TAG, "categorie posizioni " + categorieList.toString());
                menuDao.ordinaCategorie(categorieList, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        Log.i(TAG, "posizioni aggiornate");
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.e(TAG, "onFailure: ", error);
                        if (gestioneMenuFragment.getContext() != null)
                            Toast.makeText(gestioneMenuFragment.getContext(), error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                    }
                });
                assert recyclerView.getAdapter() != null;
                recyclerView.getAdapter().notifyItemMoved(dallaPosizione, allaPosizione);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorGestioneMenu.setVisibility(visibleLoading);
        textLoadingCirculatorGestioneMenu.setVisibility(visibleLoading);
    }
}
