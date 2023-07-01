package com.example.panda3.service;

import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.repository.VehicleEntityRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private VehicleEntityRepository vehicleEntityRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCars_Success1() throws JsonProcessingException {
        List<VehicleEntity> vehicles = new ArrayList<>();
        vehicles.add(new VehicleEntity("Toyota", "Camry", 2022));
        vehicles.add(new VehicleEntity("Honda", "Civic", 2021));

        when(vehicleEntityRepository.findAll()).thenReturn(vehicles);
        when(objectMapper.writeValueAsString(vehicles)).thenReturn("[{\"model\":\"Toyota\",\"make\":\"Camry\",\"yearOfProduction\":2022},{\"model\":\"Honda\",\"make\":\"Civic\",\"yearOfProduction\":2021}]");

        ResponseEntity<String> response = carService.getCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"model\":\"Toyota\",\"make\":\"Camry\",\"yearOfProduction\":2022},{\"model\":\"Honda\",\"make\":\"Civic\",\"yearOfProduction\":2021}]", response.getBody());

        verify(vehicleEntityRepository, times(1)).findAll();
        verify(objectMapper, times(1)).writeValueAsString(vehicles);
    }

    @Test
    public void testGetCars_JsonProcessingException() throws JsonProcessingException {
        when(vehicleEntityRepository.findAll()).thenReturn(new ArrayList<>());
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Error") {});

        ResponseEntity<String> response = carService.getCars();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("ERROR", response.getBody());

        verify(vehicleEntityRepository, times(1)).findAll();
        verify(objectMapper, times(1)).writeValueAsString(any());
    }

    @Test
    public void testAddCar_Success() throws JsonProcessingException {
        String jsonCarData = "{\"make\":\"Toyota\",\"model\":\"Camry\",\"yearOfProduction\":2022}";

        VehicleEntity car = new VehicleEntity();
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setYearOfProduction(2022);

        when(objectMapper.readValue(jsonCarData, VehicleEntity.class)).thenReturn(car);
        when(vehicleEntityRepository.save(car)).thenReturn(car);

        ResponseEntity<String> response = carService.addCar(jsonCarData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Samochód został dodany", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonCarData, VehicleEntity.class);
        verify(vehicleEntityRepository, times(1)).save(car);
    }

    @Test
    public void testAddCar_InvalidData() throws JsonProcessingException {
        String jsonCarData = "{\"make\":\"Toyota\",\"model\":\"Camry\",\"yearOfProduction\":\"invalid\"}";

        when(objectMapper.readValue(jsonCarData, VehicleEntity.class)).thenThrow(new JsonProcessingException("Invalid data") {});

        ResponseEntity<String> response = carService.addCar(jsonCarData);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonCarData, VehicleEntity.class);
        verify(vehicleEntityRepository, never()).save(any(VehicleEntity.class));
    }

    @Test
    public void testDeleteCar_Success() throws JsonProcessingException {
        String jsonCarData = "{\"id\":1,\"make\":\"Toyota\",\"model\":\"Camry\",\"yearOfProduction\":2022}";

        VehicleEntity car = new VehicleEntity();
        car.setId(1L);
        car.setMake("Toyota");
        car.setModel("Camry");
        car.setYearOfProduction(2022);

        when(objectMapper.readValue(jsonCarData, VehicleEntity.class)).thenReturn(car);

        ResponseEntity<String> response = carService.deleteCar(jsonCarData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Samochód został usunięty", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonCarData, VehicleEntity.class);
        verify(vehicleEntityRepository, times(1)).delete(car);
    }

    @Test
    public void testDeleteCar_InvalidData() throws JsonProcessingException {
        String jsonCarData = "{\"id\":\"invalid\",\"make\":\"Toyota\",\"model\":\"Camry\",\"yearOfProduction\":2022}";

        when(objectMapper.readValue(jsonCarData, VehicleEntity.class)).thenThrow(new JsonProcessingException("Invalid data") {});

        ResponseEntity<String> response = carService.deleteCar(jsonCarData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Błąd przy usuwaniu", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonCarData, VehicleEntity.class);
        verify(vehicleEntityRepository, never()).delete(any(VehicleEntity.class));
    }

    @Test
    public void testGetCars_Success() throws JsonProcessingException {
        List<VehicleEntity> vehicles = new ArrayList<>();
        vehicles.add(new VehicleEntity("Toyota", "Camry", 2022));
        vehicles.add(new VehicleEntity("Honda", "Accord", 2021));

        when(vehicleEntityRepository.findAll()).thenReturn(vehicles);
        when(objectMapper.writeValueAsString(vehicles)).thenReturn("[{\"make\":\"Toyota\",\"model\":\"Camry\",\"yearOfProduction\":2022},{\"make\":\"Honda\",\"model\":\"Accord\",\"yearOfProduction\":2021}]");

        ResponseEntity<String> response = carService.getCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"make\":\"Toyota\",\"model\":\"Camry\",\"yearOfProduction\":2022},{\"make\":\"Honda\",\"model\":\"Accord\",\"yearOfProduction\":2021}]", response.getBody());

        verify(vehicleEntityRepository, times(1)).findAll();
        verify(objectMapper, times(1)).writeValueAsString(vehicles);
    }

    @Test
    public void testGetCars_JsonProcessingError() throws JsonProcessingException {
        when(vehicleEntityRepository.findAll()).thenReturn(new ArrayList<>());
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("Error") {});

        ResponseEntity<String> response = carService.getCars();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("ERROR", response.getBody());

        verify(vehicleEntityRepository, times(1)).findAll();
        verify(objectMapper, times(1)).writeValueAsString(any());
    }
}