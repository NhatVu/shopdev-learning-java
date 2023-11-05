package com.learning.shopdevjava.config;

import com.learning.shopdevjava.exception.ErrorObject;

public class ErrorCodeConstant {
    public static final ErrorObject INVALID_API_KEY_NOT_FOUND = new ErrorObject("40009", "error", "API key doesn't exists");
    public static final ErrorObject INVALID_API_KEY_NOT_FOUND_IN_HEADER = new ErrorObject("40009", "error", "API key doesn't exists in header");
    public static final ErrorObject INVALID_API_KEY_NOT_SUFFICIENT_PERMISSION = new ErrorObject("40009", "error", "API key doesn't have sufficient permissions");

    public static final ErrorObject INVALID_TOKEN = new ErrorObject("40010", "error", "Invalid token");
    public static final ErrorObject FORBIDDEN = new ErrorObject("40003", "error", "Forbidden action");

    public static final ErrorObject CREATION_FAIL = new ErrorObject("20003", "error", "Creation fail");
    public static final ErrorObject UPDATE_FAIL = new ErrorObject("20004", "error", "Update fail");

    public static final ErrorObject CREATION_INVALID_TYPE = new ErrorObject("20003", "error", "Creation with invalid type");
    public static final ErrorObject INSUFFICIENT_PERMISSION = new ErrorObject("40003", "error", "Insufficient Permisison");

    /// discount
    public static final ErrorObject BAD_REQUEST_DISCOUNT_CODE_EXPIRED = new ErrorObject("40003", "error", "Discount code expired");
    public static final ErrorObject BAD_REQUEST_DISCOUNT_CODE_EXIST = new ErrorObject("40003", "error", "Discount code exist");
    public static final ErrorObject BAD_REQUEST_DISCOUNT_CODE_START_DATE_HAVE_TO_SMALLER_THAN_END_DATE = new ErrorObject("40003", "error", "startDate have to smaller than endDate");

}
