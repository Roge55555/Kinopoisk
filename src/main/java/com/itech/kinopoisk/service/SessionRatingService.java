package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.SessionRating;

import java.util.Optional;

public interface SessionRatingService {
    //add rating
    SessionRating add(SessionRating sessionRating);

    Optional<SessionRating> findByCreatorAndSession(String creatorLogin, Long sessionId);
}
