package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.entity.CountryOfFilm;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.CountryOfFilmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<Country> countryList = new ArrayList<>();
        countryList.add(Country.builder().name("Гонконг").build());
        countryList.add(Country.builder().name("Иордания").build());
        countryList.add(Country.builder().name("Кипр").build());

        Mockito.when(countryService.findByCountryName(ArgumentMatchers.anyString())).thenReturn(country);
        countryOfFilmService.add(10L, countryList);

        Mockito.verify(countryOfFilmRepository, Mockito.times(3)).save(ArgumentMatchers.any(CountryOfFilm.class));
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

        Mockito.when(countryOfFilmRepository.findByFilmId(ArgumentMatchers.anyLong())).thenReturn(list);
        List<CountryOfFilm> countryOfFilmList = countryOfFilmService.findByFilmId(34L);

        assertAll(() -> assertEquals(1, countryOfFilmList.size()),
                () -> assertEquals(countryOfFilm, countryOfFilmList.get(0)));
    }

    @Test
    @DisplayName("Finding country of film element by film id and country id")
    void findByFilmIdAndCountryId() {
        countryOfFilmService.findByFilmIdAndCountryId(34L, 89L);

        Mockito.verify(countryOfFilmRepository, Mockito.times(1)).findByFilmIdAndCountryId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Successful finding country of film element by film id and country id")
    void findByFilmIdAndCountryName() {

        Mockito.when(countryService.findByCountryName(ArgumentMatchers.anyString())).thenReturn(Country.builder().id(90L).build());
        Mockito.when(countryOfFilmService.findByFilmIdAndCountryId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(new CountryOfFilm()));
        countryOfFilmService.findByFilmIdAndCountryName(64L, "ужасы");

        Mockito.verify(countryOfFilmRepository, Mockito.times(1)).findByFilmIdAndCountryName(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("Exception when trying finding country of film element by film id and country id")
    void findByFilmIdAndCountryNameException() {
        Mockito.when(countryService.findByCountryName(ArgumentMatchers.anyString())).thenReturn(Country.builder().id(90L).build());

        assertThatThrownBy(() -> countryOfFilmService.findByFilmIdAndCountryName(64L, "ужасы"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Updating countries of film")
    void update() {
        Country country = Country.builder().id(6L).name("Мальта").build();
        List<Country> countryList = new ArrayList<>();
        countryList.add(Country.builder().name("Катар").build());
        CountryOfFilm countryOfFilmName = CountryOfFilm.builder().country(Country.builder().id(666L).name("Мексика").build()).build();
        CountryOfFilm countryOfFilmId = CountryOfFilm.builder().country(Country.builder().id(666666L).name("Новая Зеландия").build()).build();
        List<CountryOfFilm> list = new ArrayList<>();
        list.add(countryOfFilmName);
        list.add(countryOfFilmId);


        Mockito.when(countryOfFilmService.findByFilmId(ArgumentMatchers.anyLong())).thenReturn(list);
        Mockito.when(countryOfFilmService.findByFilmIdAndCountryName(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(java.util.Optional.ofNullable(countryOfFilmName));
        Mockito.when(countryService.findByCountryName(ArgumentMatchers.anyString())).thenReturn(country);
        Mockito.when(countryOfFilmService.findByFilmIdAndCountryId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.ofNullable(countryOfFilmId));
        countryOfFilmService.update(10L, countryList);

        Mockito.verify(countryOfFilmRepository, Mockito.times(1)).save(ArgumentMatchers.any(CountryOfFilm.class));
    }

    @Test
    @DisplayName("Delete country of film by id")
    void deleteById() {
        countryOfFilmService.deleteById(34L);

        Mockito.verify(countryOfFilmRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Delete all countries of film by film id")
    void deleteByFilmId() {
        countryOfFilmService.deleteByFilmId(99L);

        Mockito.verify(countryOfFilmRepository, Mockito.times(1)).deleteByFilmId(ArgumentMatchers.anyLong());
    }
}