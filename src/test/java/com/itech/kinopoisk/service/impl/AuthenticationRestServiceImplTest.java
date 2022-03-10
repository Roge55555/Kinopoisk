package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.exceptions.JwtAuthenticationException;
import com.itech.kinopoisk.model.dto.AuthenticationRequestDTO;
import com.itech.kinopoisk.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationRestServiceImplTest {

    @InjectMocks
    AuthenticationRestServiceImpl authenticationRestService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserServiceImpl userService;

    @Mock
    JwtTokenProvider tokenProvider;

    @Test
    @DisplayName("Success login")
    void loginSuccess() {
        Map<Object, Object> response = new HashMap<>();

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("rogE", "55555"))).thenReturn(null);
        when(userService.findByLogin(any(String.class))).thenReturn(new User());
        when(tokenProvider.createToken(any(String.class), any(String.class))).thenReturn(any(String.class));
        assertEquals(ResponseEntity.ok(response).getStatusCode(), authenticationRestService.login(new AuthenticationRequestDTO("rogE", "55555")).getStatusCode());
    }

    @Test
    @DisplayName("Exception login")
    void loginException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new JwtAuthenticationException("JWT token invalid/expire", HttpStatus.UNAUTHORIZED));
        when(userService.findByLogin("wrong")).thenReturn(new User());
        when(tokenProvider.createToken(any(String.class), any(String.class))).thenReturn(any(String.class));

        assertEquals(new ResponseEntity<>("Invalid login/password!", HttpStatus.FORBIDDEN),
                authenticationRestService.login(new AuthenticationRequestDTO("wrong", "data")));
    }
}