package com.Ratatouille23.Server.handler.exception;

public class RatatouilleCustomerException extends  Exception {

    public RatatouilleCustomerException(String message) {
        super(message);
    }

    public RatatouilleCustomerException(String message, Throwable cause) {
        super(message, cause);
    }


}
