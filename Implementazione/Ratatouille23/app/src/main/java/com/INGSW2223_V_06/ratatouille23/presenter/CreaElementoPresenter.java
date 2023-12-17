package com.INGSW2223_V_06.ratatouille23.presenter;


import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.INGSW2223_V_06.ratatouille23.fragment.CreaElementoFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.model.Elementi;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.MenuDao;
import com.INGSW2223_V_06.ratatouille23.retrofit.interfacce.Callback;
import com.INGSW2223_V_06.ratatouille23.utils.DTO.ProductOpenFood;
import com.INGSW2223_V_06.ratatouille23.utils.FiltroInputBigDecimalPrezzo;
import com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreaElementoPresenter {
    private final String TAG = "CreaElementoPresenter";
    private final CreaElementoFragment creaElementoFragment;
    private ImageButton backPulsanteArrowCreaElemento;
    private AutoCompleteTextView nomeElementoText;
    private TextInputEditText descrizioneElementoText;
    private TextInputEditText allergeniciCreazioneElemento;
    private TextInputEditText prezzoElementoText;
    private TextInputLayout layoutAllergeniciNuovaElemento;
    private MaterialButton buttonCreaNuovaElemento;
    private TextView sottoTitoloCategoriaNuovaElemento;
    private String categoria;
    private MenuDao menuDao;
    private List<ProductOpenFood> ElementoTrovate;

    private ChipGroup chipAllergeniciNuovaElemento;
    private NavigationView navigationView;
    private Button buttonLogout;

    private CircularProgressIndicator loadingCirculatorCreazioneElemento;

    private TextView textLoadingCirculatorCreazioneElemento;


    public CreaElementoPresenter(CreaElementoFragment creaElementoFragment, String categoria) {
        this.creaElementoFragment = creaElementoFragment;
        this.categoria = categoria;

        inizializza();
    }

    private void inizializza() {
        backPulsanteArrowCreaElemento = creaElementoFragment.getBackPulsanteArrowCreaElemento();
        nomeElementoText = creaElementoFragment.getNomeElemento();
        descrizioneElementoText = creaElementoFragment.getDescrizioneElemento();
        allergeniciCreazioneElemento = creaElementoFragment.getAllergeniciElemento();
        prezzoElementoText = creaElementoFragment.getPrezzoElemento();
        layoutAllergeniciNuovaElemento = creaElementoFragment.getLayoutAllergeniciNuovoElemento();
        buttonCreaNuovaElemento = creaElementoFragment.getButtonCreaNuovoElemento();
        sottoTitoloCategoriaNuovaElemento = creaElementoFragment.getSottoTitoloCategoriaNuovoElemento();
        sottoTitoloCategoriaNuovaElemento.setText(String.format("per la categoria: %s",
                categoria.toUpperCase()));
        chipAllergeniciNuovaElemento = creaElementoFragment.getChipAllergeniciNuovoElemento();
        prezzoElementoText.setFilters(new InputFilter[]{new FiltroInputBigDecimalPrezzo()});
        loadingCirculatorCreazioneElemento =
                creaElementoFragment.getLoadingCirculatorCreazioneElemento();
        textLoadingCirculatorCreazioneElemento =
                creaElementoFragment.getTextLoadingCirculatorCreazioneElemento();
        buttonBack();
        autocompleteJson();
        chipManuale();
        buttonAggiungiElemento();
        menuDao = new MenuDao(creaElementoFragment.getContext());
        navigationView = creaElementoFragment.getNavigationView();
        buttonLogout = creaElementoFragment.getButtonLogout();
        NavigationViewAscoltoCLick(navigationView, creaElementoFragment);
        LogoutButtonAsscoltoClick(buttonLogout, creaElementoFragment);
    }

    private void buttonAggiungiElemento() {
        buttonCreaNuovaElemento.setOnClickListener(view -> {
            String nomeElemento = nomeElementoText.getText().toString();
            String descrizioneElemento = descrizioneElementoText.getText().toString();
            String prezzoElemento = prezzoElementoText.getText().toString();
            if (!nomeElemento.isEmpty()
                    && !descrizioneElemento.isEmpty()
                    && !prezzoElemento.isEmpty()) {
                BigDecimal prezzoFinale = new BigDecimal(prezzoElemento);
                if (prezzoFinale.compareTo(BigDecimal.ZERO) > 0) {
                    setVisibleLoading(0);
                    setEnableButton(false);
                    Elementi elementi = new Elementi(nomeElemento, prezzoFinale, descrizioneElemento);
                    List<String> allergenici=getAllergeniciChip();
                    menuDao.aggiungiElemento(elementi, categoria
                            , allergenici,
                            new Callback() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (creaElementoFragment.getContext() != null) {
                                        setVisibleLoading(8);
                                        setEnableButton(true);
                                        Toast.makeText(creaElementoFragment.getContext(), object.toString(), Toast.LENGTH_SHORT).show();
                                        nomeElementoText.setText(null);
                                        descrizioneElementoText.setText(null);
                                        prezzoElementoText.setText(null);
                                        chipAllergeniciNuovaElemento.removeAllViews();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                    if (creaElementoFragment.getContext() != null) {
                                        setVisibleLoading(8);
                                        setEnableButton(true);
                                        Toast.makeText(creaElementoFragment.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    if (creaElementoFragment.getContext() != null)
                        Toast.makeText(creaElementoFragment.getContext(), "Il prezzo deve essere maggiore di zero", Toast.LENGTH_LONG).show();
                }
            } else {
                if (creaElementoFragment.getContext() != null)
                    Toast.makeText(creaElementoFragment.getContext(), "Uno o più campi della Elemento non sono stati definitii",
                            Toast.LENGTH_LONG).show();
            }

        });


    }

    private void chipManuale() {
        //crea i cheap se viene aggiunto manualmente
        layoutAllergeniciNuovaElemento.setEndIconOnClickListener(view -> {
            Log.i(TAG, "onClick: icon");
            if (!allergeniciCreazioneElemento.getText().toString().trim().isEmpty()) {
                if (!esisteChipAllergenico(allergeniciCreazioneElemento.getText().toString())) {
                    creaChip(allergeniciCreazioneElemento.getText().toString());
                    allergeniciCreazioneElemento.setText(null);
                    chipAllergeniciNuovaElemento.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void autocompleteJson() {
        nomeElementoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//ha cliccato il tasto search della tastiera
                    Log.i(TAG, "onEditorAction: " + textView.getText().toString());
                    String completedText = textView.getText().toString();
                    menuDao.autocomplete(completedText, new Callback() {
                        @Override
                        public void onSuccess(Object object) {
                            ElementoTrovate = (List<ProductOpenFood>) object;
                            if (!Objects.isNull(ElementoTrovate) && !ElementoTrovate.isEmpty()) {
                                Log.i(TAG, "Elemento " + ElementoTrovate.toString());
                                List<String> nomeElemento = ElementoTrovate.stream().map(ProductOpenFood::getProduct_name).collect(Collectors.toList());
                                try {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(creaElementoFragment.getContext(),
                                            android.R.layout.simple_dropdown_item_1line, nomeElemento);
                                    Log.i(TAG, "if di fragmentRef ");
                                    nomeElementoText.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    nomeElementoText.showDropDown();
                                } catch (RuntimeException e) {
                                    Log.e(TAG, "RuntimeException: ", e);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Throwable error) {

                        }
                    });
                    return true;
                }
                return false;
            }
        });

        nomeElementoText.setOnItemClickListener((adapterView, view, i, l) -> {
            chipAllergeniciNuovaElemento.removeAllViews();
            Log.i(TAG, "item cliccato " + adapterView.getItemAtPosition(i).toString());
            for (ProductOpenFood port : ElementoTrovate) {
                if (port.getProduct_name().equalsIgnoreCase(adapterView.getItemAtPosition(i).toString())) {
                    descrizioneElementoText.setText(port.getIngredients_text());
                    for (String prodotto : port.getAllergens_hierarchy()) {
                        if (!esisteChipAllergenico(prodotto.substring(3).trim())) {
                            creaChip(prodotto.substring(3).trim());
                            chipAllergeniciNuovaElemento.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    //crea i chip allergenici
    private void creaChip(String allergenico) {

        Chip chip = new Chip(creaElementoFragment.getContext());
        chip.setText(allergenico);
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setOnCloseIconClickListener(view -> {
            String text = ((Chip) view).getText().toString();
            Log.i(TAG, "onClick: layoutIngredienti " + text);
            //rimuove il chip dal chipgroup
            chipAllergeniciNuovaElemento.removeView(view);
        });
        //aggiunge l allergenico al chipgroup
        chipAllergeniciNuovaElemento.addView(chip);

    }


    //ritorna tutta la lista degli allergenici
    private List<String> getAllergeniciChip() {
        List<String> allergenici = new ArrayList<String>();
        for (int i = 0; i < chipAllergeniciNuovaElemento.getChildCount(); i++) {
            allergenici.add(((Chip) chipAllergeniciNuovaElemento.getChildAt(i)).getText().toString());
            Log.i(TAG, "onClick: for " + ((Chip) chipAllergeniciNuovaElemento.getChildAt(i)).getText().toString());
        }
        return allergenici;
    }

    private boolean esisteChipAllergenico(String allergenico) {
        return getAllergeniciChip().contains(allergenico);

    }

    private void buttonBack() {
        backPulsanteArrowCreaElemento.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("categoria", categoria);
            GestioneCategoriaFragment gestioneCategoriaFragment = new GestioneCategoriaFragment();
            gestioneCategoriaFragment.setArguments(bundle);
            FragmentUtils.changeFragment(gestioneCategoriaFragment, creaElementoFragment);
        });
    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorCreazioneElemento.setVisibility(visibleLoading);
        textLoadingCirculatorCreazioneElemento.setVisibility(visibleLoading);
    }

    private void setEnableButton(boolean enableButton) {
        buttonCreaNuovaElemento.setEnabled(enableButton);
        backPulsanteArrowCreaElemento.setEnabled(enableButton);
    }
}
