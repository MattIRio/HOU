package com.heroesOfUbersharik.WebSocket;

import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.repository.MyTeamMembersRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;



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

    }