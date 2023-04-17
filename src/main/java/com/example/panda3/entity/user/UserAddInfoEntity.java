package com.example.panda3.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class UserAddInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driveLicenceCategory;
    private int age;
    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;
    private String phoneNumber;

}