package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.repository.SessionRepository;
import com.itech.kinopoisk.service.FilmService;
import com.itech.kinopoisk.service.SessionService;
import com.itech.kinopoisk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final UserService userService;

    private final FilmService filmService;

    @Override
    public Session addSession(Session session) {
        session.setCreator(userService.findByLogin(session.getCreator().getLogin()));
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> findAllAvailableSession() {
        return sessionRepository.findAllByDateStartAndMaxUserCount();
    }

    @Override
    public Session updateSession(Long id, Session session) {
        Session updatedSession = sessionRepository.findById(id).get();

        if (Objects.nonNull(session.getFilm().getId())) {
            updatedSession.setFilm(filmService.findById(session.getFilm().getId()));
        }
        if (Objects.nonNull(session.getName())) {
            updatedSession.setName(session.getName());
        }
        if (Objects.nonNull(session.getDescription())) {
            updatedSession.setDescription(session.getDescription());
        }
        if (Objects.nonNull(session.getDateStart())) {
            updatedSession.setDateStart(session.getDateStart());
        }
        if (Objects.nonNull(session.getMaxUserCount())) {
            updatedSession.setMaxUserCount(session.getMaxUserCount());
        }

        return sessionRepository.save(updatedSession);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
