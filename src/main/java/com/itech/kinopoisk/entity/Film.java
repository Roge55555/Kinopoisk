package com.itech.kinopoisk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_ru", unique = true)
    @NotBlank
    private String name_ru;

    @Column(name = "name_en", unique = true)
    @NotBlank
    private String name_en;

    @Column(name = "year")
    @NotBlank
    private String year;

    @Column(name = "length")
    @NotBlank
    private String length;

    @Column(name = "rating")
    @NotNull
    private Double rating;

    @Column(name = "rating_vote_count")
    @NotNull
    private Long ratingVoteCount;

    @Column(name = "poster_url")
    @NotBlank
    private String poster_url;

    @Column(name = "poster_url_preview")
    @NotBlank
    private String poster_url_preview;

    @JsonIgnore
    @OneToMany(mappedBy = "films", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CountryOfFilm> countryOfFilmList;

    @JsonIgnore
    @OneToMany(mappedBy = "films", cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<GenreOfFilm> genreOfFilmList;
}
