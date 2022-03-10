package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.Utils;
import com.itech.kinopoisk.entity.AccessRole;
import com.itech.kinopoisk.entity.User;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.exceptions.TooLowAccessException;
import com.itech.kinopoisk.model.Role;
import com.itech.kinopoisk.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    AccessRoleServiceImpl accessRoleService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    SendEmailService sendEmailService;

    @Test
    @DisplayName("Successful adding new user")
    void add() {
        User user = User.builder().build();

        when(passwordEncoder.encode(anyString())).thenReturn(anyString());
        when(accessRoleService.findByName(Role.USER)).thenReturn(any(AccessRole.class));
        userService.add(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Successful updating user role")
    void updateRole() {

        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(userRepository.findByLogin("rogE")).thenReturn(java.util.Optional.of(User.builder().role(AccessRole.builder().id(1L).build()).build()));
            when(accessRoleService.findByName(any(Role.class))).thenReturn(AccessRole.builder().id(3L).build());
            when(userRepository.findById(6L)).thenReturn(java.util.Optional.of(User.builder().build()));
            userService.updateRole(6L, "user");

            verify(userRepository, times(1)).save(any(User.class));
        }
    }

    @Test
    @DisplayName("Exception when we trying to update role of user to not existing")
    void updateRoleExceptionRoleName() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(userRepository.findByLogin("rogE")).thenReturn(java.util.Optional.of(User.builder().role(AccessRole.builder().id(1L).build()).build()));
            when(accessRoleService.findByName(any(Role.class))).thenReturn(AccessRole.builder().id(3L).build());
            when(userRepository.findById(6L)).thenReturn(java.util.Optional.of(User.builder().build()));


            assertThatThrownBy(() -> userService.updateRole(6L, "uuser"))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("Successful updating user role")
    void updateRoleExceptionTooLowAccess() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(userRepository.findByLogin("rogE")).thenReturn(java.util.Optional.of(User.builder().role(AccessRole.builder().id(3L).build()).build()));
            when(accessRoleService.findByName(any(Role.class))).thenReturn(AccessRole.builder().id(2L).build());
            when(userRepository.findById(6L)).thenReturn(java.util.Optional.of(User.builder().build()));


            assertThatThrownBy(() -> userService.updateRole(6L, "moderator"))
                    .isInstanceOf(TooLowAccessException.class);
        }
    }

    @Test
    @DisplayName("Successful finding user by id")
    void findById() {
        User user = User.builder().id(23L).build();

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));

        assertEquals(user, userService.findById(23L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing user by id")
    void findByIdException() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> userService.findById(98L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding user by login")
    void findByLogin() {
        User user = User.builder().login("testUser").build();

        when(userRepository.findByLogin(anyString())).thenReturn(java.util.Optional.of(user));

        assertEquals(user, userService.findByLogin("testUser"));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing user by login")
    void findByLoginException() {
        when(userRepository.findByLogin(anyString())).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> userService.findByLogin("testUser"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful deleting user by id")
    void deleteUser() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {

            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(userRepository.findByLogin(anyString())).thenReturn(java.util.Optional.of(User.builder().id(11L).role(AccessRole.builder().id(2L).build()).build()));
            when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(User.builder().role(AccessRole.builder().id(3L).build()).build()));
            userService.delete(11L);

            verify(userRepository, times(1)).deleteById(11L);
        }
    }

    @Test
    @DisplayName("Exception when we trying to delete User without permission for it by id")
    void deleteUserException() {

        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {

            utilities.when(Utils::getLogin).thenReturn("rogE");
            when(userRepository.findByLogin(anyString())).thenReturn(java.util.Optional.of(User.builder().id(14L).role(AccessRole.builder().id(3L).build()).build()));
            when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(User.builder().role(AccessRole.builder().id(3L).build()).build()));

            assertThatThrownBy(() -> userService.delete(11L))
                    .isInstanceOf(TooLowAccessException.class);
        }


    }
}