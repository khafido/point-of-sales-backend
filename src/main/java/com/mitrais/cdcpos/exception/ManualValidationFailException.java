package com.mitrais.cdcpos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ManualValidationFailException extends Exception{

    public ManualValidationFailException(String message){
        super(message);
    }
}
