package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.CountryRepository;
import com.itech.kinopoisk.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Country findByCountryName(String countryName) {
        return countryRepository.findByName(countryName).orElseThrow(() -> {
            throw new NoSuchElementException(countryName);
        }/*log + exception*/);
    }
}
