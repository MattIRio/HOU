package com.heroesOfUbersharik.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "hou_members_que")
public class teamMembersModel {

    @Column(nullable = false)
    Integer senderId;

    @Column(nullable = false)
    Integer teamOwnerId;

    @Column(nullable = false)
    Date date;


}
