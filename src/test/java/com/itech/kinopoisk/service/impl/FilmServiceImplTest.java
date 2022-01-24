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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    private Film newFilm(Long id, String nameRu, String nameEn) {

        return Film.builder()
                .id(id)
                .nameRu(nameRu)
                .nameEn(nameEn)
                .year("1985")
                .length("2:08")
                .rating(1.87)
                .ratingVoteCount(3265L)
                .poster_url("/poster")
                .poster_url_preview("/poster/preview")
                .build();
    }

    @Test
    @DisplayName("Add film")
    void add() {
        Film film = new Film();

        filmService.add(film);

        verify(filmRepository, times(1)).save(any(Film.class));
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
    @DisplayName("Updating film by id")
    void update() {
        Film oldFilm = newFilm(4L, "Интерстеллар", null);

        when(filmRepository.findById(anyLong())).thenReturn(Optional.ofNullable(oldFilm));
        oldFilm.setNameEn("Interstellar");
        when(filmRepository.save(any(Film.class))).thenReturn(oldFilm);
        Film updatedFilm = filmService.update(newFilm(4L, null, "Interstellar"));


        verify(filmRepository, times(1)).save(any(Film.class));
        assertEquals(oldFilm, updatedFilm);
    }

    @Test
    @DisplayName("Delete film by id")
    void delete() {

        when(filmRepository.findById(anyLong())).thenReturn(Optional.ofNullable(newFilm(2L, "xxx", "xxx")));
        filmService.delete(641564L);

        verify(filmRepository, times(1)).deleteById(anyLong());
    }
}
