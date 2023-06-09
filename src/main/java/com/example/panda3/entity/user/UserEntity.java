package com.example.panda3.entity.user;

import com.example.panda3.entity.car.VehicleEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String driveLicenceCategory;
    private int age;

    @OneToMany
    @JoinColumn(name = "car_id")
    private List<VehicleEntity> rentedCars;


    public void addRentedCar(VehicleEntity vehicle) {
        if (rentedCars == null) {
            rentedCars = new ArrayList<>();
        }
        rentedCars.add(vehicle);
    }

    public void returnCar(VehicleEntity vehicleEntity){
        this.rentedCars = this.rentedCars.stream().filter(c-> !Objects.equals(c.getId(), vehicleEntity.getCar_id())).collect(Collectors.toList());
    }
}
