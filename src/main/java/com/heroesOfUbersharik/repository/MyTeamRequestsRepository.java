package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.TeamModel;
import com.heroesOfUbersharik.model.TeamRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyTeamRequestsRepository extends JpaRepository<TeamRequestModel, Long> {
    Optional<TeamRequestModel> findByTeamOwnerId(Integer teamOwnerId);
    void deleteBySenderId(Integer senderId);
    Optional<TeamRequestModel> findByTeamOwnerIdAndSenderId(Integer teamOwnerId, Integer senderId);

}
