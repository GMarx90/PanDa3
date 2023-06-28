package com.example.panda3.entity.car;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long car_id;
    private String model;
    private String make;
    private int yearOfProduction;
    private int seatCount;
    private double price;
    private boolean isAvailable;

    @JsonSetter("isAvailable")
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}
