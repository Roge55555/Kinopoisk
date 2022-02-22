package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.SessionRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Repository
public interface SessionRatingRepository extends JpaRepository<SessionRating, Long> {
    Optional<SessionRating> findByCreatorLoginAndSessionId(@NotBlank @Size(min = 4) String creatorLogin, Long sessionId);
}
