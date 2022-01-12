package com.itech.kinopoisk.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.noSuchLoginMessage:No such element }")
    private String noSuchLoginMessage;

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<String> NoSuchElement(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>(noSuchLoginMessage + noSuchElementException.getString(), HttpStatus.BAD_REQUEST);
    }
}
