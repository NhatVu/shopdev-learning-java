package com.learning.shopdevjava.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CommonRuntimeException extends RuntimeException{
    private ErrorObject errorObject;

    public CommonRuntimeException(){}
    public CommonRuntimeException(String message){
        super(message);
    }

    public CommonRuntimeException(String message, Throwable t){
        super(message, t);
    }

    public CommonRuntimeException(ErrorObject errorObject, Throwable t){
        super(errorObject.toString(), t);
        this.errorObject = errorObject;
    }

    public CommonRuntimeException(ErrorObject errorObject){
        super(errorObject.toString());
        this.errorObject = errorObject;
    }
}
