package com.example.panda3.service;


import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.repository.VehicleEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    ObjectMapper objectMapper;
    VehicleEntityRepository vehicleEntityRepository;

    public ResponseEntity<String> addCar(@Valid @RequestBody String jsonCarData) {
        try {
            VehicleEntity car = objectMapper.readValue(jsonCarData, VehicleEntity.class);
            vehicleEntityRepository.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body("Samochód został dodany");
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    public ResponseEntity<String> deleteCar(String jsonCarData) {
        try {
            VehicleEntity vehicleEntity = objectMapper.readValue(jsonCarData, VehicleEntity.class);
            vehicleEntityRepository.delete(vehicleEntity);
            return ResponseEntity.status(HttpStatus.OK).body("Samochód został usunięty");
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd przy usuwaniu");
        }
    }

    public ResponseEntity<String> getCars() {
        try {
            List<VehicleEntity> allVehicle = vehicleEntityRepository.findAll();
            String json = objectMapper.writeValueAsString(allVehicle);
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR");
        }
    }
}
