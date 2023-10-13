package com.learning.shopdevjava.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> badRequest(
            RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(value
            = { InvalidAPIKeyException.class, InvalidTokenException.class })
    protected ResponseEntity<Object> badRequestCustom(
            CommonRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> res = new HashMap<>();
        res.put("code", ex.getErrorObject().getCode());
        res.put("message", ex.getErrorObject().getMessage());
        res.put("status", ex.getErrorObject().getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value
            = { ForbiddenException.class })
    protected ResponseEntity<Object> forbiddenRequestCustom(
            CommonRuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> res = new HashMap<>();
        res.put("code", ex.getErrorObject().getCode());
        res.put("message", ex.getErrorObject().getMessage());
        res.put("status", ex.getErrorObject().getStatus());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
    }

    @ExceptionHandler(value
            = { NotFoundException.class})
    protected ResponseEntity<Object> notFound(
            RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // this is used for handle all exception that we didn't catch.
    @ExceptionHandler(value
            = { Exception.class})
    protected ResponseEntity<Object> internalServerError(
            RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
