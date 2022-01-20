package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.Genre;
import com.itech.kinopoisk.entity.GenreOfFilm;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.GenreOfFilmRepository;
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
        List<Genre> genreList = new ArrayList<>();
        genreList.add(Genre.builder().name("биография").build());
        genreList.add(Genre.builder().name("вестерн").build());
        genreList.add(Genre.builder().name("история").build());

        Mockito.when(genreService.findByGenreName(ArgumentMatchers.anyString())).thenReturn(genre);
        genreOfFilmService.add(10L, genreList);

        Mockito.verify(genreOfFilmRepository, Mockito.times(3)).save(ArgumentMatchers.any(GenreOfFilm.class));
    }

    @Test
    @DisplayName("Find all genres of film by film id")
    void findByFilmId() {
        GenreOfFilm genreOfFilm = GenreOfFilm.builder()
                .id(34L)
                .film(new Film())
                .genre(new Genre())
                .build();
        List<GenreOfFilm> list = new ArrayList<>();
        list.add(genreOfFilm);

        Mockito.when(genreOfFilmRepository.findByFilmId(ArgumentMatchers.anyLong())).thenReturn(list);
        List<GenreOfFilm> genreOfFilmList = genreOfFilmService.findByFilmId(34L);

        assertAll(() -> assertEquals(1, genreOfFilmList.size()),
                () -> assertEquals(genreOfFilm, genreOfFilmList.get(0)));
    }

    @Test
    @DisplayName("Finding genre of film element by film id and genre id")
    void findByFilmIdAndGenreId() {
        genreOfFilmService.findByFilmIdAndGenreId(34L, 89L);

        Mockito.verify(genreOfFilmRepository, Mockito.times(1)).findByFilmIdAndGenreId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Successful finding genre of film element by film id and genre id")
    void findByFilmIdAndGenreName() {

        Mockito.when(genreService.findByGenreName(ArgumentMatchers.anyString())).thenReturn(Genre.builder().id(90L).build());
        Mockito.when(genreOfFilmService.findByFilmIdAndGenreId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(new GenreOfFilm()));
        genreOfFilmService.findByFilmIdAndGenreName(64L, "ужасы");

        Mockito.verify(genreOfFilmRepository, Mockito.times(1)).findByFilmIdAndGenreName(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("Exception when trying finding genre of film element by film id and genre id")
    void findByFilmIdAndGenreNameException() {
        Mockito.when(genreService.findByGenreName(ArgumentMatchers.anyString())).thenReturn(Genre.builder().id(90L).build());

        assertThatThrownBy(() -> genreOfFilmService.findByFilmIdAndGenreName(64L, "ужасы"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Updating genres of film")
    void update() {
        Genre genre = Genre.builder().id(6L).name("ужасы").build();
        List<Genre> genreList = new ArrayList<>();
        genreList.add(Genre.builder().name("история").build());
        GenreOfFilm genreOfFilmName = GenreOfFilm.builder().genre(Genre.builder().id(666L).name("музыка").build()).build();
        GenreOfFilm genreOfFilmId = GenreOfFilm.builder().genre(Genre.builder().id(666666L).name("драма").build()).build();
        List<GenreOfFilm> list = new ArrayList<>();
        list.add(genreOfFilmName);
        list.add(genreOfFilmId);


        Mockito.when(genreOfFilmService.findByFilmId(ArgumentMatchers.anyLong())).thenReturn(list);
        Mockito.when(genreOfFilmService.findByFilmIdAndGenreName(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(java.util.Optional.ofNullable(genreOfFilmName));
        Mockito.when(genreService.findByGenreName(ArgumentMatchers.anyString())).thenReturn(genre);
        Mockito.when(genreOfFilmService.findByFilmIdAndGenreId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.ofNullable(genreOfFilmId));
        genreOfFilmService.update(10L, genreList);

        Mockito.verify(genreOfFilmRepository, Mockito.times(1)).save(ArgumentMatchers.any(GenreOfFilm.class));
    }

    @Test
    @DisplayName("Delete genre of film by id")
    void deleteById() {
        genreOfFilmService.deleteById(34L);

        Mockito.verify(genreOfFilmRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Delete all genres of film by film id")
    void deleteByFilmId() {
        genreOfFilmService.deleteByFilmId(99L);

        Mockito.verify(genreOfFilmRepository, Mockito.times(1)).deleteByFilmId(ArgumentMatchers.anyLong());
    }
}