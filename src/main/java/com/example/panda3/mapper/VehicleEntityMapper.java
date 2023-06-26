package com.example.panda3.mapper;

import com.example.panda3.entity.car.VehicleEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VehicleEntityMapper {

    public static VehicleEntity convertToVehicle(Map<String, Object> VehicleEntityData) {
        VehicleEntity car = new VehicleEntity();
        car.setModel((String) VehicleEntityData.get("model"));
        car.setMake((String) VehicleEntityData.get("make"));
        car.setYearOfProduction((int) VehicleEntityData.get("yearOfProduction"));
        car.setSeatCount((int) VehicleEntityData.get("seatCount"));
        car.setPrice((double) VehicleEntityData.get("price"));
        car.setAvailable((boolean) VehicleEntityData.get("isAvailable"));
        return car;
    }
}
