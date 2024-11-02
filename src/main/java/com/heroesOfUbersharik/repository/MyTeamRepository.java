package com.heroesOfUbersharik.repository;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.model.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyTeamRepository extends JpaRepository<TeamModel, Long> {
        Optional<TeamModel> findByTeamName(String teamName);
        Optional<TeamModel> findByTeamsCreatorID(Integer teamsCreatorID);
    }
