package com.itech.kinopoisk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_ru", unique = true)
    private String nameRu;

    @Column(name = "name_en", unique = true)
    private String nameEn;

    @Column(name = "year")
    @NotBlank
    private String year;

    @Column(name = "length")
    @NotBlank
    private String length;

    @Column(name = "rating")
    @NotNull
    @PositiveOrZero
    private Double rating;

    @Column(name = "rating_vote_count")
    @NotNull
    @Positive
    private Long ratingVoteCount;

    @Column(name = "poster_url")
    @NotBlank
    private String poster_url;

    @Column(name = "poster_url_preview")
    @NotBlank
    private String poster_url_preview;

    @JsonIgnore
    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CountryOfFilm> countryOfFilmList;

    @JsonIgnore
    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<GenreOfFilm> genreOfFilmList;
}
