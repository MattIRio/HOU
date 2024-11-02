package com.heroesOfUbersharik.model;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@Table(name = "hou_request_que")
public class RequestModel {


    @Column(nullable = false)
    Integer senderId;

    @Column(nullable = false)
    Integer teamOwnerId;

    @Column(nullable = false)
    Date date;

    }
