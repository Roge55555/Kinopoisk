package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.*;
import com.itech.kinopoisk.model.dto.FilmAddDTO;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;
import com.itech.kinopoisk.repository.FilmRepository;
import com.itech.kinopoisk.repository.specification.FilmSpecification;
import com.itech.kinopoisk.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final CountryOfFilmService countryOfFilmService;

    private final GenreOfFilmService genreOfFilmService;

    @Override
    public Film add(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public List<FilmAddDTO> findAll(FilmFilterRequest filterRequest) {
        List<FilmAddDTO> filmAddDTOList = new ArrayList<>();

        List<CountryOfFilm> countryOfFilmList;
        List<GenreOfFilm> genreOfFilmList;

        List<Country> countryList = new ArrayList<>();
        List<Genre> genreList = new ArrayList<>();

        List<Film> filmList = filmRepository.findAll(FilmSpecification.getSpecification(filterRequest));

        for (Film film : filmList) {

            countryOfFilmList = countryOfFilmService.findByFilmId(film.getId());
            for (CountryOfFilm countryOfFilm : countryOfFilmList) {
                countryList.add(countryOfFilm.getCountry());
            }
            genreOfFilmList = genreOfFilmService.findByFilmId(film.getId());
            for (GenreOfFilm genreOfFilm : genreOfFilmList) {
                genreList.add(genreOfFilm.getGenre());
            }

            filmAddDTOList.add(FilmAddDTO.builder()
                    .nameRu(film.getNameRu())
                    .nameEn(film.getNameEn())
                    .year(film.getYear())
                    .filmLength(film.getLength())
                    .countries(countryList)
                    .genres(genreList)
                    .rating(film.getRating())
                    .ratingVoteCount(film.getRatingVoteCount())
                    .posterUrl(film.getPoster_url())
                    .posterUrlPreview(film.getPoster_url_preview())
                    .build());

            countryOfFilmList.clear();
            genreOfFilmList.clear();
            countryList = new ArrayList<>();
            genreList = new ArrayList<>();

        }
        return filmAddDTOList;
    }

    @Override
    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(/*log + exception*/);
    }

    @Override
    public Film update(Film film) {
        Film updatedFilm = findById(film.getId());
        if (Objects.nonNull(film.getNameRu())) {
            updatedFilm.setNameRu(film.getNameRu());
        }
        if (Objects.nonNull(film.getNameEn())) {
            updatedFilm.setNameEn(film.getNameEn());
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
