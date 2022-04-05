package com.itech.kinopoisk.service.impl;

import com.itech.kinopoisk.entity.AccessRole;
import com.itech.kinopoisk.exceptions.NoSuchElementException;
import com.itech.kinopoisk.model.Role;
import com.itech.kinopoisk.repository.AccessRoleRepository;
import com.itech.kinopoisk.service.AccessRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccessRoleServiceImpl implements AccessRoleService {

    private final AccessRoleRepository accessRoleRepository;

    @Override
    public AccessRole findById(Long id) {
        return accessRoleRepository.findById(id).orElseThrow(() -> {
            log.error("No element with such id - {}.", id);
            throw new NoSuchElementException(id);
        });
    }

    @Override
    public AccessRole findByName(Role name) {
        return accessRoleRepository.findByName(name).orElseThrow(() -> {
            log.error("No element with such login - {}.", name);
            throw new NoSuchElementException(name.name());
        });
    }

}
