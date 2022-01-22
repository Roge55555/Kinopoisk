package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.entity.CountryOfFilm;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.CountryOfFilmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CountryOfFilmServiceImplTest {
    
    @InjectMocks
    CountryOfFilmServiceImpl countryOfFilmService;

    @Mock
    CountryOfFilmRepository countryOfFilmRepository;

    @Mock
    CountryServiceImpl countryService;

    @Test
    @DisplayName("Add countries of film")
    void add() {
        Country country = Country.builder().id(6L).name("Германия (ФРГ)").build();
        List<Country> countryList = Collections.singletonList(Country.builder().name("Кипр").build());

        when(countryService.findByCountryName(anyString())).thenReturn(country);
        countryOfFilmService.add(10L, countryList);

        verify(countryOfFilmRepository, times(1)).save(any(CountryOfFilm.class));
    }

    @Test
    @DisplayName("Find all countries of film by film id")
    void findByFilmId() {
        CountryOfFilm countryOfFilm = CountryOfFilm.builder()
                .id(34L)
                .film(new Film())
                .country(new Country())
                .build();
        List<CountryOfFilm> list = new ArrayList<>();
        list.add(countryOfFilm);

        when(countryOfFilmRepository.findByFilmId(anyLong())).thenReturn(list);
        List<CountryOfFilm> countryOfFilmList = countryOfFilmService.findByFilmId(34L);

        assertAll(() -> assertEquals(1, countryOfFilmList.size()),
                () -> assertEquals(countryOfFilm, countryOfFilmList.get(0)));
    }

    @Test
    @DisplayName("Finding country of film element by film id and country id")
    void findByFilmIdAndCountryId() {
        countryOfFilmService.findByFilmIdAndCountryId(34L, 89L);

        verify(countryOfFilmRepository, times(1)).findByFilmIdAndCountryId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Successful finding country of film element by film id and country id")
    void findByFilmIdAndCountryName() {

        when(countryService.findByCountryName(anyString())).thenReturn(Country.builder().id(90L).build());
        when(countryOfFilmService.findByFilmIdAndCountryId(anyLong(), anyLong())).thenReturn(java.util.Optional.of(new CountryOfFilm()));
        countryOfFilmService.findByFilmIdAndCountryName(64L, "ужасы");

        verify(countryOfFilmRepository, times(1)).findByFilmIdAndCountryName(anyLong(), anyString());
    }

    @Test
    @DisplayName("Exception when trying finding country of film element by film id and country id")
    void findByFilmIdAndCountryNameException() {
        when(countryService.findByCountryName(anyString())).thenReturn(Country.builder().id(90L).build());

        assertThatThrownBy(() -> countryOfFilmService.findByFilmIdAndCountryName(64L, "ужасы"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Updating countries of film")
    void update() {
        Country country = Country.builder().id(6L).name("Мальта").build();
        List<Country> countryList = Collections.singletonList(Country.builder().name("Катар").build());
        CountryOfFilm countryOfFilmName = CountryOfFilm.builder().country(Country.builder().id(666L).name("Мексика").build()).build();
        CountryOfFilm countryOfFilmId = CountryOfFilm.builder().country(Country.builder().id(666666L).name("Новая Зеландия").build()).build();
        List<CountryOfFilm> list = new ArrayList<>();
        list.add(countryOfFilmName);
        list.add(countryOfFilmId);


        when(countryOfFilmService.findByFilmId(anyLong())).thenReturn(list);
        when(countryOfFilmService.findByFilmIdAndCountryName(anyLong(), anyString())).thenReturn(java.util.Optional.ofNullable(countryOfFilmName));
        when(countryService.findByCountryName(anyString())).thenReturn(country);
        when(countryOfFilmService.findByFilmIdAndCountryId(anyLong(), anyLong())).thenReturn(java.util.Optional.ofNullable(countryOfFilmId));
        countryOfFilmService.update(10L, countryList);

        verify(countryOfFilmRepository, times(1)).save(any(CountryOfFilm.class));
    }

    @Test
    @DisplayName("Delete country of film by id")
    void deleteById() {
        countryOfFilmService.deleteById(34L);

        verify(countryOfFilmRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Delete all countries of film by film id")
    void deleteByFilmId() {
        countryOfFilmService.deleteByFilmId(99L);

        verify(countryOfFilmRepository, times(1)).deleteByFilmId(anyLong());
    }
}
