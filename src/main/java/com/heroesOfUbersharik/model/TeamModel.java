package com.heroesOfUbersharik.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Team_database")
public class TeamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    Integer chatRoomId;

    @Column(nullable = false, unique = true)
    String teamName;


    @Column(nullable = false)
    Integer teamsCreatorID;

    @Column(nullable = false)
    String teamsCountry;

    @Column(nullable = false)
    String teamsDifficulty;

    @Column(nullable = false)
    String teamsGameMode;

    @Column(nullable = false)
    String teamsPlayingDays;

    @Column(nullable = false)
    String teamsPlayingTime;

    @Column(nullable = false)
    Integer amountOfMembers;

    @Column(nullable = false)
    Integer requestsAmount = 0;


    Boolean isButtonActive;


    public Integer getAmountOfMembers() {
        return amountOfMembers;
    }

    public void setAmountOfMembers(Integer amountOfMembers) {
        this.amountOfMembers = amountOfMembers;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamsCountry() {
        return teamsCountry;
    }

    public void setTeamsCountry(String teamsCountry) {
        this.teamsCountry = teamsCountry;
    }

    public String getTeamsDifficulty() {
        return teamsDifficulty;
    }

    public void setTeamsDifficulty(String teamsDifficulty) {
        this.teamsDifficulty = teamsDifficulty;
    }

    public String getTeamsGameMode() {
        return teamsGameMode;
    }

    public void setTeamsGameMode(String teamsGameMode) {
        this.teamsGameMode = teamsGameMode;
    }

    public String getTeamsPlayingDays() {
        return teamsPlayingDays;
    }

    public void setTeamsPlayingDays(String teamsPlayingDays) {
        this.teamsPlayingDays = teamsPlayingDays;
    }

    public String getTeamsPlayingTime() {
        return teamsPlayingTime;
    }

    public void setTeamsPlayingTime(String teamsPlayingTime) {
        this.teamsPlayingTime = teamsPlayingTime;
    }

    public Integer getTeamsCreatorID() {
        return teamsCreatorID;
    }

    public Integer getRequests_amount() {
        return requestsAmount;
    }

    public void setRequests_amount(Integer requests_amount) {
        requestsAmount = requests_amount;
    }

    public void setTeamsCreatorID(Integer teamsCreator) {
        this.teamsCreatorID = teamsCreator;
    }

    public Integer getRequestsAmount() {
        return requestsAmount;
    }

    public void setRequestsAmount(Integer requestsAmount) {
        this.requestsAmount = requestsAmount;
    }

    public Boolean getButtonActive() {
        return isButtonActive;
    }

    public void setButtonActive(Boolean buttonActive) {
        isButtonActive = buttonActive;
    }

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public TeamModel(String teamName, String teamsCountry, String teamsDifficulty, String teamsGameMode, String teamsPlayingDays, String teamsPlayingTime, Boolean isButtonActive) {
        this.teamName = teamName;
        this.teamsCountry = teamsCountry;
        this.teamsDifficulty = teamsDifficulty;
        this.teamsGameMode = teamsGameMode;
        this.teamsPlayingDays = teamsPlayingDays;
        this.teamsPlayingTime = teamsPlayingTime;
        this.isButtonActive = isButtonActive;
    }

    public TeamModel() {

    }
}
