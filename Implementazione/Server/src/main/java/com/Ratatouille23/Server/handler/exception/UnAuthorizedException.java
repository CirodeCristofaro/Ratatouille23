package com.Ratatouille23.Server.handler.exception;

public class UnAuthorizedException extends  RatatouilleCustomerException{
    public UnAuthorizedException(String message) {
        super(message);

    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
