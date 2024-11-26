package com.heroesOfUbersharik.WebSocket;


import com.heroesOfUbersharik.repository.MyTeamMembersRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;




    @Controller
    public class WebSocketHandler {

        @Autowired
        private SimpMessagingTemplate messagingTemplate;
        @Autowired
        private MyUserRepository userRepository;
        @Autowired
        private MyTeamMembersRepository membersRepository;

        @MessageMapping("/accepted")
        public void handleAccepted(@Payload String userName) {
            String userId = String.valueOf(userRepository.findByName(userName).get().getId());
            messagingTemplate.convertAndSend("/main/" + userId, "update");
        }

        @MessageMapping("/deleted")
        public void handleDelete(@Payload String userId) {
            messagingTemplate.convertAndSend("/main/" + userId, "delete");
        }

        @MessageMapping("/teamdeleted")
        public void handleTeamDelete(@Payload String userId) {
            messagingTemplate.convertAndSend("/main/" + userId, "teamdeletedd");
        }

    }