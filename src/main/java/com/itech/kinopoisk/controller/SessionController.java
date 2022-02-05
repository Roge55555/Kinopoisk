package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.model.dto.SessionDTO;
import com.itech.kinopoisk.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session addSession(@Valid @RequestBody SessionDTO sessionDTO) {
        Session session = Session.builder()
                .creator(User.builder().login(Utils.getLogin()).build())
                .film(Film.builder().id(sessionDTO.getFilmId()).build())
                .name(sessionDTO.getName())
                .description(sessionDTO.getDescription())
                .dateStart(sessionDTO.getDateStart())
                .maxUserCount(sessionDTO.getMaxUserCount())
                .build();

        return sessionService.addSession(session);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Session> getAllAvailableSession() {
        return sessionService.findAllAvailableSession();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateSession(@PathVariable("id") Long id, @Valid @RequestBody SessionDTO sessionDTO) {
        Session session = Session.builder()
                .creator(User.builder().login(Utils.getLogin()).build())
                .film(Film.builder().id(sessionDTO.getFilmId()).build())
                .name(sessionDTO.getName())
                .description(sessionDTO.getDescription())
                .dateStart(sessionDTO.getDateStart())
                .maxUserCount(sessionDTO.getMaxUserCount())
                .build();
        sessionService.updateSession(id, session);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable("id") Long id) {
        sessionService.deleteSession(id);
    }
}
