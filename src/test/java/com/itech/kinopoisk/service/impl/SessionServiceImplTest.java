package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.AccessRole;
import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.exceptions.TooLowAccessException;
import com.itech.kinopoisk.exceptions.TryingModifyNotYourDataException;
import com.itech.kinopoisk.model.Role;
import com.itech.kinopoisk.repository.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionServiceImplTest {

    @InjectMocks
    SessionServiceImpl sessionService;

    @Mock
    SessionRepository sessionRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    FilmServiceImpl filmService;

    @Test
    @DisplayName("Successful adding new session")
    void addSession() {
        Session session = Session.builder().creator(User.builder().login("testUser").build()).build();

        when(userService.findByLogin(anyString())).thenReturn(new User());
        sessionService.addSession(session);

        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    @DisplayName("Successful finding session by id")
    void findById() {
        Session session = Session.builder().id(4L).build();

        when(sessionRepository.findById(anyLong())).thenReturn(java.util.Optional.of(session));

        assertEquals(session, sessionService.findById(4L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing session by id")
    void findByIdException() {
        when(sessionRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> sessionService.findById(4L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding all available session")
    void findAllAvailableSession() {
        sessionService.findAllAvailableSession();

        verify(sessionRepository, times(1)).findAllByDateStartAndMaxUserCount();
    }

    @Test
    @DisplayName("Successful updating session by id")
    void updateSession() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {

            when(sessionRepository.findById(anyLong())).thenReturn(java.util.Optional.of(
                    Session.builder()
                            .creator(User.builder().login("rogE").build())
                            .film(Film.builder().id(6L).build())
                            .build()));
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(filmService.findById(anyLong())).thenReturn(Film.builder().build());
            sessionService.updateSession(19L, Session.builder().film(Film.builder().build()).build());

            verify(sessionRepository, times(1)).save(any(Session.class));
        }
    }

    @Test
    @DisplayName("Exception when we trying to update not our session by id")
    void updateSessionException() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {

            when(sessionRepository.findById(anyLong())).thenReturn(java.util.Optional.of(
                    Session.builder()
                            .creator(User.builder().login("testUser").build())
                            .film(Film.builder().id(6L).build())
                            .build()));
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(filmService.findById(anyLong())).thenReturn(Film.builder().build());

            assertThatThrownBy(() -> sessionService.updateSession(19L, Session.builder().film(Film.builder().build()).build()))
                    .isInstanceOf(TryingModifyNotYourDataException.class);
        }
    }

    @Test
    @DisplayName("Successful deleting session by id")
    void deleteSession() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {

            when(userService.findByLogin(anyString())).thenReturn(User.builder().role(AccessRole.builder().name(Role.USER).build()).build());
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(sessionRepository.findById(anyLong())).thenReturn(java.util.Optional.of(Session.builder().creator(User.builder().login("rogE").build()).build()));
            sessionService.deleteSession(16L);

            verify(sessionRepository, times(1)).deleteById(16L);
        }
    }

    @Test
    @DisplayName("Exception when we trying to delete session without permission for it by id")
    void deleteSessionException() {

        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {

            when(userService.findByLogin(anyString())).thenReturn(User.builder().role(AccessRole.builder().name(Role.USER).build()).build());
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(sessionRepository.findById(16L)).thenReturn(java.util.Optional.of(Session.builder().creator(User.builder().login("testUser").build()).build()));

            assertThatThrownBy(() -> sessionService.deleteSession(16L))
                    .isInstanceOf(TooLowAccessException.class);
        }
    }
}