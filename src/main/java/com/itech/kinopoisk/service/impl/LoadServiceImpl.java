package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.model.FullFilm;
import com.itech.kinopoisk.service.LoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoadServiceImpl implements LoadService {

    @Override
    public void loadTop250Films() {
        Properties props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("application.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", props.getProperty("kp.key"));

        HttpEntity<String> request = new HttpEntity<>(headers);
        restTemplate.exchange(props.getProperty("kp.top250.url"), HttpMethod.GET, request, String.class);
    }

    @Override
    public FullFilm loadFilmInfo(Long id) {

        Properties props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("application.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", props.getProperty("kp.key"));

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<FullFilm> response = restTemplate.exchange(props.getProperty("kp.film.url") + id, HttpMethod.GET, request, FullFilm.class);

        return response.getBody();
    }
}
