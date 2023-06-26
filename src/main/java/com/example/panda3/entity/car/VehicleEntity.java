package com.example.panda3.entity.car;

import com.example.panda3.entity.rental.RentalEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCar;
    private String model;
    private String make;
    @Past
    private int yearOfProduction;
    private int seatCount;
    @Positive
    private double price;
    private boolean isAvailable;
    @OneToMany(mappedBy = "vehicleEntity", fetch = FetchType.LAZY)
    private List<RentalEntity> bookings = new ArrayList<>();


    public void setIdCar(Long id) {
        this.idCar = id;
    }

    public Long getIdCar() {
        return idCar;
    }

    public VehicleEntity(Long idCar, String model, String make, int yearOfProduction, int seatCount, double price) {
        this.idCar = idCar;
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

    public void addBooking(RentalEntity booking) {
        this.bookings.add(booking);
    }
}
