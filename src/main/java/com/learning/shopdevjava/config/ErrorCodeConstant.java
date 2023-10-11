package com.learning.shopdevjava.config;

import com.learning.shopdevjava.exception.ErrorObject;

public class ErrorCodeConstant {
    public static final ErrorObject INVALID_API_KEY_NOT_FOUND = new ErrorObject("40009", "error", "API key doesn't exists");
    public static final ErrorObject INVALID_API_KEY_NOT_FOUND_IN_HEADER = new ErrorObject("40009", "error", "API key doesn't exists in header");
    public static final ErrorObject INVALID_API_KEY_NOT_SUFFICIENT_PERMISSION = new ErrorObject("40009", "error", "API key doesn't have sufficient permissions");

    public static final ErrorObject INVALID_TOKEN = new ErrorObject("40010", "error", "Invalid token");

}
