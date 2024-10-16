package com.lameute.api_gateway_service.model;

import jakarta.persistence.*;
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
