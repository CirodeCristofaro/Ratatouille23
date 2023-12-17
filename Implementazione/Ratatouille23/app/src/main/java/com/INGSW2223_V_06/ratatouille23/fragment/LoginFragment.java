package com.INGSW2223_V_06.ratatouille23.fragment;

import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.deleteMessageTokenScaduto;
import static com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.UtenteLocale.ritornoMessageTokenScaduto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.INGSW2223_V_06.ratatouille23.MainActivity;
import com.INGSW2223_V_06.ratatouille23.R;
import com.INGSW2223_V_06.ratatouille23.presenter.LoginPresenter;
import com.INGSW2223_V_06.ratatouille23.retrofit.dao.UtenteDao;
import com.INGSW2223_V_06.ratatouille23.utils.LocalStorage.TokenManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private final String TAG = "LoginFragment";

    private TextInputEditText email;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText password;
    private MaterialButton loginbutton;
    private FrameLayout frameLayout;
    private TextView textLoadingCirculatorLogin;

    private CircularProgressIndicator loadingCirculatorLogin;

    private Toolbar toolbar;

    private UtenteDao utenteDao;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        email.setText(null);
        password.setText(null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).fragmentAttule = TAG;
        frameLayout = view.findViewById(R.id.frameLayoutActivityMain);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        loginbutton = getView().findViewById(R.id.buttonLogin);
        emailLayout = getView().findViewById(R.id.layoutEmail);
        toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        passwordLayout = getView().findViewById(R.id.passwordLayout);
        textLoadingCirculatorLogin = view.findViewById(R.id.textLoadingCirculatorLogin);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        utenteDao = new UtenteDao(getContext());
        loadingCirculatorLogin = view.findViewById(R.id.loadingCirculatorLogin);
        if (!Objects.isNull(ritornoMessageTokenScaduto(this.getContext()))
                && !ritornoMessageTokenScaduto(this.getContext()).isEmpty()
                && getContext() != null) {
            Toast.makeText(getContext(), ritornoMessageTokenScaduto(this.getContext()), Toast.LENGTH_LONG).show();
            deleteMessageTokenScaduto(this.getContext());
            TokenManager.deleteToken();
        }
        new LoginPresenter(this);


    }

    public TextView getTextLoadingCirculatorLogin() {
        return textLoadingCirculatorLogin;
    }

    public TextInputEditText getEmail() {
        return email;
    }

    public TextInputEditText getPassword() {
        return password;
    }


    public TextInputLayout getEmailLayout() {
        return emailLayout;
    }

    public MaterialButton getLoginbutton() {
        return loginbutton;
    }

    public CircularProgressIndicator getLoadingCirculatorLogin() {
        return loadingCirculatorLogin;
    }

    @Override
    public String toString() {
        return "LoginFragment";
    }
}