package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.INGSW2223_V_06.ratatouille23.fragment.AddettoSalaPanelFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.model.Tavolo;
import com.INGSW2223_V_06.ratatouille23.recyclerView.MyAdapterElencoElementiDelTavolo;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.TavoloDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoElementi;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavolo;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AggiungiTavoloPresenter {
    private final String TAG = "AggiungiTavoloPresenter";
    private AggiungiTavoloFragment aggiungiTavoloFragment;
    private TextInputEditText numeroTavoloText;
    private AutoCompleteTextView ElementoTavolo;
    private final RecyclerView recyclerViewElementoDelTavolo;
    private List<Elementi> elementiTrovate;
    private ArrayList<DtoElementi> elementi = new ArrayList<>();
    private MyAdapterElencoElementiDelTavolo myAdpter;
    private final TavoloDao tavoloDao;
    private NavigationView navigationView;
    private Button buttonLogout;
    private ImageButton backPulsanteArrowAggiungiElementoDelTavolo;
    private MaterialButton salvaButton;

    private CircularProgressIndicator loadingCirculatorAggiungiTavolo;
    private TextView textLoadingCirculatorAggiungiTavolo;


    public AggiungiTavoloPresenter(AggiungiTavoloFragment aggiungiTavoloFragment) {

        this.aggiungiTavoloFragment = aggiungiTavoloFragment;
        numeroTavoloText = this.aggiungiTavoloFragment.getNumeroTavolo();
        ElementoTavolo =
                this.aggiungiTavoloFragment.getElementoTavolo();
        recyclerViewElementoDelTavolo = this.aggiungiTavoloFragment.getRecyclerViewElementoDelTavolo();

        navigationView = this.aggiungiTavoloFragment.getNavigationView();
        buttonLogout = this.aggiungiTavoloFragment.getButtonLogout();

        backPulsanteArrowAggiungiElementoDelTavolo =
                this.aggiungiTavoloFragment.getBackPulsanteArrowAggiungiElementoDelTavolo();
        salvaButton = this.aggiungiTavoloFragment.getSalvaButton();
        loadingCirculatorAggiungiTavolo = aggiungiTavoloFragment.getLoadingCirculatorAggiungiTavolo();
        textLoadingCirculatorAggiungiTavolo =
                aggiungiTavoloFragment.getTextLoadingCirculatorAggiungiTavolo();
        NavigationViewAscoltoCLick(navigationView, aggiungiTavoloFragment);
        LogoutButtonAsscoltoClick(buttonLogout, aggiungiTavoloFragment);
        tavoloDao = new TavoloDao(aggiungiTavoloFragment.getContext());
        autoCompleteTextViewElementoTavoloListener();
        backPulsanteArrowAggiungiElementoDelTavoloListener();


        salvaButtonListener();
    }

    private void salvaButtonListener() {
        salvaButton.setOnClickListener(view -> {
            String numeroTavolo = numeroTavoloText.getText().toString();
            if (!numeroTavolo.trim().isEmpty() && !elementi.isEmpty()) {
                setVisibleLoading(0);
                setEnableButton(false);

                Tavolo tavolo = new Tavolo();
                tavolo.setNumeroTavolo(Integer.parseInt(numeroTavolo));
                DtoTavolo DTOTavolo = new DtoTavolo(elementi, tavolo);
                tavoloDao.ordinazioneTavolo(DTOTavolo,
                        new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                if (aggiungiTavoloFragment.getContext() != null) {
                                    setVisibleLoading(8);
                                    setEnableButton(true);
                                    Toast.makeText(aggiungiTavoloFragment.getContext(), object.toString()
                                            , Toast.LENGTH_LONG).show();
                                    numeroTavoloText.setText(null);
                                    elementi.clear();
                                    recyclerViewElementoDelTavolo.removeAllViews();
                                }
                            }

                            @Override
                            public void onFailure(Throwable error) {
                                if (aggiungiTavoloFragment.getContext() != null) {
                                    setVisibleLoading(8);
                                    setEnableButton(true);
                                    Toast.makeText(aggiungiTavoloFragment.getContext(), error.getMessage()
                                            , Toast.LENGTH_LONG).show();
                                    if(Objects.requireNonNull(error.getMessage()).equalsIgnoreCase("Elemento non più disponibile")){
                                        elementi.clear();
                                        recyclerViewElementoDelTavolo.removeAllViews();
                                    }

                                }
                            }
                        });
            } else {
                if (aggiungiTavoloFragment.getContext() != null)
                    Toast.makeText(aggiungiTavoloFragment.getContext(), "Uno o più campi non sono stati definiti",
                            Toast.LENGTH_LONG).show();
            }
        });

    }

    private void backPulsanteArrowAggiungiElementoDelTavoloListener() {
        backPulsanteArrowAggiungiElementoDelTavolo.setOnClickListener(view -> FragmentUtils.changeFragment(new AddettoSalaPanelFragment(), aggiungiTavoloFragment));
    }

    private void autoCompleteTextViewElementoTavoloListener() {
        tavoloDao.elementiDisponibile(new Callback() {
            @Override
            public void onSuccess(Object object) {
                elementiTrovate = (List<Elementi>) object;
                try {
                    assert elementiTrovate != null;
                    List<String> nomeElemento =
                            elementiTrovate.stream().map(Elementi::getNomeElemento).collect(Collectors.toList());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(aggiungiTavoloFragment.getContext(),
                            android.R.layout.simple_dropdown_item_1line, nomeElemento);
                    ElementoTavolo.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (RuntimeException e) {
                    Log.e(TAG, "RuntimeException: ", e);
                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.i(TAG, "onFailure: " + error);

            }
        });
        ElementoTavolo.setOnItemClickListener((parent, view, position, id) -> {

            Log.i(TAG, "onItemClick: " + parent.getItemAtPosition(position));
            DtoElementi p = new DtoElementi();
            for (Elementi elementi : elementiTrovate) {
                if (elementi.getNomeElemento().equalsIgnoreCase(parent.getItemAtPosition(position).toString())) {
                    p.setElementoOrdinata(elementi);
                    p.setQuantita(1);
                    p.setCosto(elementi.getPrezzo());
                    break;
                }
            }
            if (!esisteLElemento(p)) {//se la Elemento non è presente allora l aggiungo
                elementi.add(0, p);//aggiungo l elemento in testa
            }

            myAdpter =
                    new MyAdapterElencoElementiDelTavolo(aggiungiTavoloFragment.getActivity(),
                            elementi
                            , aggiungiTavoloFragment);
            LinearLayoutManager manager = new LinearLayoutManager(aggiungiTavoloFragment.getContext());
            recyclerViewElementoDelTavolo.setLayoutManager(manager);
            recyclerViewElementoDelTavolo.setAdapter(myAdpter);
            myAdpter.notifyDataSetChanged();
            ElementoTavolo.setText(null);

        });

    }

    private boolean esisteLElemento(DtoElementi ElementoDaCercare) {
        for (DtoElementi dtoElementi : elementi) {
            if (dtoElementi.getElementoOrdinata().getNomeElemento().equalsIgnoreCase(ElementoDaCercare.getElementoOrdinata().getNomeElemento())) {
                int quantita = dtoElementi.getQuantita();
                quantita++;
                dtoElementi.setQuantita(quantita);
                return true;
            }
        }
        return false;
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorAggiungiTavolo.setVisibility(visibleLoading);
        textLoadingCirculatorAggiungiTavolo.setVisibility(visibleLoading);
    }

    private void setEnableButton(boolean enable) {
        salvaButton.setEnabled(enable);
        backPulsanteArrowAggiungiElementoDelTavolo.setEnabled(enable);
    }

}
