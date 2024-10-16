package com.idms.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    //As of now I am considering this password as string we can encrypt this and store the encrypted password
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private boolean active;

}
