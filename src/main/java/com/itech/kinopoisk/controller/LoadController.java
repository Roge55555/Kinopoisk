package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/load")
public class LoadController {

    private final LoadService loadService;

    @GetMapping("/top250")
    @ResponseStatus(HttpStatus.FOUND)
    public void addUser() {
        loadService.loadTop250Films();
    }
}
