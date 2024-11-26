package com.heroesOfUbersharik.repository;


import com.heroesOfUbersharik.model.TeamModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyTeamRepository extends JpaRepository<TeamModel, Long> {
        Optional<TeamModel> findByTeamName(String teamName);
        Optional<TeamModel> findByTeamsCreatorID(Integer teamsCreatorID);
        List <TeamModel> findAllByTeamsCreatorID(Integer teamsCreatorID);
        Optional<TeamModel> findBychatRoomId(Integer chatRoomId);
        List<TeamModel> findAll(Sort sort);


    }
