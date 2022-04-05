package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.entity.SessionRating;
import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.repository.SessionRatingRepository;
import com.itech.kinopoisk.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionRatingServiceImplTest {

    @InjectMocks
    SessionRatingServiceImpl sessionRatingService;

    @Mock
    SessionRatingRepository sessionRatingRepository;

    @Mock
    UserService userService;

    @Test
    void add() {
        SessionRating sessionRating = new SessionRating();
        sessionRating.setSession(Session.builder().id(5L).build());

        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            utilities.when(Utils::getLogin).thenReturn("roge");
            when(userService.findByLogin("roge")).thenReturn(new User());
            sessionRatingService.add(sessionRating);

            verify(sessionRatingRepository, times(1)).save(any(SessionRating.class));
        }
    }

    @Test
    void findByCreatorAndSession() {
        sessionRatingService.findByCreatorAndSession(anyString(), anyLong());

        verify(sessionRatingRepository, times(1)).findByCreatorLoginAndSessionId(anyString(), anyLong());
    }
}