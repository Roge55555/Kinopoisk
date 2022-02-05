package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.SessionMessage;
import com.itech.kinopoisk.repository.SessionMessageRepository;
import com.itech.kinopoisk.service.SessionMessageService;
import com.itech.kinopoisk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SessionMessageServiceImpl implements SessionMessageService {

    private final SessionMessageRepository sessionMessageRepository;

    private final UserService userService;

    @Override
    public SessionMessage add(SessionMessage sessionMessage) {
        sessionMessage.setCreator(userService.findByLogin(Utils.getLogin()));
        return sessionMessageRepository.save(sessionMessage);
    }
}
