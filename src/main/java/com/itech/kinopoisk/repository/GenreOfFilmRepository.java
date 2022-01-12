package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.GenreOfFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreOfFilmRepository extends JpaRepository<GenreOfFilm, Long> {

    Optional<GenreOfFilm> findByFilmIdAndGenreName(Long filmId, String genreName);

    Optional<GenreOfFilm> findByFilmIdAndGenreId(Long filmId, Long genreId);

    List<GenreOfFilm> findByFilmId(Long filmId);

    void deleteByFilmIdAndGenreName(Long filmId, String genreName);

    void deleteByFilmId(Long filmId);
}
