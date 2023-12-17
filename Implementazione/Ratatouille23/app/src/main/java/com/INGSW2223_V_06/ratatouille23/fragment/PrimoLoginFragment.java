package com.INGSW2223_V_06.ratatouille23.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.model.Utente;
import com.INGSW2223_V_06.ratatouille23.presenter.PrimoLoginPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;


public class PrimoLoginFragment extends Fragment {
    private final String TAG = "PrimoLoginFragment";
    private TextInputEditText password;
    private TextInputEditText repassword;
    private MaterialButton cambiabutton;
    private TextView textLoadingCirculatoCambiaPasswordPrimoLogin;

    private Utente utenteBundle;

    private CircularProgressIndicator loadingCirculatorCambiaPasswordPrimoLogin;

    public PrimoLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        password = view.findViewById(R.id.password);
        repassword = view.findViewById(R.id.repassword);
        cambiabutton = view.findViewById(R.id.buttonCambia);
        loadingCirculatorCambiaPasswordPrimoLogin =
                view.findViewById(R.id.loadingCirculatorCambiaPasswordPrimoLogin);
        textLoadingCirculatoCambiaPasswordPrimoLogin =
                view.findViewById(R.id.textLoadingCirculatoCambiaPasswordPrimoLogin);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        utenteBundle = (Utente) bundle.getSerializable("utente");
        new PrimoLoginPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_primo_login, container, false);
    }

    public TextInputEditText getPassword() {
        return password;
    }


    public TextInputEditText getRepassword() {
        return repassword;
    }


    public MaterialButton getCambiabutton() {
        return cambiabutton;
    }


    public Utente getUtenteBundle() {
        return utenteBundle;
    }

    public CircularProgressIndicator getLoadingCirculatorCambiaPasswordPrimoLogin() {
        return loadingCirculatorCambiaPasswordPrimoLogin;
    }

    public TextView getTextLoadingCirculatoCambiaPasswordPrimoLogin() {
        return textLoadingCirculatoCambiaPasswordPrimoLogin;
    }

    @Override
    public String toString() {
        return "PrimoLoginFragment";
    }
}