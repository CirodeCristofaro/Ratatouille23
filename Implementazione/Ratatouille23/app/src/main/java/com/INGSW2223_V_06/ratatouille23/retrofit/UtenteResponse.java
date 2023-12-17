package com.INGSW2223_V_06.ratatouille23.retrofit;

import com.google.gson.annotations.SerializedName;

public class UtenteResponse {

    @SerializedName("accessToken")
    private String accessToken;

    public UtenteResponse() {
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
