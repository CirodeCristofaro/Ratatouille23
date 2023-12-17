package com.INGSW2223_V_06.ratatouille23.presenter;

import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.LogoutButtonAsscoltoClick;
import static com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils.NavMenuUtils.NavigationViewAscoltoCLick;

import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.INGSW2223_V_06.ratatouille23.fragment.CreaCategoriaFragment;
import com.INGSW2223_V_06.ratatouille23.fragment.GestioneMenuFragment;
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

public class CreaCategoriePresenter {
    private final String TAG = "CreaCategoriePresenter";
    private final CreaCategoriaFragment creaCategoriaFragment;
    private AutoCompleteTextView nomeElementoText;
    private TextInputEditText descrizioneElementoText;
    private TextInputEditText allergeniciCreazioneCategoria;
    private TextInputEditText nomeCategoriaText;
    private TextInputEditText prezzoText;
    private TextInputLayout layoutIngredientiLayout;
    private TextInputLayout layoutAllergeniciLayout;
    private TextInputLayout layoutPrezzo;
    private MaterialButton buttonCreaCategoria;
    private ImageButton BackPulsanteArrowGestioneCategorie;

    private MenuDao menuDao;

    private List<ProductOpenFood> ElementoTrovate;
    private ChipGroup chipAllergenici;

    private NavigationView navigationView;
    private Button buttonLogout;

    private CircularProgressIndicator loadingCirculatorCreaCategoria;


    private TextView textLoadingCirculatorCreaCategoria;

    public CreaCategoriePresenter(CreaCategoriaFragment creaCategoriaFragment) {
        this.creaCategoriaFragment = creaCategoriaFragment;
        inizializza();
    }

    private void inizializza() {
        nomeElementoText = creaCategoriaFragment.getNomeElemento();
        descrizioneElementoText = creaCategoriaFragment.getDescrizioneElemento();
        allergeniciCreazioneCategoria = creaCategoriaFragment.getAllergeniciCreazioneCategoria();
        layoutAllergeniciLayout = creaCategoriaFragment.getLayoutAllergeniciLayout();
        layoutIngredientiLayout = creaCategoriaFragment.getLayoutIngredientiLayout();
        buttonCreaCategoria = creaCategoriaFragment.getButtonCreaCategoria();
        BackPulsanteArrowGestioneCategorie = creaCategoriaFragment.getBackPulsanteArrowGestioneCategorie();
        nomeCategoriaText = creaCategoriaFragment.getNomeCategoria();
        chipAllergenici = creaCategoriaFragment.getChipAllergenici();
        textLoadingCirculatorCreaCategoria =
                creaCategoriaFragment.getTextLoadingCirculatorCreaCategoria();
        navigationView = creaCategoriaFragment.getNavigationView();
        buttonLogout = creaCategoriaFragment.getButtonLogout();
        loadingCirculatorCreaCategoria = creaCategoriaFragment.getLoadingCirculatorCreaCategoria();
        prezzoText = creaCategoriaFragment.getPrezzo();
        layoutPrezzo = creaCategoriaFragment.getLayoutPrezzo();
        menuDao = new MenuDao(creaCategoriaFragment.getContext());
        NavigationViewAscoltoCLick(navigationView, creaCategoriaFragment);
        LogoutButtonAsscoltoClick(buttonLogout, creaCategoriaFragment);

        prezzoText.setFilters(new InputFilter[]{new FiltroInputBigDecimalPrezzo()});
        buttonCreaCategoriaListener();
        autoCompleteTextViewListener();
        layoutAllergeniciLayoutListener();

        BackPulsanteArrowGestioneCategorie.setOnClickListener(view1 -> {
            FragmentUtils.changeFragment(new GestioneMenuFragment(), creaCategoriaFragment);
        });
    }

    private void layoutAllergeniciLayoutListener() {
        layoutAllergeniciLayout.setEndIconOnClickListener(view -> {
            Log.i(TAG, "onClick: icon");
            if (!allergeniciCreazioneCategoria.getText().toString().trim().isEmpty()) {
                if (esisteChipAllergenico(allergeniciCreazioneCategoria.getText().toString())) {
                    creaChip(allergeniciCreazioneCategoria.getText().toString());
                    allergeniciCreazioneCategoria.setText(null);
                }
            }
        });
    }


