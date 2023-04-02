package com.example.panda3.car;

import com.example.panda3.entity.CarEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private CarRepository carRepository;

    @Transactional
    public CarEntity addNewCar(CarEntity car) {
        return carRepository.save(car);
    }
    @Transactional
    public Optional<CarEntity> getCar(Long carId) {
       return carRepository.findById(carId);
    }
}
