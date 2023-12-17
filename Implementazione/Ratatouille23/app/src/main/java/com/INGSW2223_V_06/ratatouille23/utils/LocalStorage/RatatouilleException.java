package com.INGSW2223_V_06.ratatouille23.utils.LocalStorage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RatatouilleException implements Serializable {
    @Expose
    @SerializedName("message")
    private final String message;
    private final Throwable throwable;


    public RatatouilleException(String message, Throwable throwable) {
        this.message = message;
        this.throwable = throwable;

    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }


}
