package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.TeamMembersModel;
import com.heroesOfUbersharik.model.TeamModel;
import com.heroesOfUbersharik.model.TeamRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyTeamMembersRepository extends JpaRepository<TeamMembersModel, Long> {
    Optional<TeamMembersModel> findByMemberId(Integer memberId);
    Optional<TeamMembersModel> findByTeamName(String teamName);
    Optional<TeamMembersModel> findByTeamOwnerId(Integer teamOwnerId);

    }
