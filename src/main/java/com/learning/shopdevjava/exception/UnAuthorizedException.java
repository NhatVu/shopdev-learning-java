package com.learning.shopdevjava.exception;

public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String message){
        super(message);
    }

    public UnAuthorizedException(String message, Throwable t){
        super(message, t);
    }
}
