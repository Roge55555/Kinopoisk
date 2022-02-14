package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.model.FullFilm;
import com.itech.kinopoisk.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/load")
public class LoadController {

    private final LoadService loadService;

    @GetMapping("/top250")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('admin:permission')")
    public void addUser() {
        loadService.loadTop250Films();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasAuthority('admin:permission')")
    public FullFilm addUser(@PathVariable("id") Long id) {
        return loadService.loadFilmInfo(id);
    }
}
