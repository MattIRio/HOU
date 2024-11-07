package com.heroesOfUbersharik.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "hou_request_que")
public class TeamRequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    public TeamRequestModel() {
    }

    @Column(nullable = false)
    Integer senderId;

    @Column(nullable = false)
    Integer teamOwnerId;

    @Column()
    LocalDate date;


    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTeamOwnerId() {
        return teamOwnerId;
    }

    public void setTeamOwnerId(Integer teamOwnerId) {
        this.teamOwnerId = teamOwnerId;
    }


    public TeamRequestModel(Integer senderId, Integer teamOwnerId, LocalDate date) {
        this.senderId = senderId;
        this.teamOwnerId = teamOwnerId;
        this.date = date;
    }
}
