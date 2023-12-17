package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.CreaCategoriePresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class CreaCategoriaFragment extends Fragment {
    private final String TAG = "CreaCategoriaFragment";
    private AutoCompleteTextView nomeElemento;

    private TextInputEditText descrizioneElemento;
    private TextInputEditText allergeniciCreazioneCategoria;
    private TextInputEditText nomeCategoria;
    private TextInputEditText prezzo;
    private TextInputLayout layoutIngredientiLayout;
    private TextInputLayout layoutAllergeniciLayout;
    private TextInputLayout layoutPrezzo;

    private MaterialButton buttonCreaCategoria;
    private ImageButton BackPulsanteArrowGestioneCategorie;
    private ChipGroup chipAllergenici;
    private NavigationView navigationView;
    private Button buttonLogout;
    private CircularProgressIndicator loadingCirculatorCreaCategoria;
    private TextView textLoadingCirculatorCreaCategoria;

    public CreaCategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        nomeElemento = view.findViewById(R.id.autoCompleteTextView);
        descrizioneElemento = view.findViewById(R.id.descrizioneElemento);
        allergeniciCreazioneCategoria = view.findViewById(R.id.allergeniciCreazioneCategoria);
        layoutAllergeniciLayout = view.findViewById(R.id.layoutAllergenici);
        layoutIngredientiLayout = view.findViewById(R.id.layoutDescrizione);
        buttonCreaCategoria = view.findViewById(R.id.buttonCreaCategoria);
        nomeCategoria = view.findViewById(R.id.nomeCreazioneCategoria);
        BackPulsanteArrowGestioneCategorie = view.findViewById(R.id.backPulsanteArrowGestioneCategorie);
        chipAllergenici = view.findViewById(R.id.chipAllergenici);
        layoutPrezzo = view.findViewById(R.id.layoutPrezzo);
        prezzo = view.findViewById(R.id.prezzo);
        textLoadingCirculatorCreaCategoria =
                view.findViewById(R.id.textLoadingCirculatorCreaCategoria);
        loadingCirculatorCreaCategoria = view.findViewById(R.id.loadingCirculatorCreaCategoria);
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);


        allergeniciCreazioneCategoria = view.findViewById(R.id.allergeniciCreazioneCategoria);
        new CreaCategoriePresenter(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crea_categorie, container, false);
    }


    public TextInputEditText getNomeCategoria() {
        return nomeCategoria;
    }

    public ImageButton getBackPulsanteArrowGestioneCategorie() {
        return BackPulsanteArrowGestioneCategorie;
    }

    public AutoCompleteTextView getNomeElemento() {
        return nomeElemento;
    }

    public TextInputEditText getDescrizioneElemento() {
        return descrizioneElemento;
    }


    public TextInputEditText getAllergeniciCreazioneCategoria() {
        return allergeniciCreazioneCategoria;
    }


    public TextInputLayout getLayoutIngredientiLayout() {
        return layoutIngredientiLayout;
    }


    public TextInputLayout getLayoutAllergeniciLayout() {
        return layoutAllergeniciLayout;
    }


    public MaterialButton getButtonCreaCategoria() {
        return buttonCreaCategoria;
    }

    public ChipGroup getChipAllergenici() {
        return chipAllergenici;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public TextInputEditText getPrezzo() {
        return prezzo;
    }

    public TextInputLayout getLayoutPrezzo() {
        return layoutPrezzo;
    }

    public CircularProgressIndicator getLoadingCirculatorCreaCategoria() {
        return loadingCirculatorCreaCategoria;
    }

    public TextView getTextLoadingCirculatorCreaCategoria() {
        return textLoadingCirculatorCreaCategoria;
    }

    @Override
    public String toString() {
        return "CreaCategoriaFragment";
    }
}