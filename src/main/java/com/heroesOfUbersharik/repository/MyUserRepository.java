package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MyUser;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// просто создание обьекта который представляет из себя указанны в app.prop базу данных

    public interface MyUserRepository extends JpaRepository<MyUser, Long> {
        Optional<MyUser> findByEmail(String email);
        Optional<MyUser> findByName(String name);
        Optional<MyUser> findByid(Integer id);
    }