    private void autoCompleteTextViewListener() {
        nomeElementoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) { //ha cliccato il tasto search della tastiera
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
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(creaCategoriaFragment.getContext(),
                                            android.R.layout.simple_dropdown_item_1line, nomeElemento);

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

        //aggiunge i cheap presi dall api
        nomeElementoText.setOnItemClickListener((adapterView, view, i, l) -> {
            chipAllergenici.removeAllViews();
            Log.i(TAG, "item cliccato " + adapterView.getItemAtPosition(i).toString());
            for (ProductOpenFood port : ElementoTrovate) {
                if (port.getProduct_name().equalsIgnoreCase(adapterView.getItemAtPosition(i).toString())) {
                    descrizioneElementoText.setText(port.getIngredients_text());
                    for (String prodotto : port.getAllergens_hierarchy()) {
                        if (esisteChipAllergenico(prodotto.substring(3).trim())) {
                            creaChip(prodotto.substring(3).trim());

                        }
                    }
                }
            }
        });
    }

    private void buttonCreaCategoriaListener() {
        buttonCreaCategoria.setOnClickListener(view -> {
            String nomeCategoria = nomeCategoriaText.getText().toString().trim();
            String nomeElemento = nomeElementoText.getText().toString();
            String descrizioneElemento = descrizioneElementoText.getText().toString();
            String prezzo = prezzoText.getText().toString();
            if (!nomeCategoria.isEmpty() && !nomeElemento.trim().isEmpty() &&
                    !descrizioneElemento.trim().isEmpty() && !prezzo.trim().isEmpty()) {
                BigDecimal prezzoFinale = new BigDecimal(prezzo);
                if (prezzoFinale.compareTo(BigDecimal.ZERO) > 0) {
                    setVisibleLoading(0);
                    setEnableButton(false);
                    Elementi Elemento = new Elementi(nomeElemento, prezzoFinale,
                            descrizioneElemento);
                    menuDao.creaCategoriaConElemento(nomeCategoria,
                            Elemento, getAllergeniciChip(), new Callback() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (creaCategoriaFragment.getContext() != null) {
                                        setVisibleLoading(8);
                                        setEnableButton(true);
                                        Toast.makeText(creaCategoriaFragment.getContext(), object.toString(), Toast.LENGTH_SHORT).show();
                                        nomeCategoriaText.setText(null);
                                        nomeElementoText.setText(null);
                                        descrizioneElementoText.setText(null);
                                        prezzoText.setText(null);
                                        chipAllergenici.removeAllViews();
                                    }
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                    if (creaCategoriaFragment.getContext() != null) {
                                        Toast.makeText(creaCategoriaFragment.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                        setVisibleLoading(8);
                                        setEnableButton(true);

                                    }
                                }
                            });
                } else {
                    if (creaCategoriaFragment.getContext() != null)
                        Toast.makeText(creaCategoriaFragment.getContext(), "Il prezzo deve essere maggiore di zero",
                                Toast.LENGTH_SHORT).show();
                }

            } else {
                if (creaCategoriaFragment.getContext() != null)
                    Toast.makeText(creaCategoriaFragment.getContext(), "Uno o più campi non sono stati definiti",
                            Toast.LENGTH_SHORT).show();
            }

        });
    }


    //crea i chip allergenici
    private void creaChip(String allergenico) {

        Chip chip = new Chip(creaCategoriaFragment.getContext());
        chip.setText(allergenico);
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setOnCloseIconClickListener(view -> {
            String text = ((Chip) view).getText().toString();
            Log.i(TAG, "onClick: layoutIngredienti " + text);
            //rimuove il chip dal chipgroup
            chipAllergenici.removeView(view);
        });
        //aggiunge l allergenico al chipgroup
        chipAllergenici.addView(chip);

    }


    //ritorna tutta la lista degli allergenici
    private List<String> getAllergeniciChip() {
        List<String> allergenici = new ArrayList<>();
        for (int i = 0; i < chipAllergenici.getChildCount(); i++) {
            allergenici.add(((Chip) chipAllergenici.getChildAt(i)).getText().toString());
            Log.i(TAG,
                    "onClick: per " + ((Chip) chipAllergenici.getChildAt(i)).getText().toString());
        }
        return allergenici;
    }


    private boolean esisteChipAllergenico(String allergenico) {
        return !getAllergeniciChip().contains(allergenico);

    }

    private void setVisibleLoading(int visibleLoading) {
        loadingCirculatorCreaCategoria.setVisibility(visibleLoading);
        textLoadingCirculatorCreaCategoria.setVisibility(visibleLoading);
    }

    private void setEnableButton(boolean enableButton) {
        buttonCreaCategoria.setEnabled(enableButton);
        BackPulsanteArrowGestioneCategorie.setEnabled(enableButton);
    }

}
