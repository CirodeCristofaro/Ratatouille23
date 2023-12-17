package com.INGSW2223_V_06.ratatouille23.retrofit;


import java.io.Serializable;

public class ApiSuccess implements Serializable {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiSuccess(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiSuccess{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
