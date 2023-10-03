package com.learning.shopdevjava.config;

import com.learning.shopdevjava.exception.ErrorObject;

public class ErrorCodeConstant {
    public static final ErrorObject INVALID_API_KEY_NOT_FOUND = new ErrorObject("40009", "error", "API key doesn't exists");
}
