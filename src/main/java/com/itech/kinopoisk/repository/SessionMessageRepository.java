package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.SessionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionMessageRepository extends JpaRepository<SessionMessage, Long> {
}
