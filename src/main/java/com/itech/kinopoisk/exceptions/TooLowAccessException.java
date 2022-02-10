package com.itech.kinopoisk.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TooLowAccessException extends RuntimeException {

    private String string;

    public TooLowAccessException(String string) {
        this.string = string;
    }

}
