package com.example.panda3.service;


import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.mapper.VehicleEntityMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/add")
@AllArgsConstructor
public class AddCarService {

    VehicleEntityMapper vehicleEntityMapper;

    @PostMapping
    public ResponseEntity<String> addCar(@Valid @RequestBody Map<String, Object> carData) {

        VehicleEntity car = VehicleEntityMapper.convertToVehicle(carData);
        return ResponseEntity.status(HttpStatus.CREATED).body("Samochód został dodany");
    }
}
