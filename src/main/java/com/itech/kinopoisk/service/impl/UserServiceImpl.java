package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.exceptions.TooLowAccessException;
import com.itech.kinopoisk.model.Role;
import com.itech.kinopoisk.repository.UserRepository;
import com.itech.kinopoisk.service.AccessRoleService;
import com.itech.kinopoisk.service.SendEmailService;
import com.itech.kinopoisk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AccessRoleService accessRoleService;

    private final PasswordEncoder passwordEncoder;

    private final SendEmailService sendEmailService;

    @Override
    public User add(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(accessRoleService.findByName(Role.USER));

        sendEmailService.sendActivationCode(user.getEmail(), "Film2Night", "You are registered in kinopoisk group watching film service.");

        return userRepository.save(user);
    }

    @Override
    public void updateRole(Long id, String role) {
        List<Role> list = Arrays.stream(Role.values()).collect(Collectors.toList());
        User user;
        if(list.contains(Role.valueOf(role.toUpperCase(Locale.ROOT)))) {
            if(findByLogin(Utils.getLogin()).getRole().getId() <= accessRoleService.findByName(Role.valueOf(role.toUpperCase(Locale.ROOT))).getId()) {
                user = findById(id);
                user.setRole(accessRoleService.findByName(Role.valueOf(role.toUpperCase(Locale.ROOT))));
            }
            else {
                log.error("You can`t up access higher then your`s.");
                throw new TooLowAccessException("You can`t up access higher then your`s. Your - " + findByLogin(Utils.getLogin()).getRole().getName() + ", you want set - " + role + ".");
            }
        }
        else {
            log.error("No element with such role - {}.", role);
            throw new NoSuchElementException(role);
        }

        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> {
            log.error("No element with such login - {}.", login);
            throw new NoSuchElementException("login - " + login + ".");
        });
    }

    @Override
    public void delete() {
        userRepository.deleteById(findByLogin(Utils.getLogin()).getId());
    }
}
