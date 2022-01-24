package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.entity.CountryOfFilm;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.CountryOfFilmRepository;
import com.itech.kinopoisk.service.CountryOfFilmService;
import com.itech.kinopoisk.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CountryOfFilmServiceImpl implements CountryOfFilmService {

    private final CountryOfFilmRepository countryOfFilmRepository;

    private final CountryService countryService;

    @Override
    public void add(Long filmId, List<Country> countryList) {
        CountryOfFilm countryOfFilm;
        for (Country country : countryList) {
             countryOfFilm = CountryOfFilm.builder()
                     .film(Film.builder().id(filmId).build())
                     .country(Country.builder().id(countryService.findByCountryName(country.getName()).getId()).build()).build();
            countryOfFilmRepository.save(countryOfFilm);
        }
    }

    @Override
    public List<CountryOfFilm> findByFilmId(Long id) {
        return countryOfFilmRepository.findByFilmId(id);
    }

    //TODO inside check of existing
    @Override
    public Optional<CountryOfFilm> findByFilmIdAndCountryId(Long filmId, Long countryId) {
        return countryOfFilmRepository.findByFilmIdAndCountryId(filmId, countryId);
    }

    @Override
    public Optional<CountryOfFilm> findByFilmIdAndCountryName(Long filmId, String countryName) {
        if (findByFilmIdAndCountryId(filmId, countryService.findByCountryName(countryName).getId()).isEmpty()) {
            log.error("No element with such film id - {} and country name - {}.", filmId, countryName);
            throw new NoSuchElementException(filmId + " " + countryName);
        }
        return countryOfFilmRepository.findByFilmIdAndCountryName(filmId, countryName);
    }

    @Override
    public void update(Long filmId, List<Country> countryList) {
        List<String> newCountryNameList = new ArrayList<>();

        for (Country country : countryList) {
            newCountryNameList.add(country.getName());
        }

        List<CountryOfFilm> oldCountryOfFilmList = findByFilmId(filmId);
        for (CountryOfFilm countryOfFilm : oldCountryOfFilmList) {

            if (!newCountryNameList.contains(countryOfFilm.getCountry().getName())) {
                deleteById(countryOfFilm.getId());
            }
        }

        CountryOfFilm countryOfFilm;
        for (Country country : countryList) {
            if (findByFilmIdAndCountryName(filmId, country.getName()).isEmpty()) {
                countryOfFilm = CountryOfFilm.builder()
                        .film(Film.builder().id(filmId).build())
                        .country(Country.builder().id(countryService.findByCountryName(country.getName()).getId()).build()).build();
                countryOfFilmRepository.save(countryOfFilm);
            }

        }
    }

    @Override
    public void deleteById(Long id) {
        countryOfFilmRepository.deleteById(id);
    }

    @Override
    public void deleteByFilmId(Long filmId) {
        countryOfFilmRepository.deleteByFilmId(filmId);
    }
}
