package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.model.MyUserDetailService;
import com.heroesOfUbersharik.repository.MyUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
@Controller
public class MainPageController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyUserRepository userRepository;



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

    @PostMapping("/mainpage")
    public String addDataToUserProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<MyUser> user = userRepository.findByEmail(userDetails.getUsername());

            MyUser existingUser = user.get();

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


}
