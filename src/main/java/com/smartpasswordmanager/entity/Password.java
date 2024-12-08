package com.smartpasswordmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_passwords")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int passwordId;
    private String website;
    private String websiteUsername;
    private String websitePassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
