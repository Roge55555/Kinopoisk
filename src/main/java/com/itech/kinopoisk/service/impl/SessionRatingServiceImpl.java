package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.SessionRating;
import com.itech.kinopoisk.repository.SessionRatingRepository;
import com.itech.kinopoisk.service.SessionRatingService;
import com.itech.kinopoisk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SessionRatingServiceImpl implements SessionRatingService {

    private final SessionRatingRepository sessionRatingRepository;

    private final UserService userService;

    @Override
    public SessionRating add(SessionRating sessionRating) {
        sessionRating.setCreator(userService.findByLogin(Utils.getLogin()));
        return sessionRatingRepository.save(sessionRating);
    }
}
