package com.learning.shopdevjava.exception;

public class UpdateException extends CommonRuntimeException{
    public UpdateException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public UpdateException(ErrorObject errorObject){
        super(errorObject);
    }
}
