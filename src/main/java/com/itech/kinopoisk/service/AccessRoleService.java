package com.itech.kinopoisk.service;


import com.itech.kinopoisk.entity.AccessRole;
import com.itech.kinopoisk.model.Role;

public interface AccessRoleService {

    AccessRole findById(Long id);

    AccessRole findByName(Role name);

}
