package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.CountryOfFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryOfFilmRepository extends JpaRepository<CountryOfFilm, Long> {

    Optional<CountryOfFilm> findByFilmIdAndCountryName(Long filmId, String countryName);

    Optional<CountryOfFilm> findByFilmIdAndCountryId(Long filmId, Long countryId);

    List<CountryOfFilm> findByFilmId(Long filmId);

    void deleteByFilmIdAndCountryName(Long filmId, String countryName);

    void deleteByFilmId(Long filmId);
}
