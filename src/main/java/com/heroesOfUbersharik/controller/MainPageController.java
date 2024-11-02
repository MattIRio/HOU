package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.model.MyUserDetailService;
import com.heroesOfUbersharik.model.TeamModel;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Controller
public class MainPageController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;



    @GetMapping("/mainpage")
    public String handleAdminHome(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpSession session) {
        session.setAttribute("username", userRepository.findByEmail(userDetails.getUsername()).get().getName());

        model.addAttribute("Name", userRepository.findByEmail(userDetails.getUsername()).get().getName());
        model.addAttribute("Country", userRepository.findByEmail(userDetails.getUsername()).get().getCountry());
        model.addAttribute("English_level", userRepository.findByEmail(userDetails.getUsername()).get().getEnglishLvl());
        model.addAttribute("Your_Main_Career", userRepository.findByEmail(userDetails.getUsername()).get().getMainCareer());
        model.addAttribute("All_careers", userRepository.findByEmail(userDetails.getUsername()).get().getAllCareers());
        model.addAttribute("Difficulty", userRepository.findByEmail(userDetails.getUsername()).get().getDifficulty());
        model.addAttribute("Game_mode", userRepository.findByEmail(userDetails.getUsername()).get().getGameMode());
        model.addAttribute("Days", userRepository.findByEmail(userDetails.getUsername()).get().getPlayingDays());
        model.addAttribute("time", userRepository.findByEmail(userDetails.getUsername()).get().getPlayingHours());
        model.addAttribute("Difficulty", userRepository.findByEmail(userDetails.getUsername()).get().getDifficulty());




        return "mainpage";
    }


    @GetMapping("/mainpage")
    public String teamList(@AuthenticationPrincipal TeamModel teamModel,)


    @PostMapping("/submitForm1")
    public String addDataToUserProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TeamModel teamModel) {

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

        if (userModel.getName() != null){
            existingUser.setName(userModel.getName().substring(0, 1).toUpperCase() +userModel.getName().substring(1));}

        if (userModel.getPlayingDays() != null){
            existingUser.setPlayingDays(userModel.getPlayingDays());}

        if (userModel.getPlayingHours() != null){
            existingUser.setPlayingHours(userModel.getPlayingHours());}

            userRepository.save(existingUser);
            return "redirect:/mainpage";
    }

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
        if (teamModel.getTeamName().length() < 4 || teamModel.getTeamName().length() > 15 ){
            response.put("success", false);
            response.put("errorMessage", "The name must be 4 to 15 characters long");
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

        teamRepository.save(existingTeamModel);
        response.put("success", true);

        return ResponseEntity.ok(response);
    }



}
