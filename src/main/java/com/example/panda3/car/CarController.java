package com.example.panda3.car;

import com.example.panda3.entity.CarEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/car-administrator")
@AllArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/car")
    public ResponseEntity<CarEntity> createCar(@RequestBody CarEntity car) {
        CarEntity carEntity = carService.addNewCar(car);
        return ResponseEntity.ok(carEntity);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarEntity> createCar(@PathVariable Long carId) {
        Optional<CarEntity> car = carService.getCar(carId);
        return car.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
