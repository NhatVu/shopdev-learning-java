package com.learning.shopdevjava.exception;

public class InvalidAPIKeyException extends CommonRuntimeException{
    public InvalidAPIKeyException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public InvalidAPIKeyException(ErrorObject errorObject){
        super(errorObject);
    }
}
