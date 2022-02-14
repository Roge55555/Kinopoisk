package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.entity.Session;
import com.itech.kinopoisk.entity.SessionMessage;
import com.itech.kinopoisk.model.dto.SessionMessageDTO;
import com.itech.kinopoisk.service.SessionMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class SessionMessageController {

    private final SessionMessageService sessionMessageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('user:permission')")
    public void addSessionRating(@Valid @RequestBody SessionMessageDTO sessionMessageDTO) {
        SessionMessage sessionMessage = SessionMessage.builder()
                .session(Session.builder().id(sessionMessageDTO.getSessionId()).build())
                .date(LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS))
                .txt(sessionMessageDTO.getTxt()).build();
        sessionMessageService.add(sessionMessage);
    }
}
