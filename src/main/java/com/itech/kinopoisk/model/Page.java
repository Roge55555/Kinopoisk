package com.itech.kinopoisk.model;

import com.itech.kinopoisk.model.dto.FilmUpdateDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Page {
    private Long pagesCount;

    private List<FilmUpdateDTO> films;
}
