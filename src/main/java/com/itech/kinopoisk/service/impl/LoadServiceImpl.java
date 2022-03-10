package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.model.FullFilm;
import com.itech.kinopoisk.service.LoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoadServiceImpl implements LoadService {

    @Value("${service.top250.url}")
    private String topUrl;

    @Value("${service.film.url}")
    private String fullUrl;

    private final RestTemplate restTemplate;

    @Override
    public void loadTop250Films() {
        restTemplate.exchange(topUrl, HttpMethod.GET, null, String.class);
    }

    @Override
    public FullFilm loadFilmInfo(Long id) {
        return restTemplate.exchange(fullUrl + id, HttpMethod.GET, null, FullFilm.class).getBody();
    }
}
