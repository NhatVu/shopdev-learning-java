package com.learning.shopdevjava.exception;

public class CreationException extends CommonRuntimeException{
    public CreationException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public CreationException(ErrorObject errorObject){
        super(errorObject);
    }
}
