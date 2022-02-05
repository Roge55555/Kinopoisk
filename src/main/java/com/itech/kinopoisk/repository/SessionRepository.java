package com.itech.kinopoisk.repository;

import com.itech.kinopoisk.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    String FIND_AVAILABLE_SESSION = "select * from session as s where max_user_count > (select count(*) from user_of_session as us where us.session_id = s.id) and date_start > now();";

    @Query(value = FIND_AVAILABLE_SESSION, nativeQuery = true)
    List<Session> findAllByDateStartAndMaxUserCount();
}
