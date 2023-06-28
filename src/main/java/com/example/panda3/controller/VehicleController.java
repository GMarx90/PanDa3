package com.example.panda3.controller;

import com.example.panda3.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class VehicleController {

    CarService addCarService;

    @PostMapping("/admin/addVehicle")
    @Operation(summary = "Add a vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<String> addCar(@Valid @org.springframework.web.bind.annotation.RequestBody @RequestBody(
            description = "Car data",
            content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(value = "{\"model\": \"Ford Mustang\", \"make\": \"Ford\", " +
                            "\"yearOfProduction\": 2022, \"seatCount\": 4, \"price\": 50000.0, \"isAvailable\": true}",
                            name = "Example Car Data")
            })) String jsonCarData) {
        return addCarService.addCar(jsonCarData);
    }


    @Operation(summary = "Delete a car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @DeleteMapping("/admin/deleteVehicle")
    public ResponseEntity<String> deleteCar(
            @Valid @org.springframework.web.bind.annotation.RequestBody @RequestBody(
                    description = "Car data",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "{\"carId\": 12345}",
                                    name = "Example Car Data")
                    })
            ) String jsonCarData) {
        return addCarService.deleteCar(jsonCarData);
    }

    @GetMapping("/Cars")
    @Operation(summary = "Get list of cars", description = "Returns a list of cars.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getCars(){
        return addCarService.getCars();
    }
}
