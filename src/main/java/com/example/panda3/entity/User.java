package com.example.panda3.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String password;


    public User() {

    }
}
