package com.lameute.account_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    
    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNumber;
}
