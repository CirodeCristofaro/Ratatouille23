package com.Ratatouille23.Server.handler.exception;

public class BadRequestException extends  RatatouilleCustomerException{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
