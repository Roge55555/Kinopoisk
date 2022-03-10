package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.SessionMessage;
import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.repository.SessionMessageRepository;
import com.itech.kinopoisk.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SessionMessageServiceImplTest {

    @InjectMocks
    SessionMessageServiceImpl sessionMessageService;

    @Mock
    SessionMessageRepository sessionMessageRepository;

    @Mock
    UserService userService;

    @Test
    @DisplayName("Add message in session")
    void add() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            utilities.when(Utils::getLogin).thenReturn("roge");
            when(userService.findByLogin("roge")).thenReturn(new User());

            sessionMessageService.add(new SessionMessage());

            verify(sessionMessageRepository, times(1)).save(any(SessionMessage.class));
        }
    }
}