package com.heroesOfUbersharik.WebSocket;

import com.heroesOfUbersharik.model.ChatMessageRename;
import com.heroesOfUbersharik.model.MessageModel;
import com.heroesOfUbersharik.repository.MyMessageRepository;
import com.heroesOfUbersharik.repository.MyTeamRepository;
import com.heroesOfUbersharik.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Date;

import static com.heroesOfUbersharik.model.ChatMessageRename.MessageType.LEAVE;
import static com.heroesOfUbersharik.model.ChatMessageRename.MessageType.REMOVE;


@Controller
public class ChatSetting {

    @Autowired
    private MyUserRepository userRepository;
    @Autowired
    private MyTeamRepository teamRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MyMessageRepository myMessageRepository;



    @MessageMapping("/chat.joined")
    public ChatMessageRename register(@Payload ChatMessageRename chatMessage,
                                      SimpMessageHeaderAccessor headerAccessor,
                                      Principal principal) {

        String username = chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", username);

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;
        
        messagingTemplate.convertAndSend("/main/" + roomName, chatMessage);
        Date currentDate = new Date();
        MessageModel currentMessage = new MessageModel(chatMessage.getSender() + " has joined the chat", 1, userRepository.findByEmail(principal.getName()).get().getChatRoomId(), currentDate, null);
        myMessageRepository.save(currentMessage);

        return chatMessage;
    }


    @MessageMapping("/chat.left")
    public ChatMessageRename left(@Payload ChatMessageRename chatMessage,
                                      SimpMessageHeaderAccessor headerAccessor,
                                      Principal principal) {

        String username = chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", username);

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;
        int chatroomId = userRepository.findByEmail(principal.getName()).get().getChatRoomId();
        chatMessage.setType(LEAVE);

        Date currentDate = new Date();
        MessageModel currentMessage = new MessageModel(chatMessage.getSender() + " has left the chat", 0, chatroomId, currentDate, null);
        myMessageRepository.save(currentMessage);

        messagingTemplate.convertAndSend("/main/" + roomName, chatMessage);

        return chatMessage;
    }

    @MessageMapping("/chat.remove")
    public ChatMessageRename remove(@Payload ChatMessageRename chatMessage,
                                  SimpMessageHeaderAccessor headerAccessor,
                                  Principal principal) {

        String username = chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", username);

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;
        chatMessage.setType(REMOVE);

        Date currentDate = new Date();
        MessageModel currentMessage = new MessageModel(chatMessage.getSender() + " has left the chat", 0, userRepository.findByEmail(principal.getName()).get().getChatRoomId(), currentDate, null);
        myMessageRepository.save(currentMessage);

        messagingTemplate.convertAndSend("/main/" + roomName, chatMessage);

        return chatMessage;
    }


    @MessageMapping("/chat.send")
    public ChatMessageRename sendMessage(@Payload ChatMessageRename chatMessage, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        String userId = String.valueOf(userRepository.findByEmail(principal.getName()).get().getChatRoomId());
        String roomName = "chat-room-" + userId;
        Date currentDate = new Date();

        MessageModel currentMessage = new MessageModel(chatMessage.getContent(), userRepository.findByEmail(principal.getName()).get().getId(), userRepository.findByEmail(principal.getName()).get().getChatRoomId(), currentDate, null);
        myMessageRepository.save(currentMessage);

        ChatMessageRename messageWithStatus = new ChatMessageRename();
        messageWithStatus.setContent(chatMessage.getContent());
        messageWithStatus.setSender(chatMessage.getSender());

        messagingTemplate.convertAndSend("/main/" + roomName, messageWithStatus);

        return messageWithStatus;
    }
}
