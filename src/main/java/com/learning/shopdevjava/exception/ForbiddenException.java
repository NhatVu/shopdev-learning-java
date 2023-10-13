package com.learning.shopdevjava.exception;

public class ForbiddenException extends CommonRuntimeException{
    public ForbiddenException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public ForbiddenException(ErrorObject errorObject){
        super(errorObject);
    }
}
