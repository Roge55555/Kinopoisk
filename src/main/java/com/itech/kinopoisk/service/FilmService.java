package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.model.dto.FilmAddDTO;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;

import java.util.List;

public interface FilmService {

    Film add(Film film);

    List<FilmAddDTO> findAll(FilmFilterRequest filterRequest);

    Film findById(Long id);

    Film update(Film film);

    void delete(Long id);
}
