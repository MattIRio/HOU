package com.heroesOfUbersharik.model;

import jakarta.persistence.*;


@Entity
@Table(name = "hou_members_que")
public class TeamMembersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "team_owner_id", nullable = false)
    private Integer teamOwnerId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "is_notified")
    private boolean isNotified = false;

    public TeamMembersModel() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getTeamOwnerId() {
        return teamOwnerId;
    }

    public void setTeamOwnerId(Integer teamOwnerId) {
        this.teamOwnerId = teamOwnerId;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public TeamMembersModel(Integer memberId, Integer teamOwnerId, String teamName) {
        this.memberId = memberId;
        this.teamOwnerId = teamOwnerId;
        this.teamName = teamName;
    }
}
