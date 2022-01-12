package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Genre;

public interface GenreService {

    Genre findByGenreName(String genreName);
}
