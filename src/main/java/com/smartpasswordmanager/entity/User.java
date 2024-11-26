package com.smartpasswordmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;

    @Column(unique = true)
    private String userMail;

    private String userPassword;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
//
//    @OneToMany(mappedBy = "user")
//    private List<Password> passwordlist;
}
