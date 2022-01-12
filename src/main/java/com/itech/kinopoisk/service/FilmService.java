package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Film;

import java.util.List;

public interface FilmService {

    Film add(Film film);

    List<Film> findAll();

    Film findById(Long id);

    Film update(Film film);

    void delete(Long id);
}
