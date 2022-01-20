package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Genre;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.repository.GenreRepository;
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
class GenreServiceImplTest {

    @InjectMocks
    GenreServiceImpl genreService;

    @Mock
    GenreRepository genreRepository;

    @Test
    @DisplayName("Get genre of film by name")
    void findByGenreName() {
        Genre genre = new Genre();
        genre.setId(16L);
        genre.setName("биография");

        Mockito.when(genreRepository.findByName(ArgumentMatchers.anyString())).thenReturn(java.util.Optional.of(genre));

        assertEquals(genre, genreService.findByGenreName("биография"));

    }

    @Test
    @DisplayName("Exception when trying to get not existed genre of film by name")
    void findByGenreNameException() {

        Mockito.when(genreRepository.findByName(ArgumentMatchers.anyString())).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> genreService.findByGenreName("биография"))
                .isInstanceOf(NoSuchElementException.class);
    }
}