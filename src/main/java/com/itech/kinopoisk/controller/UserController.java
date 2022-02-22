package com.itech.kinopoisk.controller;

import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.model.dto.UserDTO;
import com.itech.kinopoisk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody UserDTO userDTO) {
        User user = User.builder()
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();
        return userService.add(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('moderator:permission') || hasAuthority('admin:permission')")
    public void updateUser(@PathVariable("id") Long id, @RequestBody String role) {
        userService.updateRole(id, role);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('user:permission') || hasAuthority('moderator:permission') || hasAuthority('admin:permission')")
    public void deleteUser() {
        userService.delete();
    }
}
