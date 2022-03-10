package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.CountryOfFilm;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.GenreOfFilm;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.model.dto.FilmAddDTO;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;
import com.itech.kinopoisk.repository.FilmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FilmServiceImplTest {

    @InjectMocks
    FilmServiceImpl filmService;

    @Mock
    FilmRepository filmRepository;

    @Mock
    CountryOfFilmServiceImpl countryOfFilmService;

    @Mock
    GenreOfFilmServiceImpl genreOfFilmService;

    @Mock
    RestTemplate restTemplate;

    private Film newFilm(Long id, String nameRu, String nameEn) {

        return Film.builder()
                .id(id)
                .nameRu(nameRu)
                .nameEn(nameEn)
                .year("1985")
                .length("2:08")
                .rating(1.87)
                .ratingVoteCount(3265L)
                .posterUrl("/poster")
                .posterUrlPreview("/poster/preview")
                .isBlocked(false)
                .build();
    }

    @Test
    @DisplayName("Showing all films with selected filter")
    void findAll() {
        List<Film> film = Collections.singletonList(newFilm(1L, "1+1", null));
        List<CountryOfFilm> countryOfFilmList = new ArrayList<>();
        countryOfFilmList.add(new CountryOfFilm());
        countryOfFilmList.add(new CountryOfFilm());
        List<GenreOfFilm> genreOfFilmList = new ArrayList<>();
        genreOfFilmList.add(new GenreOfFilm());
        genreOfFilmList.add(new GenreOfFilm());
        genreOfFilmList.add(new GenreOfFilm());

        when(filmRepository.findAll((Specification<Film>) any())).thenReturn(film);
        when(countryOfFilmService.findByFilmId(film.get(0).getId())).thenReturn(countryOfFilmList);
        when(genreOfFilmService.findByFilmId(film.get(0).getId())).thenReturn(genreOfFilmList);
        List<FilmAddDTO> filmAddDTO = filmService.findAll(new FilmFilterRequest());

        assertAll(() -> assertEquals(1, filmAddDTO.size()),
                () -> assertEquals(2, filmAddDTO.get(0).getCountries().size()),
                () -> assertEquals(3, filmAddDTO.get(0).getGenres().size()),
                () -> assertEquals("1+1", filmAddDTO.get(0).getNameRu()));


    }

    @Test
    @DisplayName("Successful finding film by id")
    void findById() {
        Film film = Film.builder().id(6L).build();

        when(filmRepository.findById(anyLong())).thenReturn(java.util.Optional.of(film));

        assertEquals(film, filmService.findById(6L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing community message by id")
    void findByIdException() {
        when(filmRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> filmService.findById(6L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful ban film by id")
    void banFilm() {
        filmService.banFilm(6L);

        verify(restTemplate, times(1)).put(anyString(), any(), any(Class.class));
    }
}
