package com.heroesOfUbersharik.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Entity
@Table(name = "notifications_database")
public class MyNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // используется "id" вместо "ID"

    private String message;  // исправлено на "message" вместо "messege"

    private Integer userId;  // используется "userId" вместо "userID"

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date date;

    // Геттеры и сеттеры


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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
