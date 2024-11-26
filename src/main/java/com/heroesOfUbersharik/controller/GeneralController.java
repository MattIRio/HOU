package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyUserDetailService;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GeneralController {

  @Autowired
  private MyUserDetailService myUserDetailService;
  @Autowired
  private MyUserRepository userRepository;

  @GetMapping("/home")
  public String handleWelcome() {
    return "home";
  }


  @GetMapping("/login")
  public String loginPage(Model model, @RequestParam(value = "error", required = false) String error) {
    if (error != null) {
      model.addAttribute("wro", "Wrong email or password");
    }
    return "LoginPage";
  }
  @GetMapping("/register")
  public String handleRegister() {
    return "RegisterPage";
  }

}
