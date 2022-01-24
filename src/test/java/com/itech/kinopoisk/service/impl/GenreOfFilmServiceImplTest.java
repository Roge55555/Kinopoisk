package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.Genre;
import com.itech.kinopoisk.entity.GenreOfFilm;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.GenreOfFilmRepository;
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
class GenreOfFilmServiceImplTest {

    @InjectMocks
    GenreOfFilmServiceImpl genreOfFilmService;

    @Mock
    GenreOfFilmRepository genreOfFilmRepository;

    @Mock
    GenreServiceImpl genreService;

    @Test
    @DisplayName("Add genres of film")
    void add() {
        Genre genre = Genre.builder().id(6L).name("ужасы").build();
        List<Genre> genreList = Collections.singletonList(Genre.builder().name("биография").build());

        when(genreService.findByGenreName(anyString())).thenReturn(genre);
        genreOfFilmService.add(10L, genreList);

        verify(genreOfFilmRepository, times(1)).save(any(GenreOfFilm.class));
    }

    @Test
    @DisplayName("Find all genres of film by film id")
    void findByFilmId() {
        GenreOfFilm genreOfFilm = GenreOfFilm.builder()
                .id(34L)
                .film(new Film())
                .genre(new Genre())
                .build();
        List<GenreOfFilm> list = Collections.singletonList(genreOfFilm);

        when(genreOfFilmRepository.findByFilmId(anyLong())).thenReturn(list);
        List<GenreOfFilm> genreOfFilmList = genreOfFilmService.findByFilmId(34L);

        assertAll(() -> assertEquals(1, genreOfFilmList.size()),
                () -> assertEquals(genreOfFilm, genreOfFilmList.get(0)));
    }

    @Test
    @DisplayName("Finding genre of film element by film id and genre id")
    void findByFilmIdAndGenreId() {
        genreOfFilmService.findByFilmIdAndGenreId(34L, 89L);

        verify(genreOfFilmRepository, times(1)).findByFilmIdAndGenreId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Successful finding genre of film element by film id and genre id")
    void findByFilmIdAndGenreName() {

        when(genreService.findByGenreName(anyString())).thenReturn(Genre.builder().id(90L).build());
        when(genreOfFilmService.findByFilmIdAndGenreId(anyLong(), anyLong())).thenReturn(java.util.Optional.of(new GenreOfFilm()));
        genreOfFilmService.findByFilmIdAndGenreName(64L, "ужасы");

        verify(genreOfFilmRepository, times(1)).findByFilmIdAndGenreName(anyLong(), anyString());
    }

    @Test
    @DisplayName("Exception when trying finding genre of film element by film id and genre id")
    void findByFilmIdAndGenreNameException() {
        when(genreService.findByGenreName(anyString())).thenReturn(Genre.builder().id(90L).build());

        assertThatThrownBy(() -> genreOfFilmService.findByFilmIdAndGenreName(64L, "ужасы"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Updating genres of film")
    void update() {
        Genre genre = Genre.builder().id(6L).name("ужасы").build();
        List<Genre> genreList = Collections.singletonList(Genre.builder().name("история").build());
        GenreOfFilm genreOfFilmName = GenreOfFilm.builder().genre(Genre.builder().id(666L).name("музыка").build()).build();
        GenreOfFilm genreOfFilmId = GenreOfFilm.builder().genre(Genre.builder().id(666666L).name("драма").build()).build();
        List<GenreOfFilm> list = new ArrayList<>();
        list.add(genreOfFilmName);
        list.add(genreOfFilmId);


        when(genreOfFilmService.findByFilmId(anyLong())).thenReturn(list);
        when(genreOfFilmService.findByFilmIdAndGenreName(anyLong(), anyString())).thenReturn(java.util.Optional.ofNullable(genreOfFilmName));
        when(genreService.findByGenreName(anyString())).thenReturn(genre);
        when(genreOfFilmService.findByFilmIdAndGenreId(anyLong(), anyLong())).thenReturn(java.util.Optional.ofNullable(genreOfFilmId));
        genreOfFilmService.update(10L, genreList);

        verify(genreOfFilmRepository, times(1)).save(any(GenreOfFilm.class));
    }

    @Test
    @DisplayName("Delete genre of film by id")
    void deleteById() {
        genreOfFilmService.deleteById(34L);

        verify(genreOfFilmRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Delete all genres of film by film id")
    void deleteByFilmId() {
        genreOfFilmService.deleteByFilmId(99L);

        verify(genreOfFilmRepository, times(1)).deleteByFilmId(anyLong());
    }
}
