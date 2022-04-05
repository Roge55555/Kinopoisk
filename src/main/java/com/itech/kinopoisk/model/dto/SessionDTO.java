package com.itech.kinopoisk.model.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionDTO {

    private Long filmId;

    @Size(min = 3)
    @NotBlank
    private String name;

    private String description;

    @Future
    private LocalDateTime dateStart;

    @Positive
    private Long maxUserCount;
}
