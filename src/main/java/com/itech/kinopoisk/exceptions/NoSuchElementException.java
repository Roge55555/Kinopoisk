package com.itech.kinopoisk.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchElementException extends RuntimeException {

    private String string;

    private Long id;

    public NoSuchElementException(Long id) {
        this.id = id;
    }

    public NoSuchElementException(String string) {
        this.string = string;
    }

}
