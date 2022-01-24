package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Genre;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.GenreRepository;
import com.itech.kinopoisk.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre findByGenreName(String genreName) {
        return genreRepository.findByName(genreName).orElseThrow(() -> {
            log.error("No element with such name - {}.", genreName);
            throw new NoSuchElementException(genreName);
        });
    }
}
