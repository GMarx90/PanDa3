package com.example.panda3.service;

import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.entity.user.UserEntity;
import com.example.panda3.repository.UserRepository;
import com.example.panda3.repository.VehicleEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentService {

    ObjectMapper objectMapper;
    UserRepository userRepository;
    VehicleEntityRepository vehicleEntityRepository;

    @Transactional
    public ResponseEntity<String> rentCar(Long carId, String jsonUserData) {
        try {
            UserEntity user = objectMapper.readValue(jsonUserData, UserEntity.class);
            UserEntity userEntity = userRepository.findByEmail(user.getEmail());
            Optional<VehicleEntity> vehicleEntity = vehicleEntityRepository.findById(carId);

            vehicleEntity.ifPresentOrElse(vehicle -> {
                if (vehicle.isAvailable()) {
                    userEntity.addRentedCar(vehicle);
                    vehicle.setAvailable(false);
                    vehicleEntityRepository.save(vehicle);
                    userRepository.save(userEntity);
                } else {
                    throw new RuntimeException("Samochód nie może być wypozyczony");
                }
            }, () -> {
                throw new RuntimeException("Samochód nie może być wypozyczony");
            });

            return ResponseEntity.ok("Samochód został wypożyczony");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tranzakcja nieudana");
        }
    }

    public ResponseEntity<String> getAllRentCars(String jsonUserData) {
        try {
            UserEntity user = objectMapper.readValue(jsonUserData, UserEntity.class);
            UserEntity userEntity = userRepository.findByEmail(user.getEmail());
            String json = objectMapper.writeValueAsString(userEntity.getRentedCars());
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @Transactional
    public ResponseEntity<String> returnCar(Long carId, String jsonUserData) {
        try {
            UserEntity user = objectMapper.readValue(jsonUserData, UserEntity.class);
            UserEntity userEntity = userRepository.findByEmail(user.getEmail());

            VehicleEntity vehicleEntity = userEntity.getRentedCars()
                    .stream()
                    .filter(c -> Objects.equals(c.getId(), carId))
                    .findFirst()
                    .orElseThrow(() -> {
                                throw new IllegalStateException("Samochód nie moze być zwrócony");
                            }
                    );

            userEntity.returnCar(vehicleEntity);
            vehicleEntity.setAvailable(true);

            userRepository.save(userEntity);
            vehicleEntityRepository.save(vehicleEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Samochód zwórcony");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}