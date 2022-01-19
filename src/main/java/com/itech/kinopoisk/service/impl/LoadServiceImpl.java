package com.itech.kinopoisk.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.model.Page;
import com.itech.kinopoisk.model.dto.FilmUpdateDTO;
import com.itech.kinopoisk.service.CountryOfFilmService;
import com.itech.kinopoisk.service.FilmService;
import com.itech.kinopoisk.service.GenreOfFilmService;
import com.itech.kinopoisk.service.LoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoadServiceImpl implements LoadService {

    private final FilmService filmService;

    private final CountryOfFilmService countryOfFilmService;

    private final GenreOfFilmService genreOfFilmService;

    @Override
    public void loadTop250Films() {
        int pageNumber = 0;
        int end;
        Page page = new Page();
        List<FilmUpdateDTO> filmList = new ArrayList<>();
        Film film;

        Properties props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("application.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        do {
            pageNumber++;

            try {
                URL urldemo = new URL(props.getProperty("kp.url") + pageNumber);

                URLConnection yc = urldemo.openConnection();
                yc.setRequestProperty("X-API-KEY", props.getProperty("kp.key"));

                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
                String inputLine = in.readLine();
                ObjectMapper mapper = new ObjectMapper();
                page.setPagesCount(mapper.readValue(inputLine, Page.class).getPagesCount());


                filmList.addAll(mapper.readValue(inputLine, Page.class).getFilms());
                for (FilmUpdateDTO filmFromTop : filmList) {
                     film = Film.builder()
                             .id(filmFromTop.getFilmId())
                             .nameRu(filmFromTop.getNameRu())
                             .nameEn(filmFromTop.getNameEn())
                             .year(filmFromTop.getYear())
                             .length(filmFromTop.getFilmLength())
                             .rating(filmFromTop.getRating())
                             .ratingVoteCount(filmFromTop.getRatingVoteCount())
                             .poster_url(filmFromTop.getPosterUrl())
                             .poster_url_preview(filmFromTop.getPosterUrlPreview())
                             .build();

                    film = filmService.add(film);
                    countryOfFilmService.add(film.getId(), filmFromTop.getCountries());
                    genreOfFilmService.add(film.getId(), filmFromTop.getGenres());
                }
                filmList.clear();


            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            end = page.getPagesCount().intValue();
        } while (pageNumber < end);
    }
}
