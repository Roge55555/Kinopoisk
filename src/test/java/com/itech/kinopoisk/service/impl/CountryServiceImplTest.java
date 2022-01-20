package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.CountryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CountryServiceImplTest {

    @InjectMocks
    CountryServiceImpl countryService;

    @Mock
    CountryRepository countryRepository;

    @Test
    @DisplayName("Get country of film by name")
    void findByCountryName() {
        Country country = new Country();
        country.setId(7L);
        country.setName("Австралия");

        Mockito.when(countryRepository.findByName(ArgumentMatchers.anyString())).thenReturn(java.util.Optional.of(country));

        assertEquals(country, countryService.findByCountryName("Австралия"));

    }

    @Test
    @DisplayName("Exception when trying to get not existed country of film by name")
    void findByCountryNameException() {

        Mockito.when(countryRepository.findByName(ArgumentMatchers.anyString())).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> countryService.findByCountryName("Австралия"))
                .isInstanceOf(NoSuchElementException.class);
    }
}