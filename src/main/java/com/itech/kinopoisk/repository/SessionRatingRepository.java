package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.SessionRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRatingRepository extends JpaRepository<SessionRating, Long> {
}
