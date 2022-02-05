package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.Session;

import java.util.List;

public interface SessionService {

    //create session
    Session addSession(Session session);

    //find all sessions
    List<Session> findAllAvailableSession();

    //update session
    Session updateSession(Long id, Session session);

    //cancel(?delete) session
    void deleteSession(Long id);
}
