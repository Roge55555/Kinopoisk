package com.itech.kinopoisk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    @NotBlank
    @NotNull
    @Size(min = 4)
    private String login;

    @Size(min = 5)
    private String password;

}
