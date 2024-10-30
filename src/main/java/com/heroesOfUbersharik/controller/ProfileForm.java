package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProfileForm {

    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/profileform")
    public String handleUserHome(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<MyUser> user = myUserRepository.findByEmail(userDetails.getUsername());
        if (user.get().getName() != null) {
            return "redirect:/mainpage";
        } else {
            return "profile_form";
        }
    }


    @PostMapping("/profileform")
    public String addDataToUserProfile(@ModelAttribute MyUser userModel, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {

        Optional<MyUser> user = myUserRepository.findByEmail(userDetails.getUsername());

        if (userModel.getAllCareers() == null
                || userModel.getDifficulty() == null
                || userModel.getCountry() == null
                || userModel.getEnglishLvl() == null
                || userModel.getGameMode() == null
                || userModel.getMainCareer() == null
                || userModel.getName() == null
                || userModel.getPlayingDays() == null
                || userModel.getPlayingHours() == null) {

            redirectAttributes.addFlashAttribute("FormOmited", "You need to fill out all the forms");
            return "redirect:/profileform";
        } else {

            MyUser existingUser = user.get();

            if (userModel.getName() != null && userModel.getName().length() < 5) {
                redirectAttributes.addFlashAttribute("FormOmited", "Name must be more than 4 characters");
                return "redirect:/profileform";
            }

            existingUser.setAllCareers(userModel.getAllCareers());
            existingUser.setDifficulty(userModel.getDifficulty());
            existingUser.setCountry(userModel.getCountry());
            existingUser.setEnglishLvl(userModel.getEnglishLvl());
            existingUser.setGameMode(userModel.getGameMode());
            existingUser.setMainCareer(userModel.getMainCareer());
            existingUser.setName(userModel.getName().substring(0, 1).toUpperCase() + userModel.getName().substring(1));
            existingUser.setPlayingDays(userModel.getPlayingDays());
            existingUser.setPlayingHours(userModel.getPlayingHours());

            myUserRepository.save(existingUser);
            return "redirect:/mainpage";
        }
    }
}

