package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.AccessRole;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.model.Role;
import com.itech.kinopoisk.repository.AccessRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccessRoleServiceImplTest {

    @InjectMocks
    AccessRoleServiceImpl accessRoleService;

    @Mock
    AccessRoleRepository accessRoleRepository;

    @Test
    @DisplayName("Successful finding access role by his id")
    void findByIdSuccess() {
        AccessRole role = new AccessRole();
        role.setId(2L);
        role.setName(Role.USER);

        when(accessRoleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(role));

        assertEquals(role, accessRoleService.findById(2L));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing access role by id")
    void findByIdException() {
        when(accessRoleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accessRoleService.findById(3L)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Successful finding access role by his name")
    void findByRoleNameSuccess() {
        AccessRole role = new AccessRole();
        role.setId(2L);
        role.setName(Role.USER);

        when(accessRoleRepository.findByName(any(Role.class))).thenReturn(java.util.Optional.of(role));

        assertEquals(role, accessRoleService.findByName(Role.USER));
    }

    @Test
    @DisplayName("Exception when we trying to find not existing access role by his name")
    void findByRoleNameException() {
        when(accessRoleRepository.findByName(any(Role.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accessRoleService.findByName(Role.USER)).isInstanceOf(NoSuchElementException.class);
    }
}