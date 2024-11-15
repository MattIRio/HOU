package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MyNotification;
import com.heroesOfUbersharik.model.TeamMembersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyNotificationRepository extends JpaRepository<MyNotification, Long> {
    List<MyNotification> findAllByUserId(Integer userId);
    void deleteById(Integer id);
}
