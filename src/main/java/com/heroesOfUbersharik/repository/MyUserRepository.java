package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// просто создание обьекта который представляет из себя указанны в app.prop базу данных

    public interface MyUserRepository extends JpaRepository<MyUser, Long> {
        Optional<MyUser> findByEmail(String email);
    }
