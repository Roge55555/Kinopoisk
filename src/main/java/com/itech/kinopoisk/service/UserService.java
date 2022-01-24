package com.itech.kinopoisk.service;

import com.itech.kinopoisk.entity.User;

public interface UserService {

    User findByLogin(String login);
}
