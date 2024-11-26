package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.*;
import com.heroesOfUbersharik.repository.MyTeamMembersRepository;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyTeamRequestsRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.*;

@Controller
public class MainPageController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;
    @Autowired
    private MyTeamMembersRepository teamMembersRepository;
    @Autowired
    private MyTeamRequestsRepository requestsRepository;

//Sends all information about the user and his team to his home page
    @GetMapping("/mainpage")
    public String handleAdminHome(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpSession session) {
        session.setAttribute("username", userRepository.findByEmail(userDetails.getUsername()).get().getName());
        MyUser localUser = userRepository.findByEmail(userDetails.getUsername()).get();


        model.addAttribute("Name", localUser.getName());
        model.addAttribute("Country", localUser.getCountry());
        model.addAttribute("English_level", localUser.getEnglishLvl());
        model.addAttribute("Your_Main_Career", localUser.getMainCareer());
        model.addAttribute("All_careers", localUser.getAllCareers());
        model.addAttribute("Difficulty", localUser.getDifficulty());
        model.addAttribute("Game_mode", localUser.getGameMode());
        model.addAttribute("Days", localUser.getPlayingDays());
        model.addAttribute("time", localUser.getPlayingHours());
        model.addAttribute("Difficulty", localUser.getDifficulty());


        if (localUser.getChatRoomId() != null){
            Optional <TeamModel> usersTeam = teamRepository.findBychatRoomId(localUser.getChatRoomId());

            model.addAttribute("teamName",usersTeam.get().getTeamName());
            model.addAttribute("teamDifficulty", usersTeam.get().getTeamsDifficulty());
            model.addAttribute("teamCountry", usersTeam.get().getTeamsCountry());
            model.addAttribute("teamGameMode", usersTeam.get().getTeamsGameMode());
            model.addAttribute("teamPlayingTime", usersTeam.get().getTeamsPlayingTime());
            model.addAttribute("teamPlayingDays", usersTeam.get().getTeamsPlayingDays());


        }
        return "mainpage";
    }

//Returns all members of the current team
    @GetMapping("/mainpage/teammembers")
    public ResponseEntity<Map<String, Object>> teammembers(@AuthenticationPrincipal UserDetails currentUser) {
        ArrayList<MyUser> membersModelArrayList = new ArrayList<>();

        Optional<MyUser> localUser = userRepository.findByEmail(currentUser.getUsername());
        Optional<TeamModel> currentTeam = teamRepository.findByTeamsCreatorID(localUser.get().getId());


        for (TeamMembersModel membersModel : teamMembersRepository.findAll()) {
            if (membersModel.getTeamOwnerId() == teamMembersRepository.findByMemberId(localUser.get().getId()).get().getTeamOwnerId()) {
                MyUser trueUser = userRepository.findByid(membersModel.getMemberId()).get();
                membersModelArrayList.add(trueUser);
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("members", membersModelArrayList);
        return ResponseEntity.ok(response);
    }


//Processes changes to user information
    @PostMapping("/submitForm1")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addDataToUserProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TeamModel teamModel) {
        Map<String, Object> response = new HashMap<>();
        Optional<MyUser> user = userRepository.findByEmail(userDetails.getUsername());

        MyUser existingUser = user.get();
        TeamModel existingTeamModel =  new TeamModel();

        if (userModel.getAllCareers() != null){
        existingUser.setAllCareers(userModel.getAllCareers());}

        if (userModel.getDifficulty() != null){
            existingUser.setDifficulty(userModel.getDifficulty());}

        if (userModel.getCountry() != null){
            existingUser.setCountry(userModel.getCountry());}

        if (userModel.getEnglishLvl() != null){
            existingUser.setEnglishLvl(userModel.getEnglishLvl());}

        if (userModel.getGameMode() != null){
            existingUser.setGameMode(userModel.getGameMode());}

        if (userModel.getMainCareer() != null){
            existingUser.setMainCareer(userModel.getMainCareer());}

        if (userModel.getPlayingDays() != null){
            existingUser.setPlayingDays(userModel.getPlayingDays());}

        if (userModel.getPlayingHours() != null){
            existingUser.setPlayingHours(userModel.getPlayingHours());}

        if (userModel.getName().length() <= 4 || userModel.getName().length() >= 20 ) {
            response.put("success", false);
            response.put("errorMessage", "The name must be 4 to 20 characters long");
            return ResponseEntity.badRequest().body(response);
        } else {
            existingUser.setName(userModel.getName().substring(0, 1).toUpperCase() +userModel.getName().substring(1));
        }
            userRepository.save(existingUser);

        response.put("success", true);
        response.put("message", "Profile updated successfully.");
        return ResponseEntity.ok(response);
    }

//Returns current user id
    @RestController
    public class UserController {
        @GetMapping("/api/getuserid")
        public ResponseEntity<Map<String, Object>> getUserId(Principal principal) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", userRepository.findByEmail(principal.getName()).get().getId());
            return ResponseEntity.ok(response);
        }
    }
}
