package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.entity.SessionRating;
import com.itech.kinopoisk.model.dto.SessionRatingDTO;
import com.itech.kinopoisk.service.SessionRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class SessionRatingController {

    private final SessionRatingService sessionRatingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('user:permission')")
    public void addSessionRating(@Valid @RequestBody SessionRatingDTO sessionRatingDTO) {
        SessionRating sessionRating = SessionRating.builder()
                .session(Session.builder().id(sessionRatingDTO.getSessionId()).build())
                .date(LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS))
                .rating(sessionRatingDTO.getRating())
                .txt(sessionRatingDTO.getTxt()).build();
        sessionRatingService.add(sessionRating);
    }
}
