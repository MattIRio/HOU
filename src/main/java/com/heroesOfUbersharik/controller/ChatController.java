package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.model.MyUserDetailService;
import com.heroesOfUbersharik.model.TeamMembersModel;
import com.heroesOfUbersharik.model.TeamModel;
import com.heroesOfUbersharik.repository.MyTeamMembersRepository;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyTeamRequestsRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
public class ChatController {
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



    @GetMapping("/mainpage/chatavalibility")
    public ResponseEntity<Boolean>  isChatAvaliable(@AuthenticationPrincipal UserDetails currentUser) {
        boolean response = false;
        if (teamMembersRepository.findByMemberId(userRepository.findByEmail(currentUser.getUsername()).get().getId()).isPresent()){
            response = true;
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }

}
