package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Film;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        Mockito.when(filmService.add(ArgumentMatchers.any(Film.class))).thenReturn(Film.builder().id(6L).build());
        loadService.loadTop250Films();

        Mockito.verify(filmService, Mockito.times(250)).add(ArgumentMatchers.any(Film.class));
        Mockito.verify(countryOfFilmService, Mockito.times(250)).add(ArgumentMatchers.anyLong(), ArgumentMatchers.anyList());
        Mockito.verify(genreOfFilmService, Mockito.times(250)).add(ArgumentMatchers.anyLong(), ArgumentMatchers.anyList());
    }
}