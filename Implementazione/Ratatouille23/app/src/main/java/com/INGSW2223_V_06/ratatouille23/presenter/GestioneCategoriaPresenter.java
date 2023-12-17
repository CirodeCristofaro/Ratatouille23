package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.fragment.CreaElementoFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.recyclerView.MyAdapterElencoElementi;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.MenuDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class GestioneCategoriaPresenter {
    private final String TAG = "GestioneCategoriaPresenter";
    private RecyclerView recyclerView;
    private GestioneCategoriaFragment gestioneCategoriaFragment;
    private MenuDao menuDao;
    private ArrayList<Elementi> ElementoList;
    private String categoria;
    private ImageButton backArrow, aggiungiElemento;
    private TextView textGestioneCategoriaInvisibile;
    private MyAdapterElencoElementi myAdpter;
    private NavigationView navigationView;
    private Button buttonLogout;
    private Spinner spinnerOrdinamento;
    private CircularProgressIndicator loadingCirculatorGestioneCategoria;
    private TextView textLoadingCirculatorGestioneCategoria;
    private ItemTouchHelper.SimpleCallback simpleCallback;

    public GestioneCategoriaPresenter(GestioneCategoriaFragment gestioneCategoriaFragment, String categoria) {
        this.gestioneCategoriaFragment = gestioneCategoriaFragment;
        this.categoria = categoria;

        inizializza();
    }

    private void inizializza() {
        navigationView = gestioneCategoriaFragment.getNavigationView();
        buttonLogout = gestioneCategoriaFragment.getButtonLogout();
        NavigationViewAscoltoCLick(navigationView, gestioneCategoriaFragment);
        LogoutButtonAsscoltoClick(buttonLogout, gestioneCategoriaFragment);
        textLoadingCirculatorGestioneCategoria =
                gestioneCategoriaFragment.getTextLoadingCirculatorGestioneCategoria();
        recyclerView = gestioneCategoriaFragment.getRecyclerView();
        backArrow = gestioneCategoriaFragment.getBackArrow();
        aggiungiElemento = gestioneCategoriaFragment.getAggiungiElemento();
        textGestioneCategoriaInvisibile = gestioneCategoriaFragment.getTextGestioneCategoriaInvisibile();
        loadingCirculatorGestioneCategoria =
                gestioneCategoriaFragment.getLoadingCirculatorGestioneCategoria();

        backArrow.setOnClickListener(v -> FragmentUtils.changeFragment(new GestioneMenuFragment(), gestioneCategoriaFragment));

        menuDao = new MenuDao(gestioneCategoriaFragment.getContext());
        spinnerOrdinamento = gestioneCategoriaFragment.getSpinnerOrdinamento();

        visualizzaElementi();
        spinnerOrdinamento();
        moveElemento();
        aggiungiElementoFragment();
    }

    private void aggiungiElementoFragment(){
        aggiungiElemento.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoria);
            CreaElementoFragment creaElementoFragment = new CreaElementoFragment();
            creaElementoFragment.setArguments(bundle);
            FragmentUtils.changeFragment(creaElementoFragment, gestioneCategoriaFragment);

        });
    }

    private void spinnerOrdinamento() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(gestioneCategoriaFragment.getContext(),
                R.array.ElementiOrdinamento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrdinamento.setAdapter(adapter);
        Log.i(TAG, "spinnerOrdinamento: " + ElementoList);

        spinnerOrdinamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "onItemSelected: " + adapterView.getItemAtPosition(i));
                if (adapterView.getItemAtPosition(i) != null && !adapterView.getItemAtPosition(i).equals("")) {
                    if (ElementoList != null && !ElementoList.isEmpty()) {

                        if (adapterView.getItemAtPosition(i).equals("Nome crescente")) {
                            if (!Objects.isNull(ElementoList)) {
                                Log.i(TAG, "ordinamento per Nome crescente ");
                                ElementoList.sort(Comparator.comparing(Elementi::getNomeElemento));
                                myAdpter = new MyAdapterElencoElementi(ElementoList, gestioneCategoriaFragment.getActivity(), gestioneCategoriaFragment);
                                recyclerView.setAdapter(myAdpter);
                                myAdpter.notifyDataSetChanged();
                            }
                        }
                        if (adapterView.getItemAtPosition(i).equals("Nome decrescente")) {
                            if (!Objects.isNull(ElementoList)) {
                                Log.i(TAG, "ordinamento per  Nome decrescente ");
                                ElementoList.sort(Comparator.comparing(Elementi::getNomeElemento).reversed());
                                myAdpter.setArrayList(ElementoList);
                                recyclerView.setAdapter(myAdpter);
                                myAdpter.notifyDataSetChanged();
                            }
                        }
                        if (adapterView.getItemAtPosition(i).equals("Prezzo decrescente")) {
                            if (!Objects.isNull(ElementoList)) {
                                Log.i(TAG, "ordinamento per Prezzo decrescente ");
                                ElementoList.sort(Comparator.comparing(Elementi::getPrezzo).reversed());
                                myAdpter.setArrayList(ElementoList);
                                recyclerView.setAdapter(myAdpter);
                                myAdpter.notifyDataSetChanged();
                            }
                        }

                        if (adapterView.getItemAtPosition(i).equals("Prezzo crescente")) {
                            if (!Objects.isNull(ElementoList)) {
                                Log.i(TAG, "ordinamento per Prezzo crescente ");
                                ElementoList.sort(Comparator.comparing(Elementi::getPrezzo));
                                myAdpter.setArrayList(ElementoList);
                                myAdpter.notifyDataSetChanged();
                                recyclerView.setAdapter(myAdpter);

                            }
                        }
                        setVisibleLoading(0);
                        recyclerView.setVisibility(View.GONE);
                        menuDao.ordinaElementi(ElementoList, new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                recyclerView.setVisibility(View.VISIBLE);
                                setVisibleLoading(8);
                                Log.i(TAG, "posizioni aggiornate");
                            }

                            @Override
                            public void onFailure(Throwable error) {
                                Log.e(TAG, "onError ordinElemento: ");
                                if (gestioneCategoriaFragment.getContext() != null) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    setVisibleLoading(8);
                                    Toast.makeText(gestioneCategoriaFragment.getContext(), error.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void visualizzaElementi() {
        recyclerView.setHasFixedSize(true);
        setVisibleLoading(0);
        menuDao.allElementi(categoria, new Callback() {
            @Override
            public void onSuccess(Object object) {
                setVisibleLoading(8);
                ElementoList = (ArrayList<Elementi>) object;
                Log.i("getElemento ", String.valueOf(ElementoList.size()));
                if (!ElementoList.isEmpty()) {
                    setVisibleLoading(8); //gone
                    Log.i("getElemento ", ElementoList.toString());
                    myAdpter = new MyAdapterElencoElementi(ElementoList, gestioneCategoriaFragment.getActivity(), gestioneCategoriaFragment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(gestioneCategoriaFragment.getContext()));
                    recyclerView.setAdapter(myAdpter);
                    myAdpter.notifyDataSetChanged();
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                } else {
                    FragmentUtils.changeFragment(new GestioneMenuFragment(), gestioneCategoriaFragment);
                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.i("getElemento ", error.getMessage());
                if (gestioneCategoriaFragment.getContext() != null) {
                    setVisibleLoading(8); //gone
                    Toast.makeText(gestioneCategoriaFragment.getContext(), error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void moveElemento() {
        simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int dallaPosizione = viewHolder.getAdapterPosition();
                int allaPosizione = target.getAdapterPosition();
                Collections.swap(ElementoList, dallaPosizione, allaPosizione);
                Log.i(TAG, ElementoList.toString());
                menuDao.ordinaElementi(ElementoList, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        Log.i(TAG, "posizioni aggiornate");
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Log.e(TAG, "onError ordinElemento: ");
                        if (gestioneCategoriaFragment.getContext() != null)
                            Toast.makeText(gestioneCategoriaFragment.getContext(), error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                    }
                });
                //  recyclerView.setHasFixedSize(true);
                assert recyclerView.getAdapter() != null;
                recyclerView.getAdapter().notifyItemMoved(dallaPosizione, allaPosizione);
                //slow
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            //slow
            @Override
            public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
                final int direction = (int) Math.signum(viewSizeOutOfBounds);
                return 10 * direction;
            }
        };

    }


    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorGestioneCategoria.setVisibility(visibleLoading);
        textLoadingCirculatorGestioneCategoria.setVisibility(visibleLoading);
    }


}
