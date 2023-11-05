package com.learning.shopdevjava.exception;

public class BadRequestException extends CommonRuntimeException{
    public BadRequestException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public BadRequestException(ErrorObject errorObject){
        super(errorObject);
    }
}
