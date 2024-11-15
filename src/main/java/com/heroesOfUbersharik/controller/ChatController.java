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

import java.security.Principal;
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


    @GetMapping("/chatavalibility")
    public ResponseEntity<Map<String, Object>> getChatRoomName(Principal principal) {
        // Получаем userId на основе email
        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;

        // Пример проверки, является ли пользователь членом команды
        boolean isMember = false;
        if (teamMembersRepository.findByMemberId(userRepository.findByEmail(principal.getName()).get().getId()).isPresent()){
            isMember = true;
        }

        // Возвращаем объект с roomName и флагом isMember
        return ResponseEntity.ok(Map.of("roomName", roomName, "isMember", isMember));
    }

    @GetMapping("/chatavalibility1")
    public ResponseEntity getChatRoomName1(Principal principal) {

        boolean isMember = false;
        if (teamMembersRepository.findByMemberId(userRepository.findByEmail(principal.getName()).get().getId()).isPresent()){
            isMember = true;
        }

        // Возвращаем объект с roomName и флагом isMember
        return ResponseEntity.ok(isMember);
    }

}
