package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Film;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LoadServiceImplTest {

    @InjectMocks
    LoadServiceImpl loadService;

    @Mock
    FilmServiceImpl filmService;

    @Mock
    CountryOfFilmServiceImpl countryOfFilmService;

    @Mock
    GenreOfFilmServiceImpl genreOfFilmService;

    @Test
    @DisplayName("Load all 250 films in list")
    void loadTop250Films() {

        when(filmService.add(any(Film.class))).thenReturn(Film.builder().id(6L).build());
        loadService.loadTop250Films();

        verify(filmService, times(250)).add(any(Film.class));
        verify(countryOfFilmService, times(250)).add(anyLong(), anyList());
        verify(genreOfFilmService, times(250)).add(anyLong(), anyList());
    }
}
