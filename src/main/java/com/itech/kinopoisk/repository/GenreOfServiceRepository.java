package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.GenreOfFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreOfServiceRepository extends JpaRepository<GenreOfFilm, Long> {
}
