package com.itech.kinopoisk.service;

import com.itech.kinopoisk.model.dto.AuthenticationRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationRestService {

    ResponseEntity<?> login(AuthenticationRequestDTO requestDTO);

}
