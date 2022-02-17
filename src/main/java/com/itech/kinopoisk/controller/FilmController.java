package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.model.dto.FilmAddDTO;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;
import com.itech.kinopoisk.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('user:permission')")
    public List<FilmAddDTO> getFilm(@RequestBody FilmFilterRequest filterRequest) {
        return filmService.findAll(filterRequest);
    }

}
