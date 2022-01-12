package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.entity.CountryOfFilm;

import java.util.List;
import java.util.Optional;

public interface CountryOfFilmService {

    void add(Long filmId, List<Country> countryList);

    List<CountryOfFilm> findAllByCountryId(Long id);

    List<CountryOfFilm> findByFilmId(Long id);

    Optional<CountryOfFilm> findByFilmIdAndCountryId(Long filmId, Long countryId);

    Optional<CountryOfFilm> findByFilmIdAndCountryName(Long filmId, String countryName);

    void update(Long filmId, List<Country> countryList);

    void deleteById(Long id);

    void deleteByFilmId(Long filmId);
}
