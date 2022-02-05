package com.itech.kinopoisk.model.dto;

import com.itech.kinopoisk.entity.Session;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionRatingDTO {

    private Long sessionId;

    @PositiveOrZero
    private Double rating;

    @NotBlank
    private String txt;
}
