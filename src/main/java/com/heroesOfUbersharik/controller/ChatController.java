package com.heroesOfUbersharik.controller;

import com.heroesOfUbersharik.model.*;
import com.heroesOfUbersharik.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;


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
    @Autowired
    private MyMessageRepository myMessageRepository;

// Checking if the current user is a member of the team and chat room
    @GetMapping("/chatavalibility")
    public ResponseEntity<Map<String, Object>> getChatRoomName(Principal principal) {

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;
        boolean isMember = false;

        if (teamMembersRepository.findByMemberId(userRepository.findByEmail(principal.getName()).get().getId()).isPresent()) {
            isMember = true;
        }
        return ResponseEntity.ok(Map.of("roomName", roomName, "isMember", isMember));
    }

// A special method to show a side container with information about the team if user is a team member
    @GetMapping("/chatavalibility1")
    public ResponseEntity getChatRoomName1(Principal principal) {

        boolean isMember = false;
        if (teamMembersRepository.findByMemberId(userRepository.findByEmail(principal.getName()).get().getId()).isPresent()) {
            isMember = true;
        }
        return ResponseEntity.ok(isMember);
    }


//Returns all messages in the current chat room
    @GetMapping("/chatMessages")
    public ResponseEntity<List<Map<String, Object>>> getChatMessages(Principal principal) {

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;

        List<Map<String, Object>> userMessages = new ArrayList<>();

        List<MessageModel> userMessagesModels = myMessageRepository.findAllByChatRoomId(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        MyUser localUser = userRepository.findByEmail(principal.getName()).get();
        Date now = new Date();

        for (MessageModel model : userMessagesModels) {
            Date modelDate = model.getDate();
            long diffInMillis = now.getTime() - modelDate.getTime();
            long daysBetween = TimeUnit.MILLISECONDS.toDays(diffInMillis);
            if (daysBetween > 1){
                myMessageRepository.deleteById(model.getId());
            }

            if (model.getSenderId().equals(localUser.getId())) {
                Map<String, Object> messageDetails = new HashMap<>();
                messageDetails.put("content", model.getMessage());
                messageDetails.put("sender", userRepository.findById((long) model.getSenderId()).get().getName());
                messageDetails.put("isCurrentUser", true);
                userMessages.add(messageDetails);
            } else {
                Map<String, Object> messageDetails = new HashMap<>();
                messageDetails.put("content", model.getMessage());
                if (model.getSenderId() == 1) {
                    messageDetails.put("content", model.getMessage());
                    messageDetails.put("sender", "Join Message");
                    messageDetails.put("isCurrentUser", false);
                    userMessages.add(messageDetails);
                } else if (model.getSenderId() == 0) {
                    messageDetails.put("content", model.getMessage());
                    messageDetails.put("sender", "Leave Message");
                    messageDetails.put("isCurrentUser", false);
                    userMessages.add(messageDetails);
                } else {

                    messageDetails.put("sender", userRepository.findById((long) model.getSenderId()).get().getName());
                    messageDetails.put("isCurrentUser", false);
                    userMessages.add(messageDetails);
                }


            }
        }
        return ResponseEntity.ok(userMessages);
    }
}
