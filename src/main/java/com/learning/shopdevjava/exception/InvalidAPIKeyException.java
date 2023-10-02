package com.learning.shopdevjava.exception;

public class InvalidAPIKeyException extends RuntimeException{
    public InvalidAPIKeyException(String message){
        super(message);
    }

    public InvalidAPIKeyException(String message, Throwable t){
        super(message, t);
    }
}
