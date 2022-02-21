package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.*;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.model.dto.FilmAddDTO;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;
import com.itech.kinopoisk.repository.FilmRepository;
import com.itech.kinopoisk.repository.specification.FilmSpecification;
import com.itech.kinopoisk.service.CountryOfFilmService;
import com.itech.kinopoisk.service.FilmService;
import com.itech.kinopoisk.service.GenreOfFilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final CountryOfFilmService countryOfFilmService;

    private final GenreOfFilmService genreOfFilmService;

    @Override
    public List<FilmAddDTO> findAll(FilmFilterRequest filterRequest) {
        List<FilmAddDTO> filmAddDTOList = new ArrayList<>();

        List<CountryOfFilm> countryOfFilmList;
        List<GenreOfFilm> genreOfFilmList;

        List<Country> countryList = new ArrayList<>();
        List<Genre> genreList = new ArrayList<>();

        List<Film> filmList = filmRepository.findAll(FilmSpecification.getSpecification(filterRequest));

        for (Film film : filmList) {

            if (!film.getIsBlocked()) {

                countryOfFilmList = countryOfFilmService.findByFilmId(film.getId());
                for (CountryOfFilm countryOfFilm : countryOfFilmList) {
                    countryList.add(countryOfFilm.getCountry());
                }
                genreOfFilmList = genreOfFilmService.findByFilmId(film.getId());
                for (GenreOfFilm genreOfFilm : genreOfFilmList) {
                    genreList.add(genreOfFilm.getGenre());
                }

                filmAddDTOList.add(FilmAddDTO.builder()
                        .nameRu(film.getNameRu())
                        .nameEn(film.getNameEn())
                        .year(film.getYear())
                        .filmLength(film.getLength())
                        .countries(countryList)
                        .genres(genreList)
                        .rating(film.getRating())
                        .ratingVoteCount(film.getRatingVoteCount())
                        .posterUrl(film.getPoster_url())
                        .posterUrlPreview(film.getPoster_url_preview())
                        .build());

                countryOfFilmList.clear();
                genreOfFilmList.clear();
                countryList = new ArrayList<>();
                genreList = new ArrayList<>();

            }
        }

        return filmAddDTOList;
    }

    @Override
    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> {
            log.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public void banFilm(Long id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<>(headers);
        restTemplate.put("http://localhost:8079/JE_war_exploded/films/" + id, request, String.class);
    }

}
