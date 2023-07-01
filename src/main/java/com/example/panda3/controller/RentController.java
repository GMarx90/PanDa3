package com.example.panda3.controller;

import com.example.panda3.service.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RentController {

    RentService rentService;

    @PostMapping("/{carId}/rent")
    @Operation(summary = "Rent a car",
            description = "Rent a car by providing the car ID and user data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car rented successfully"),
            @ApiResponse(responseCode = "500", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    public ResponseEntity<String> rentCar(@PathVariable Long carId, @RequestBody String jsonUserData) {
        return rentService.rentCar(carId, jsonUserData);
    }

    @PostMapping("/{carId}/return")
    @Operation(summary = "Return a rented car",
            description = "Return a rented car by providing the car ID and user data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    public ResponseEntity<String> returnCar(@PathVariable Long carId, @RequestBody String jsonUserData) {
        return rentService.returnCar(carId, jsonUserData);
    }


    @GetMapping("/rentCars")
    @Operation(summary = "Get all rented cars",
            description = "Get a list of all currently rented cars.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rented cars retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<String> getAllRentCar(@RequestBody String jsonUserData) {
       return rentService.getAllRentCars(jsonUserData);
    }
}
