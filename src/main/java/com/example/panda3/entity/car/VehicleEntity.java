package com.example.panda3.entity.car;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.Year;

@Data
@NoArgsConstructor
@Entity
public class VehicleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String make;
    private int yearOfProduction;
    private int seatCount;
    private double price;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public VehicleEntity(Long id, String model, String make, int yearOfProduction, int seatCount, double price) {
        this.id = id;
        this.model = model;
        this.make = make;
        if (yearOfProduction < Year.now().getValue()) {
            this.yearOfProduction = yearOfProduction;
        } else {
            System.out.println("Year of production cannot be in future");
        }
        if (seatCount == 2 || seatCount == 4 || seatCount == 5 || seatCount == 7 || seatCount == 9) {
            this.seatCount = seatCount;
        } else {
            System.out.println("Invalid seat number");
        }
        if (price > 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be lower than 0");
        }
    }
}
