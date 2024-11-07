package com.heroesOfUbersharik.WebSocket;

import com.heroesOfUbersharik.model.ChatMessageRename;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSetting {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.joined")
    public ChatMessageRename register(@Payload ChatMessageRename chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        String destination = "/main/public";

        // Возвращаем сообщение, отправив его в нужный топик
        messagingTemplate.convertAndSend(destination, chatMessage);

        return chatMessage; // Возвращаем сообщение обратно, если нужно
    }


    @MessageMapping("/chat.send")
    public ChatMessageRename sendMessage(@Payload ChatMessageRename chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        String destination = "/main/public";

        // Возвращаем сообщение, отправив его в нужный топик
        messagingTemplate.convertAndSend(destination, chatMessage);

        return chatMessage;
    }

}
