package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Genre;
import com.itech.kinopoisk.entity.GenreOfFilm;

import java.util.List;
import java.util.Optional;

public interface GenreOfFilmService {

    void add(Long filmId, List<Genre> genreList);

    List<GenreOfFilm> findAllByGenreId(Long id);

    List<GenreOfFilm> findByFilmId(Long id);

    Optional<GenreOfFilm> findByFilmIdAndGenreId(Long filmId, Long genreId);

    Optional<GenreOfFilm> findByFilmIdAndGenreName(Long filmId, String genreName);

    void update(Long filmId, List<Genre> genreList);

    void deleteById(Long id);

    void deleteByFilmId(Long filmId);
}
