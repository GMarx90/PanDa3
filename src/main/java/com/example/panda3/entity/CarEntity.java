package com.example.panda3.entity;

import com.example.panda3.utils.FuelTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Entity
@Table(name = "cars")
@NoArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 1, max = 32, message = "Name car model must be between 1 and 32 characters")
    private String carModel;

    @NonNull
    private Date yearOfProduction;

    @NonNull
    private Long engineMileage;

    @Enumerated
    private FuelTypeEnum fuelType;
}
