package com.INGSW2223_V_06.ratatouille23.retrofit;

public class UtenteRichiesta {
    private String email;
    private String password;


    public UtenteRichiesta(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
