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
import com.INGSW2223_V_06.ratatouille23.presenter.CreaElementoPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class CreaElementoFragment extends Fragment {
    private final String TAG = "CreaElementoFragment";
    private ImageButton backPulsanteArrowCreaElemento;
    private AutoCompleteTextView nomeElemento;
    private TextInputEditText descrizioneElemento;
    private TextInputEditText allergeniciElemento;
    private TextInputEditText prezzoElemento;
    private TextInputLayout layoutAllergeniciNuovoElemento;
    private MaterialButton buttonCreaNuovoElemento;
    private TextView sottoTitoloCategoriaNuovoElemento;
    private ChipGroup chipAllergeniciNuovoElemento;

    private NavigationView navigationView;
    private String categoria;
    private Button buttonLogout;
    private CircularProgressIndicator loadingCirculatorCreazioneElemento;
    private TextView textLoadingCirculatorCreazioneElemento;

    public CreaElementoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        backPulsanteArrowCreaElemento =
                view.findViewById(R.id.backPulsanteArrowCreaElemento);
        nomeElemento =
                view.findViewById(R.id.autoCompleteTextViewCreazioneElemento);
        descrizioneElemento = view.findViewById(R.id.descrizioneNuovoElemento);
        allergeniciElemento =
                view.findViewById(R.id.allergeniciCreazioneElemento);
        prezzoElemento = view.findViewById(R.id.prezzoNuovoElemento);
        layoutAllergeniciNuovoElemento =
                view.findViewById(R.id.layoutAllergeniciNuovoElemento);
        buttonCreaNuovoElemento = view.findViewById(R.id.buttonCreaNuovoElemento);
        sottoTitoloCategoriaNuovoElemento =
                view.findViewById(R.id.sottoTitoloCategoriaNuovoElemento);
        loadingCirculatorCreazioneElemento = view.findViewById(R.id.loadingCirculatorCreazioneElemento);
        textLoadingCirculatorCreazioneElemento = view.findViewById(R.id.textLoadingCirculatorCreazioneElemento);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        categoria = bundle.getString("categoria");
        chipAllergeniciNuovoElemento = view.findViewById(R.id.chipAllergeniciNuovoElemento);
        navigationView = this.getActivity().findViewById(R.id.nav);
        buttonLogout = this.getActivity().findViewById(R.id.logout);
        new CreaElementoPresenter(this, bundle.get("categoria").toString());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crea_elemento, container, false);
    }

    public ImageButton getBackPulsanteArrowCreaElemento() {
        return backPulsanteArrowCreaElemento;
    }

    public AutoCompleteTextView getNomeElemento() {
        return nomeElemento;
    }

    public TextInputEditText getDescrizioneElemento() {
        return descrizioneElemento;
    }

    public TextInputEditText getAllergeniciElemento() {
        return allergeniciElemento;
    }

    public TextInputEditText getPrezzoElemento() {
        return prezzoElemento;
    }

    public TextInputLayout getLayoutAllergeniciNuovoElemento() {
        return layoutAllergeniciNuovoElemento;
    }

    public MaterialButton getButtonCreaNuovoElemento() {
        return buttonCreaNuovoElemento;
    }

    public TextView getSottoTitoloCategoriaNuovoElemento() {
        return sottoTitoloCategoriaNuovoElemento;
    }

    public ChipGroup getChipAllergeniciNuovoElemento() {
        return chipAllergeniciNuovoElemento;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Button getButtonLogout() {
        return buttonLogout;
    }

    public String getCategoria() {
        return categoria;
    }

    public CircularProgressIndicator getLoadingCirculatorCreazioneElemento() {
        return loadingCirculatorCreazioneElemento;
    }

    public TextView getTextLoadingCirculatorCreazioneElemento() {
        return textLoadingCirculatorCreazioneElemento;
    }
}