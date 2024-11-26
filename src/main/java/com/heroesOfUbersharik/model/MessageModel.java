package com.heroesOfUbersharik.model;

import jakarta.persistence.*;


import java.util.Date;
@Table(name = "messages_database")
@Entity
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    private Integer senderId;

    private String senderName;

    private Integer chatRoomId;

    @Column(name = "date")
    private Date date;


    public MessageModel() {
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MessageModel(String message, Integer senderId, Integer chatRoomId, Date date, String senderName) {
        this.message = message;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;
        this.date = date;
        this.senderName = senderName;
    }


}
