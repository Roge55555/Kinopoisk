package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Country;

public interface CountryService {

    Country findByCountryName(String countryName);
}
