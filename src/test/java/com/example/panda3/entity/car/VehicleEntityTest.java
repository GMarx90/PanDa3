package com.example.panda3.entity.car;

import com.example.panda3.factory.Facotry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleEntityTest {
    private Facotry facotry = new Facotry();
    private static Validator validator;


    @BeforeAll
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void constructorTest() {

        VehicleEntity expetedCar = facotry.properVehicleFor5Person();

        assertEquals(1L, expetedCar.getIdCar());
        assertEquals("Ford", expetedCar.getModel());
        assertEquals("Mustang", expetedCar.getMake());
        assertEquals(2020, expetedCar.getYearOfProduction());
        assertEquals(5, expetedCar.getSeatCount());
        assertEquals(480, expetedCar.getPrice());
    }

    @Test
    public void shouldValidProperFields() {
        VehicleEntity threeSeats = facotry.VehicleFor3Person();
        VehicleEntity futureCar = facotry.vehicleFromFuture();
        VehicleEntity minusPriceCar = facotry.vehicleForMinusPrice();

        assertTrue(validator.validate(threeSeats).isEmpty());
        assertTrue(validator.validate(futureCar).isEmpty());
        assertTrue(validator.validate(minusPriceCar).isEmpty());
        assertEquals(0,threeSeats.getSeatCount());
        assertEquals(0,futureCar.getYearOfProduction());
        assertEquals(0,minusPriceCar.getPrice());
    }
}
