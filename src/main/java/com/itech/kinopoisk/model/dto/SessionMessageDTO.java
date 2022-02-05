package com.itech.kinopoisk.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionMessageDTO {

    private Long sessionId;

    @NotBlank
    private String txt;
}
