package com.heroesOfUbersharik.WebSocket;

import com.heroesOfUbersharik.model.ChatMessageRename;
import com.heroesOfUbersharik.model.MyUser;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class ChatSetting {

    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @MessageMapping("/chat.joined")
    public ChatMessageRename register(@Payload ChatMessageRename chatMessage,
                                      SimpMessageHeaderAccessor headerAccessor,
                                      Principal principal) {

        String username = chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", username);

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;


        messagingTemplate.convertAndSend("/main/" + roomName, chatMessage);


        return chatMessage; // Возвращаем сообщение обратно
    }


    @MessageMapping("/chat.left")
    public ChatMessageRename left(@Payload ChatMessageRename chatMessage,
                                      SimpMessageHeaderAccessor headerAccessor,
                                      Principal principal) {

        String username = chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", username);

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;


        messagingTemplate.convertAndSend("/main/" + roomName, chatMessage);


        return chatMessage; // Возвращаем сообщение обратно
    }


    @MessageMapping("/chat.send")
    public ChatMessageRename sendMessage(@Payload ChatMessageRename chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        String userId = (String) headerAccessor.getSessionAttributes().get("ID");
        String roomName = "chat-room-" + userId;


        messagingTemplate.convertAndSend("/main/" + roomName, chatMessage);
        System.out.println(roomName + "============================================");
        return chatMessage; // Возвращаем сообщение обратно
    }

}
