package com.itech.kinopoisk.service;

import com.itech.kinopoisk.model.FullFilm;

public interface LoadService {

    void loadTop250Films();

    FullFilm loadFilmInfo(Long id);
}
