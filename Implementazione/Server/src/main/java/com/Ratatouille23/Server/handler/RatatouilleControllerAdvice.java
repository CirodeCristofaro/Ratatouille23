package com.Ratatouille23.Server.handler;

import com.Ratatouille23.Server.handler.entity.RatatouilleException;
import com.Ratatouille23.Server.handler.exception.BadRequestException;
import com.Ratatouille23.Server.handler.exception.InternalServerException;
import com.Ratatouille23.Server.handler.exception.NotFoundException;
import com.Ratatouille23.Server.handler.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RatatouilleControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> ratatouilleNotFoundExceptio(NotFoundException notFoundException){
        RatatouilleException ratatouilleException = new RatatouilleException(notFoundException.getMessage(), notFoundException.getCause(), HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(ratatouilleException,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(value = {InternalServerException.class})
    public ResponseEntity<Object> ratatouilleInternalServerExceptio(InternalServerException internalServerException){
        RatatouilleException ratatouilleException = new RatatouilleException(internalServerException.getMessage(), internalServerException.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        return  new ResponseEntity<>(ratatouilleException,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> ratatouilleBadRequestException(BadRequestException badRequestException){
        RatatouilleException ratatouilleException = new RatatouilleException(badRequestException.getMessage(), badRequestException.getCause(), HttpStatus.BAD_REQUEST);
        return  new ResponseEntity<>(ratatouilleException,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {UnAuthorizedException.class})
    public ResponseEntity<Object> ratatouilleUnAuthorizedException(UnAuthorizedException badRequestException){
        RatatouilleException ratatouilleException = new RatatouilleException(badRequestException.getMessage(), badRequestException.getCause(), HttpStatus.BAD_REQUEST);
        return  new ResponseEntity<>(ratatouilleException,HttpStatus.UNAUTHORIZED);
    }

}
