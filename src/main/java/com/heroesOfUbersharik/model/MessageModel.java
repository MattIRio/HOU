package com.heroesOfUbersharik.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

public class MessageModel {


    @Entity
    @Table(name = "messages_database")
    public class MyNotification {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String message;  // исправлено на "message" вместо "messege"

        private Integer senderId;

        private Integer chatRoomId;

        public Integer getChatRoomId() {
            return chatRoomId;
        }

        public void setChatRoomId(Integer chatRoomId) {
            this.chatRoomId = chatRoomId;
        }

        public Integer getSenderId() {
            return senderId;
        }

        public void setSenderId(Integer senderId) {
            this.senderId = senderId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }


        public MyNotification(String message, Integer senderId, Integer chatRoomId) {
            this.message = message;
            this.senderId = senderId;
            this.chatRoomId = chatRoomId;
        }
    }
}
