package com.heroesOfUbersharik.controller;


import com.heroesOfUbersharik.model.MyUser;

import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String createUser(@ModelAttribute MyUser user, RedirectAttributes redirectAttributes) {
        if (myUserRepository.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("EmailExist", "User with such email already exist");
            return "redirect:/register";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            redirectAttributes.addFlashAttribute("registrationSuccess", "You are successfully registered!");
            myUserRepository.save(user);
            return "redirect:/login";
        }
    }
}
