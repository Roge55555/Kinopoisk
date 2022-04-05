package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.AccessRole;
import com.itech.kinopoisk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {

    Optional<AccessRole> findByName(Role name);

}
