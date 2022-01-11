package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.CountryOfFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryOfFilmRepository extends JpaRepository<CountryOfFilm, Long> {
}
