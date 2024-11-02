package com.heroesOfUbersharik.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Team_database")
public class TeamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

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

    @Column()
    Integer amountOfMembers;

    public Integer getAmountOfMembers() {
        return amountOfMembers;
    }

    public void setAmountOfMembers(Integer amountOfMembers) {
        this.amountOfMembers = amountOfMembers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setTeamsCreatorID(Integer teamsCreator) {
        this.teamsCreatorID = teamsCreator;
    }

    public TeamModel(String teamsPlayingTime, String teamsPlayingDays, String teamsGameMode, String teamsDifficulty, String teamsCountry, String teamName) {
        this.teamsPlayingTime = teamsPlayingTime;
        this.teamsPlayingDays = teamsPlayingDays;
        this.teamsGameMode = teamsGameMode;
        this.teamsDifficulty = teamsDifficulty;
        this.teamsCountry = teamsCountry;
        this.teamName = teamName;
    }
    public TeamModel() {

    }
}
