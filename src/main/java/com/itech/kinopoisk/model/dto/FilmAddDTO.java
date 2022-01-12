package com.itech.kinopoisk.model.dto;

import com.itech.kinopoisk.entity.Country;
import com.itech.kinopoisk.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilmAddDTO {

    private String nameRu;

    private String nameEn;

    @NotBlank
    private String year;

    @NotBlank
    private String filmLength;

    @NotNull
    private List<Country> countries;

    @NotNull
    private List<Genre> genres;

    @NotNull
    @PositiveOrZero
    private Double rating;

    @NotNull
    @Positive
    private Long ratingVoteCount;

    @NotBlank
    private String posterUrl;

    @NotBlank
    private String posterUrlPreview;

}
