package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.Genre;
import com.itech.kinopoisk.entity.GenreOfFilm;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.GenreOfFilmRepository;
import com.itech.kinopoisk.service.GenreOfFilmService;
import com.itech.kinopoisk.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class GenreOfFilmServiceImpl implements GenreOfFilmService {
    
    private final GenreOfFilmRepository genreOfFilmRepository;
    
    private final GenreService genreService;

    @Override
    public void add(Long filmId, List<Genre> genreList) {
        GenreOfFilm genreOfFilm;
        for (Genre genre : genreList) {
            genreOfFilm = GenreOfFilm.builder()
                    .film(Film.builder().id(filmId).build())
                    .genre(Genre.builder().id(genreService.findByGenreName(genre.getName()).getId()).build()).build();
            genreOfFilmRepository.save(genreOfFilm);
        }
    }

    @Override
    public List<GenreOfFilm> findByFilmId(Long id) {
        return genreOfFilmRepository.findByFilmId(id);
    }

    //TODO inside check of existing
    @Override
    public Optional<GenreOfFilm> findByFilmIdAndGenreId(Long filmId, Long genreId) {
        return genreOfFilmRepository.findByFilmIdAndGenreId(filmId, genreId);
    }

    @Override
    public Optional<GenreOfFilm> findByFilmIdAndGenreName(Long filmId, String genreName) {
        if (Objects.isNull(findByFilmIdAndGenreId(filmId, genreService.findByGenreName(genreName).getId()))) {
            //log
            //err
            throw new NoSuchElementException(filmId + " " + genreService.findByGenreName(genreName).getId());
        }
        return genreOfFilmRepository.findByFilmIdAndGenreName(filmId, genreName);
    }

    @Override
    public void update(Long filmId, List<Genre> genreList) {
        List<String> newGenreNameList = new ArrayList<>();

        for (Genre genre : genreList) {
            newGenreNameList.add(genre.getName());
        }

        List<GenreOfFilm> oldGenreOfFilmList = findByFilmId(filmId);
        for (GenreOfFilm genreOfFilm : oldGenreOfFilmList) {

            if (!newGenreNameList.contains(genreOfFilm.getGenre().getName())) {
                deleteById(genreOfFilm.getId());
            }
        }

        GenreOfFilm genreOfFilm;
        for (Genre genre : genreList) {
            if (findByFilmIdAndGenreName(filmId, genre.getName()).isEmpty()) {
                genreOfFilm = GenreOfFilm.builder()
                        .film(Film.builder().id(filmId).build())
                        .genre(Genre.builder().id(genreService.findByGenreName(genre.getName()).getId()).build()).build();
                genreOfFilmRepository.save(genreOfFilm);
            }

        }
    }

    @Override
    public void deleteById(Long id) {
        genreOfFilmRepository.deleteById(id);
    }

    @Override
    public void deleteByFilmId(Long filmId) {
        genreOfFilmRepository.deleteByFilmId(filmId);
    }
}
