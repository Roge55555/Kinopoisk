package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.repository.FilmRepository;
import com.itech.kinopoisk.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    @Override
    public Film add(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(/*log + exception*/);
    }

    @Override
    public Film update(Film film) {
        Film updatedFilm = findById(film.getId());
        if (Objects.nonNull(film.getName_ru())) {
            updatedFilm.setName_ru(film.getName_ru());
        }
        if (Objects.nonNull(film.getName_en())) {
            updatedFilm.setName_en(film.getName_en());
        }
        if (Objects.nonNull(film.getYear())) {
            updatedFilm.setYear(film.getYear());
        }
        if (Objects.nonNull(film.getLength())) {
            updatedFilm.setLength(film.getLength());
        }
        if (Objects.nonNull(film.getRating())) {
            updatedFilm.setRating(film.getRating());
        }
        if (Objects.nonNull(film.getRatingVoteCount())) {
            updatedFilm.setRatingVoteCount(film.getRatingVoteCount());
        }
        if (Objects.nonNull(film.getPoster_url())) {
            updatedFilm.setPoster_url(film.getPoster_url());
        }
        if (Objects.nonNull(film.getPoster_url_preview())) {
            updatedFilm.setPoster_url_preview(film.getPoster_url_preview());
        }
        return filmRepository.save(updatedFilm);
    }

    @Override
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}
