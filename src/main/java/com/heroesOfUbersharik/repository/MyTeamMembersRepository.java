package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.TeamMembersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyTeamMembersRepository extends JpaRepository<TeamMembersModel, Long> {
    Optional<TeamMembersModel> findByMemberId(Integer memberId);
    void deleteByMemberId(Integer memberId);
    Optional<TeamMembersModel> findByTeamName(String teamName);
    Optional<TeamMembersModel> findByTeamOwnerId(Integer teamOwnerId);
    List<TeamMembersModel> findAllByTeamName(String teamName);

    }
