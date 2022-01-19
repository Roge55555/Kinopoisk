package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.model.dto.FilmAddDTO;
import com.itech.kinopoisk.model.dto.FilmUpdateDTO;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;
import com.itech.kinopoisk.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    private final CountryOfFilmService countryOfFilmService;

    private final GenreOfFilmService genreOfFilmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFilm(@Valid @RequestBody FilmAddDTO filmAddDTO) {
        Film film = Film.builder()
                .nameRu(filmAddDTO.getNameRu())
                .nameEn(filmAddDTO.getNameEn())
                .year(filmAddDTO.getYear())
                .length(filmAddDTO.getFilmLength())
                .rating(filmAddDTO.getRating())
                .ratingVoteCount(filmAddDTO.getRatingVoteCount())
                .poster_url(filmAddDTO.getPosterUrl())
                .poster_url_preview(filmAddDTO.getPosterUrlPreview())
                .build();

        film = filmService.add(film);
        countryOfFilmService.add(film.getId(), filmAddDTO.getCountries());
        genreOfFilmService.add(film.getId(), filmAddDTO.getGenres());
    }

    @GetMapping("/filter")
    public List<FilmAddDTO> getFilm(@RequestBody FilmFilterRequest filterRequest) {
        return filmService.findAll(filterRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@Valid @RequestBody FilmUpdateDTO filmUpdateDTO) {
        Film film = Film.builder()
                .id(filmUpdateDTO.getFilmId())
                .nameRu(filmUpdateDTO.getNameRu())
                .nameEn(filmUpdateDTO.getNameEn())
                .year(filmUpdateDTO.getYear())
                .length(filmUpdateDTO.getFilmLength())
                .rating(filmUpdateDTO.getRating())
                .ratingVoteCount(filmUpdateDTO.getRatingVoteCount())
                .poster_url(filmUpdateDTO.getPosterUrl())
                .poster_url_preview(filmUpdateDTO.getPosterUrlPreview())
                .build();

        film = filmService.update(film);
        countryOfFilmService.update(film.getId(), filmUpdateDTO.getCountries());
        genreOfFilmService.update(film.getId(), filmUpdateDTO.getGenres());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable("id") Long id) {
        filmService.delete(id);
        countryOfFilmService.deleteByFilmId(id);
        genreOfFilmService.deleteByFilmId(id);
    }
}
