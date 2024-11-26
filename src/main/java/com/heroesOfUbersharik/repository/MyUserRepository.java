package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



    public interface MyUserRepository extends JpaRepository<MyUser, Long> {
        Optional<MyUser> findByEmail(String email);
        Optional<MyUser> findByName(String name);
        Optional<MyUser> findByid(Integer id);
        List<MyUser> findAllBychatRoomId(Integer chatRoomId);
    }
