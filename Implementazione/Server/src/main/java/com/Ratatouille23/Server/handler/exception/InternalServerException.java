package com.Ratatouille23.Server.handler.exception;

public class InternalServerException extends  RatatouilleCustomerException{
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
