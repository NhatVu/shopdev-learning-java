package com.learning.shopdevjava.exception;

public class InsufficientPermissionException extends CommonRuntimeException{
    public InsufficientPermissionException(ErrorObject errorObject, Throwable t){
        super(errorObject, t);
    }

    public InsufficientPermissionException(ErrorObject errorObject){
        super(errorObject);
    }
}
