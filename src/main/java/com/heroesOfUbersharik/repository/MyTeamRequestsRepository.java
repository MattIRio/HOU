package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.TeamRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyTeamRequestsRepository extends JpaRepository<TeamRequestModel, Long> {
    Optional<TeamRequestModel> findByTeamOwnerId(Integer teamOwnerId);
    List<TeamRequestModel> findAllByTeamOwnerId(Integer teamOwnerId);
    void deleteBySenderId(Integer senderId);
    Optional<TeamRequestModel> findByTeamOwnerIdAndSenderId(Integer teamOwnerId, Integer senderId);

}
