package com.itech.kinopoisk.model.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilmFilterRequest {

    private String nameRu;

    private String nameEn;

    private String year;

    private double rating;
}
