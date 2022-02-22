package com.itech.kinopoisk.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.noSuchElementMessage:No such element }")
    private String noSuchElementMessage;

    @Value(value = "${data.exception.JWTInvalidTokenMessage:Your authentication is invalid.}")
    private String jwtInvalidTokenMessage;

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<String> noSuchElement(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>(noSuchElementMessage + noSuchElementException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    public ResponseEntity<String> jwtInvalidToken(JwtAuthenticationException jwtAuthenticationException) {
        return new ResponseEntity<>(jwtInvalidTokenMessage, HttpStatus.GONE);
    }

    @ExceptionHandler(value = TooLowAccessException.class)
    public ResponseEntity<String> noSuchElement(TooLowAccessException tooLowAccessException) {
        return new ResponseEntity<>(tooLowAccessException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
