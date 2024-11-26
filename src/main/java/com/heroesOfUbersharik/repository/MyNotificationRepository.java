package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MyNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MyNotificationRepository extends JpaRepository<MyNotification, Long> {
    List<MyNotification> findAllByUserId(Integer userId);
    void deleteById(Integer id);
}
