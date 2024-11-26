package com.heroesOfUbersharik.model;

import jakarta.persistence.*;



@Entity
@Table(name = "hou_users_database")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;


    @Column(name = "name",nullable = true)

    String name;
    @Column(name = "country",nullable = true)
    String country;
    @Column(name = "englishLvl", nullable = true)
    String englishLvl;
    @Column(name = "mainCareer", nullable = true)
    String mainCareer;
    @Column(name = "allCareers", nullable = true)
    String allCareers;
    @Column(name = "playingHours", nullable = true)
    String playingHours;
    @Column(name = "playingDays", nullable = true)
    String playingDays;
    @Column(name = "difficulty", nullable = true)
    String difficulty;
    @Column(name = "gameMode", nullable = true)
    String gameMode;

    @Column(name = "chatRoomId", nullable = false)
    Integer chatRoomId;

    public MyUser(String name, String country, String englishLvl, String mainCareer, String allCareers, String playingHours, String playingDays, String difficulty, String gameMode) {
        this.name = name;
        this.country = country;
        this.englishLvl = englishLvl;
        this.mainCareer = mainCareer;
        this.allCareers = allCareers;
        this.playingHours = playingHours;
        this.playingDays = playingDays;
        this.difficulty = difficulty;
        this.gameMode = gameMode;
    }
    public MyUser() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEnglishLvl() {
        return englishLvl;
    }

    public void setEnglishLvl(String englishLvl) {
        this.englishLvl = englishLvl;
    }

    public String getMainCareer() {
        return mainCareer;
    }

    public void setMainCareer(String mainCareer) {
        this.mainCareer = mainCareer;
    }

    public String getAllCareers() {
        return allCareers;
    }

    public void setAllCareers(String allCareers) {
        this.allCareers = allCareers;
    }

    public String getPlayingHours() {
        return playingHours;
    }

    public void setPlayingHours(String playingHours) {
        this.playingHours = playingHours;
    }

    public String getPlayingDays() {
        return playingDays;
    }

    public void setPlayingDays(String playingDays) {
        this.playingDays = playingDays;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
