package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.User;

public interface UserService {

    User add(User user);

    void updateRole(Long id, String role);

    User findById(Long id);

    User findByLogin(String login);

    void delete(Long id);
}
