package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.model.MyUserDetailService;
import com.heroesOfUbersharik.model.TeamModel;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class TeamController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;


    @PostMapping("/submitForm2")
    public ResponseEntity<Map<String, Object>> addDataToTeamProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TeamModel teamModel) {

        Optional<MyUser> user = userRepository.findByEmail(userDetails.getUsername());
        Map<String, Object> response = new HashMap<>();
        MyUser existingUser = user.get();
        TeamModel existingTeamModel =  new TeamModel();


        if (teamRepository.findByTeamsCreatorID(existingUser.getId()).isPresent()){
            response.put("success", false);
            response.put("errorMessage", "You are already a member of the team");
            return ResponseEntity.badRequest().body(response);
        }
        if (teamModel.getTeamName().length() < 4 || teamModel.getTeamName().length() > 20 ){
            response.put("success", false);
            response.put("errorMessage", "The name must be 4 to 20 characters long");
            return ResponseEntity.badRequest().body(response);
        }
        if (teamModel.getTeamName().equals("Choose your option")|| teamModel.getTeamsCountry().equals( "Choose your option" )|| teamModel.getTeamsDifficulty().equals( "Choose your option" )|| teamModel.getTeamsPlayingDays().equals( "Choose your option" )|| teamModel.getTeamsGameMode().equals( "Choose your option" )|| teamModel.getTeamsPlayingTime().equals( "Choose your option" ) ){
            response.put("success", false);
            response.put("errorMessage", "All fields must be filled in");
            return ResponseEntity.badRequest().body(response);
        }

        //TEAM MODEL
        existingTeamModel.setTeamsCreatorID(existingUser.getId());

        if (teamModel.getTeamName() != "" && teamModel.getTeamName() != null){
            existingTeamModel.setTeamName(teamModel.getTeamName());
        }
        if (teamModel.getTeamsDifficulty() != "" && teamModel.getTeamsDifficulty() != null){
            existingTeamModel.setTeamsDifficulty(teamModel.getTeamsDifficulty());
        }
        if (teamModel.getTeamsCountry() != "" && teamModel.getTeamsCountry() != null){
            existingTeamModel.setTeamsCountry(teamModel.getTeamsCountry());
        }
        if (teamModel.getTeamsGameMode() != "" && teamModel.getTeamsGameMode() != null){
            existingTeamModel.setTeamsGameMode(teamModel.getTeamsGameMode());
        }
        if (teamModel.getTeamsPlayingTime() != "" && teamModel.getTeamsPlayingTime() != null){
            existingTeamModel.setTeamsPlayingTime(teamModel.getTeamsPlayingTime());
        }
        if (teamModel.getTeamsPlayingDays() != "" && teamModel.getTeamsPlayingDays() != null){
            existingTeamModel.setTeamsPlayingDays(teamModel.getTeamsPlayingDays());
        }
        existingTeamModel.setAmountOfMembers(1);
        teamRepository.save(existingTeamModel);
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/mainpage/teams")
    public ResponseEntity <List<TeamModel>> teamList(@AuthenticationPrincipal TeamModel teamModel) {
        List<TeamModel> teamList = new ArrayList<>();
        List<TeamModel> allTeams = teamRepository.findAll();

        for (TeamModel currentUser : allTeams) {
            TeamModel teamModel1 = new TeamModel(
                    currentUser.getTeamsPlayingTime(),
                    currentUser.getTeamsPlayingDays(),
                    currentUser.getTeamsGameMode(),
                    currentUser.getTeamsDifficulty(),
                    currentUser.getTeamsCountry(),
                    currentUser.getTeamName());

            teamList.add(teamModel1);
        }
        return ResponseEntity.ok(teamList);
    }

    @PostMapping("/joinrequest")
    public ResponseEntity <List<TeamModel>> sendingRequest ()
}
