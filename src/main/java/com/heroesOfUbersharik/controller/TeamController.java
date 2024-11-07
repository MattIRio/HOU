package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.*;
import com.heroesOfUbersharik.repository.MyTeamMembersRepository;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyTeamRequestsRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class TeamController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;
    @Autowired
    private MyTeamRequestsRepository requestsRepository;
    @Autowired
    private MyTeamMembersRepository membersRepository;

    @PostMapping("/submitForm2")
    public ResponseEntity<Map<String, Object>> addDataToTeamProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TeamModel teamModel) {

        Optional<MyUser> user = userRepository.findByEmail(userDetails.getUsername());
        Map<String, Object> response = new HashMap<>();
        MyUser existingUser = user.get();
        TeamModel existingTeamModel = new TeamModel();


        if (teamRepository.findByTeamsCreatorID(existingUser.getId()).isPresent()) {
            response.put("success", false);
            response.put("errorMessage", "You are already a member of the team");
            return ResponseEntity.badRequest().body(response);
        }
        if (teamModel.getTeamName().length() < 4 || teamModel.getTeamName().length() > 20) {
            response.put("success", false);
            response.put("errorMessage", "The name must be 4 to 20 characters long");
            return ResponseEntity.badRequest().body(response);
        }
        if (teamModel.getTeamName().equals("Choose your option") || teamModel.getTeamsCountry().equals("Choose your option") || teamModel.getTeamsDifficulty().equals("Choose your option") || teamModel.getTeamsPlayingDays().equals("Choose your option") || teamModel.getTeamsGameMode().equals("Choose your option") || teamModel.getTeamsPlayingTime().equals("Choose your option")) {
            response.put("success", false);
            response.put("errorMessage", "All fields must be filled in");
            return ResponseEntity.badRequest().body(response);
        }

        //TEAM MODEL
        existingTeamModel.setTeamsCreatorID(existingUser.getId());

        if (teamModel.getTeamName() != "" && teamModel.getTeamName() != null) {
            existingTeamModel.setTeamName(teamModel.getTeamName());
        }
        if (teamModel.getTeamsDifficulty() != "" && teamModel.getTeamsDifficulty() != null) {
            existingTeamModel.setTeamsDifficulty(teamModel.getTeamsDifficulty());
        }
        if (teamModel.getTeamsCountry() != "" && teamModel.getTeamsCountry() != null) {
            existingTeamModel.setTeamsCountry(teamModel.getTeamsCountry());
        }
        if (teamModel.getTeamsGameMode() != "" && teamModel.getTeamsGameMode() != null) {
            existingTeamModel.setTeamsGameMode(teamModel.getTeamsGameMode());
        }
        if (teamModel.getTeamsPlayingTime() != "" && teamModel.getTeamsPlayingTime() != null) {
            existingTeamModel.setTeamsPlayingTime(teamModel.getTeamsPlayingTime());
        }
        if (teamModel.getTeamsPlayingDays() != "" && teamModel.getTeamsPlayingDays() != null) {
            existingTeamModel.setTeamsPlayingDays(teamModel.getTeamsPlayingDays());
        }
        existingTeamModel.setAmountOfMembers(1);
        teamRepository.save(existingTeamModel);
        response.put("success", true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/mainpage/teams")
    public ResponseEntity<ArrayList <TeamModel>> teamList(@AuthenticationPrincipal TeamModel teamModel, @AuthenticationPrincipal UserDetails localuser) {
        ArrayList <TeamModel> teamMap = new ArrayList<>();
        List<TeamModel> allTeams = teamRepository.findAll();


        for (TeamModel currentUser : allTeams) {
            Boolean isActive = true;
            if (userRepository.findByEmail(localuser.getUsername()).get().getId() == currentUser.getTeamsCreatorID()) {              //IF USER IS OWNER
                isActive = false;
            } else if (requestsRepository.findByTeamOwnerIdAndSenderId(currentUser.getTeamsCreatorID(), userRepository.findByEmail(localuser.getUsername()).get().getId()).isPresent()) {    //IF USER ALREADY SENT REQUEST
                isActive = false;
            }

                TeamModel teamModel1 = new TeamModel(
                        currentUser.getTeamName(),
                        currentUser.getTeamsCountry(),
                        currentUser.getTeamsDifficulty(),
                        currentUser.getTeamsGameMode(),
                        currentUser.getTeamsPlayingDays(),
                        currentUser.getTeamsPlayingTime(),
                        isActive);
                teamMap.add(teamModel1);

        }

            return ResponseEntity.ok(teamMap);
    }

    @PostMapping("/joinrequest")
    public ResponseEntity<Map<String, Object>> sendingRequest(@RequestBody TeamModel teamModel, @AuthenticationPrincipal UserDetails currentUser) {
        Map<String, Object> response = new HashMap<>();
        Optional<TeamModel> currentTeamOpt = teamRepository.findByTeamName(teamModel.getTeamName());


        TeamModel currentTeam = currentTeamOpt.get();
        currentTeam.setRequests_amount(currentTeam.getRequests_amount() + 1);

        TeamRequestModel teamRequestModel = new TeamRequestModel(
                userRepository.findByEmail(currentUser.getUsername()).get().getId(),
                currentTeam.getTeamsCreatorID(),
                LocalDate.now()
        );
        requestsRepository.save(teamRequestModel);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mainpage/requestsList")
    public ResponseEntity<ArrayList <MyUser>> requestList(@AuthenticationPrincipal TeamModel teamModel, @AuthenticationPrincipal UserDetails localuser) {
        ArrayList <MyUser> requestMap = new ArrayList<>();
        List<TeamRequestModel> allTeams = requestsRepository.findAll();


        for (TeamRequestModel currentUser : allTeams) {
            Boolean isActive = true;
           if (currentUser.getTeamOwnerId() == userRepository.findByEmail(localuser.getUsername()).get().getId()){
               MyUser localUser = userRepository.findByid(currentUser.getSenderId()).get();

               MyUser userModel = new MyUser(
                       localUser.getName(),
                       localUser.getCountry(),
                       localUser.getEnglishLvl(),
                       localUser.getMainCareer(),
                       localUser.getAllCareers(),
                       localUser.getPlayingHours(),
                       localUser.getPlayingDays(),
                       localUser.getDifficulty(),
                       localUser.getGameMode());
               requestMap.add(userModel);
           }
        }

        return ResponseEntity.ok(requestMap);
    }

    @Transactional
    @PostMapping ("/mainpage/addmember")
    public ResponseEntity<Map<String, Object>> addmember(@RequestParam String userName, @AuthenticationPrincipal UserDetails currentUser) {
        ArrayList<TeamRequestModel> requestModelArrayList = new ArrayList<>();
        ArrayList<MyUser> membersModelArrayList = new ArrayList<>();

        Optional<MyUser> localUser = userRepository.findByEmail(currentUser.getUsername());
        Optional<TeamModel> currentTeam = teamRepository.findByTeamsCreatorID(localUser.get().getId());


        currentTeam.get().setRequests_amount(currentTeam.get().getRequestsAmount() -1);
        currentTeam.get().setAmountOfMembers(currentTeam.get().getAmountOfMembers() +1);


        requestsRepository.deleteBySenderId(userRepository.findByName(userName).get().getId());

        TeamMembersModel member = new TeamMembersModel(userRepository.findByName(userName).get().getId(), localUser.get().getId(), teamRepository.findByTeamsCreatorID(localUser.get().getId()).get().getTeamName());
        membersRepository.save(member);

        for (TeamRequestModel requestModel : requestsRepository.findAll()){
            requestModelArrayList.add(requestModel);
        }
        for (TeamMembersModel membersModel : membersRepository.findAll()){
            if (membersModel.getTeamOwnerId() == localUser.get().getId()){
                MyUser trueUser = userRepository.findByid(membersModel.getMemberId()).get();
                membersModelArrayList.add(trueUser);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("requests", requestModelArrayList);
        response.put("members", membersModelArrayList);

        return ResponseEntity.ok(response);
    }

}
