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
import com.INGSW2223_V_06.ratatouille23.fragment.AggiungiElementoAlTavoloFragment;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.model.Tavolo;
import com.INGSW2223_V_06.ratatouille23.model.TavoloOrdinaElemento;
import com.INGSW2223_V_06.ratatouille23.recyclerView.myAdapterAggiungiElementoAlTavolo;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.TavoloDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoElementi;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.DtoTavolo;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AggiungiElementoAlTavoloPresenter {
    private static final String TAG = "AggiungiElementoAlTavoloPresenter";
    private AggiungiElementoAlTavoloFragment aggiungiElementoAlTavoloFragment;
    private TextView gestioneTavoloN;

    private AutoCompleteTextView autoCompleteTextViewElementoAlTavolo;
    private RecyclerView recyclerViewElementoAlTavolo;
    private TavoloDao tavoloDao;

    private myAdapterAggiungiElementoAlTavolo myAdpter;
    private List<Elementi> elementiTrovate;
    private List<DtoElementi> ElementoList = new ArrayList<>();
    private List<TavoloOrdinaElemento> tavoloOrdinaElemento;
    private final ImageButton backPulsanteArrowAggiungiElementoAlTavolo;

    private NavigationView navigationView;
    private Button buttonLogout;
    private MaterialButton buttonAggiornaTavolo;
    private String idTavolo;
    private CircularProgressIndicator loadingCirculatorAggiungiElementoAlTavolo;
    private TextView textLoadingCirculatorAggiungiElementoAlTavolo;


    public AggiungiElementoAlTavoloPresenter(AggiungiElementoAlTavoloFragment aggiungiElementoAlTavoloFragment) {
        this.aggiungiElementoAlTavoloFragment = aggiungiElementoAlTavoloFragment;

        gestioneTavoloN = this.aggiungiElementoAlTavoloFragment.getGestioneTavoloN();


        autoCompleteTextViewElementoAlTavolo =
                this.aggiungiElementoAlTavoloFragment.getAutoCompleteTextViewElementoAlTavolo();
        recyclerViewElementoAlTavolo = this.aggiungiElementoAlTavoloFragment.getRecyclerViewElementoAlTavolo();

        backPulsanteArrowAggiungiElementoAlTavolo =
                this.aggiungiElementoAlTavoloFragment.getBackPulsanteArrowAggiungiElementoAlTavolo();
        idTavolo = this.aggiungiElementoAlTavoloFragment.getIdTavoloBundle();
        navigationView = this.aggiungiElementoAlTavoloFragment.getNavigationView();
        buttonLogout = this.aggiungiElementoAlTavoloFragment.getButtonLogout();
        buttonAggiornaTavolo = this.aggiungiElementoAlTavoloFragment.getButtonAggiornaTavolo();

        tavoloDao = new TavoloDao(aggiungiElementoAlTavoloFragment.getContext());
        textLoadingCirculatorAggiungiElementoAlTavolo =
                aggiungiElementoAlTavoloFragment.getTextLoadingCirculatorAggiungiElementoAlTavolo();
        loadingCirculatorAggiungiElementoAlTavolo =
                aggiungiElementoAlTavoloFragment.getLoadingCirculatorAggiungiElementoAlTavolo();
        NavigationViewAscoltoCLick(navigationView, aggiungiElementoAlTavoloFragment);
        LogoutButtonAsscoltoClick(buttonLogout, aggiungiElementoAlTavoloFragment);
        ritornoInformazioniDelTavoloListener();
        elementoDisponibileListener();
        autoCompleteTextViewElementoAlTavoloListener();
        backPulsanteArrowAggiungiElementoAlTavoloListener();
        buttonAggiornaTavoloListener();
    }

    private void backPulsanteArrowAggiungiElementoAlTavoloListener() {
        backPulsanteArrowAggiungiElementoAlTavolo.setOnClickListener(v ->
                FragmentUtils.changeFragment(new AddettoSalaPanelFragment(), aggiungiElementoAlTavoloFragment));
    }

    private void autoCompleteTextViewElementoAlTavoloListener() {
        autoCompleteTextViewElementoAlTavolo.setOnItemClickListener((parent, view, position, id) -> {

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
                ElementoList.add(0, p);//e l aggiungo in testa
            }
            try {

                myAdpter = new myAdapterAggiungiElementoAlTavolo(aggiungiElementoAlTavoloFragment.getActivity(),
                        new ArrayList<>(ElementoList)
                        , aggiungiElementoAlTavoloFragment);
                LinearLayoutManager manager = new LinearLayoutManager(aggiungiElementoAlTavoloFragment.getContext());
                recyclerViewElementoAlTavolo.setLayoutManager(manager);
                recyclerViewElementoAlTavolo.setAdapter(myAdpter);
                myAdpter.notifyDataSetChanged();
                autoCompleteTextViewElementoAlTavolo.setText(null);
            } catch (RuntimeException e) {
                Log.e(TAG, "autoCompleteTextViewElementoAlTavoloListener: ", e);
            }

        });
    }

    private void elementoDisponibileListener() throws RuntimeException {
        tavoloDao.elementiDisponibile(new Callback() {
            @Override
            public void onSuccess(Object object) {
                elementiTrovate = (List<Elementi>) object;
                try {
                    List<String> nomeElemento =
                            elementiTrovate.stream().map(Elementi::getNomeElemento).collect(Collectors.toList());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(aggiungiElementoAlTavoloFragment.getContext(),
                            android.R.layout.simple_dropdown_item_1line, nomeElemento);
                    autoCompleteTextViewElementoAlTavolo.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (RuntimeException e) {
                    Log.e(TAG, "onSuccess: ", e);
                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.i(TAG, "onFailure: " + error);
                if (aggiungiElementoAlTavoloFragment.getContext() != null) {

                    Toast.makeText(aggiungiElementoAlTavoloFragment.getContext(), error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ritornoInformazioniDelTavoloListener() {
        setVisibleLoading(0);
        tavoloDao.ritornoInformazioniDelTavolo(Long.valueOf(idTavolo), new Callback() {
            @Override
            public void onSuccess(Object object) {
                setVisibleLoading(8);
                tavoloOrdinaElemento = (List<TavoloOrdinaElemento>) object;
                for (TavoloOrdinaElemento tavolo : tavoloOrdinaElemento) {
                    DtoElementi dtoElementi = new DtoElementi();
                    dtoElementi.setElementoOrdinata(tavolo.getElementoOrdinate());
                    dtoElementi.setQuantita(tavolo.getQuantita());
                    dtoElementi.setCosto(tavolo.getElementoOrdinate().getPrezzo());
                    ElementoList.add(dtoElementi);
                }
                Log.i(TAG, "onSuccess: Elementos: " + ElementoList);
                myAdpter =
                        new myAdapterAggiungiElementoAlTavolo(aggiungiElementoAlTavoloFragment.getActivity(),
                                new ArrayList<>(ElementoList)
                                , aggiungiElementoAlTavoloFragment);
                LinearLayoutManager manager = new LinearLayoutManager(aggiungiElementoAlTavoloFragment.getContext());
                recyclerViewElementoAlTavolo.setLayoutManager(manager);
                recyclerViewElementoAlTavolo.setAdapter(myAdpter);

                gestioneTavoloN.setText(MessageFormat.format("Gestione Tavolo: {0}",
                        tavoloOrdinaElemento.get(0).getTavolo().getNumeroTavolo()));


            }

            @Override
            public void onFailure(Throwable error) {
                Log.e(TAG, "onFailure: ", error);
                if (aggiungiElementoAlTavoloFragment.getContext() != null) {
                    setVisibleLoading(8);
                    Toast.makeText(aggiungiElementoAlTavoloFragment.getContext(), error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean esisteLElemento(DtoElementi elementoDaCercare) {
        for (DtoElementi dtoElementi : ElementoList) {
            if (dtoElementi.getElementoOrdinata().getNomeElemento().equalsIgnoreCase(elementoDaCercare.getElementoOrdinata().getNomeElemento())) {
                int quantita = dtoElementi.getQuantita();
                quantita++;
                dtoElementi.setQuantita(quantita);
                return true;
            }
        }
        return false;
    }


    private void buttonAggiornaTavoloListener() {
        buttonAggiornaTavolo.setOnClickListener(view -> {
            Tavolo tavolo = new Tavolo();
            tavolo.setIdTavolo(Long.valueOf(idTavolo));
            tavolo.setNumeroTavolo(tavoloOrdinaElemento.get(0).getTavolo().getNumeroTavolo());
            //  ElementoList=myAdpter.getArrayList();
            if (myAdpter.getArrayList() != null &&
                    !myAdpter.getArrayList().stream().allMatch(dtoElemento -> dtoElemento.getQuantita() == 0)) {
                DtoTavolo DTOTavolo = new DtoTavolo(ElementoList, tavolo);
                Log.i(TAG, "ElementoList: " + ElementoList);
                setVisibleLoading(0);
                setEnableButton(false);
                tavoloDao.aggiornaElementoAlTavolo(DTOTavolo, new Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        if (aggiungiElementoAlTavoloFragment.getContext() != null) {
                            setEnableButton(true);
                            setVisibleLoading(8);
                            Toast.makeText(aggiungiElementoAlTavoloFragment.getContext(),
                                    object.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        if (aggiungiElementoAlTavoloFragment.getContext() != null) {
                            setEnableButton(true);
                            setVisibleLoading(8);
                            Toast.makeText(aggiungiElementoAlTavoloFragment.getContext(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                if (aggiungiElementoAlTavoloFragment.getContext() != null)
                    Toast.makeText(aggiungiElementoAlTavoloFragment.getContext(),
                            "Il tavolo deve contenere almeno una Elemento", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorAggiungiElementoAlTavolo.setVisibility(visibleLoading);
        textLoadingCirculatorAggiungiElementoAlTavolo.setVisibility(visibleLoading);
    }

    private void setEnableButton(boolean enable) {
        buttonAggiornaTavolo.setEnabled(enable);
        backPulsanteArrowAggiungiElementoAlTavolo.setEnabled(enable);
    }
}
