package com.learning.shopdevjava.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ErrorObject {
    private String code;
    private String status;
    private String message;

    public ErrorObject(String code, String status, String message){
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString(){
        return String.format("code=%s, status=%s, message=%s", code, status, message);
    }
}
