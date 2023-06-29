package com.cz.spring_cloud_oauth2_server.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
}
