package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.exceptions.TooLowAccessException;
import com.itech.kinopoisk.exceptions.TryingModifyNotYourDataException;
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
    public Session findById(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> {
            log.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public List<Session> findAllAvailableSession() {
        return sessionRepository.findAllByDateStartAndMaxUserCount();
    }

    @Override
    public Session updateSession(Long id, Session session) {
        Session updatedSession = findById(id);

        if(updatedSession.getCreator().getLogin().equals(Utils.getLogin())) {

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
        else {
            log.error("Trying update not your session - {}.", id);
            throw new TryingModifyNotYourDataException("Trying update not your session!");
        }
    }

    @Override
    public void deleteSession(Long id) {
        if(findById(id).getCreator().getLogin().equals(Utils.getLogin()) || userService.findByLogin(Utils.getLogin()).getRole().getId() == 2L) {
            sessionRepository.deleteById(id);
        }
        else {
            log.error("User - {}, trying delete session with id - {}.", Utils.getLogin(), id);
            throw new TooLowAccessException("Only creator or moderator can delete session!");
        }
    }
}
