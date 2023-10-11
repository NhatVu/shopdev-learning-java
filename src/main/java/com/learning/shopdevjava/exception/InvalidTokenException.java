package com.learning.shopdevjava.exception;

public class InvalidTokenException extends CommonRuntimeException{
    public InvalidTokenException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public InvalidTokenException(ErrorObject errorObject){
        super(errorObject);
    }
}
