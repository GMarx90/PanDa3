package com.example.panda3.service;

import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.entity.user.UserEntity;
import com.example.panda3.repository.UserRepository;
import com.example.panda3.repository.VehicleEntityRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RentServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleEntityRepository vehicleEntityRepository;

    @InjectMocks
    private RentService rentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        vehicleEntityRepository.deleteAll();
    }

    @Test
    public void testRentCar_Success() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(1L);
        vehicle.setAvailable(true);

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(vehicleEntityRepository.findById(carId)).thenReturn(Optional.of(vehicle));
        when(vehicleEntityRepository.save(vehicle)).thenReturn(vehicle);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<String> response = rentService.rentCar(carId, jsonUserData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Samochód został wypożyczony", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(vehicleEntityRepository, times(1)).findById(carId);
        verify(vehicleEntityRepository, times(1)).save(vehicle);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRentCar_UnavailableCar() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setId(1L);
        vehicle.setAvailable(false);

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(vehicleEntityRepository.findById(carId)).thenReturn(Optional.of(vehicle));

        ResponseEntity<String> response = rentService.rentCar(carId, jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Tranzakcja nieudana", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(vehicleEntityRepository, times(1)).findById(carId);
        verify(vehicleEntityRepository, never()).save(any(VehicleEntity.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRentCar_UserNotFound() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        ResponseEntity<String> response = rentService.rentCar(carId, jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Tranzakcja nieudana", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(vehicleEntityRepository, never()).save(any(VehicleEntity.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testRentCar_Exception() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = rentService.rentCar(carId, jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Tranzakcja nieudana", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, never()).findByEmail(anyString());
        verify(vehicleEntityRepository, never()).findById(anyLong());
        verify(vehicleEntityRepository, never()).save(any(VehicleEntity.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testGetAllRentCars_Success() throws Exception {
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        List<VehicleEntity> rentedCars = new ArrayList<>();
        rentedCars.add(new VehicleEntity("Toyota", "Camry", 2022));
        rentedCars.add(new VehicleEntity("Honda", "Accord", 2021));
        user.setRentedCars(rentedCars);

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(objectMapper.writeValueAsString(user.getRentedCars())).thenReturn("[{\"make\":\"Toyota\",\"model\":\"Camry\",\"year\":2022},{\"make\":\"Honda\",\"model\":\"Accord\",\"year\":2021}]");

        ResponseEntity<String> response = rentService.getAllRentCars(jsonUserData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[{\"make\":\"Toyota\",\"model\":\"Camry\",\"year\":2022},{\"make\":\"Honda\",\"model\":\"Accord\",\"year\":2021}]", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(objectMapper, times(1)).writeValueAsString(user.getRentedCars());
    }

    @Test
    public void testGetAllRentCars_UserNotFound() throws Exception {
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        ResponseEntity<String> response = rentService.getAllRentCars(jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(objectMapper, never()).writeValueAsString(any());
    }

    @Test
    public void testGetAllRentCars_Exception() throws Exception {
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = rentService.getAllRentCars(jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, never()).findByEmail(anyString());
        verify(objectMapper, never()).writeValueAsString(any());
    }

    @Test
    public void testReturnCar_Success() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        List<VehicleEntity> rentedCars = new ArrayList<>();
        VehicleEntity vehicle = new VehicleEntity("Toyota", "Camry", 2022);
        vehicle.setId(carId);
        rentedCars.add(vehicle);
        user.setRentedCars(rentedCars);

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(vehicleEntityRepository.save(vehicle)).thenReturn(vehicle);

        ResponseEntity<String> response = rentService.returnCar(carId, jsonUserData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Samochód zwórcony", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(vehicleEntityRepository, times(1)).save(vehicle);
    }

    @Test
    public void testReturnCar_CarNotFound() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        List<VehicleEntity> rentedCars = new ArrayList<>();
        rentedCars.add(new VehicleEntity("Toyota", "Camry", 2022));
        user.setRentedCars(rentedCars);

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        ResponseEntity<String> response = rentService.returnCar(carId, jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(vehicleEntityRepository, never()).save(any(VehicleEntity.class));
    }

    @Test
    public void testReturnCar_Exception() throws Exception {
        Long carId = 1L;
        String jsonUserData = "{\"email\":\"test@example.com\"}";

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        List<VehicleEntity> rentedCars = new ArrayList<>();
        VehicleEntity vehicle = new VehicleEntity("Toyota", "Camry", 2022);
        vehicle.setId(carId);
        rentedCars.add(vehicle);
        user.setRentedCars(rentedCars);

        when(objectMapper.readValue(jsonUserData, UserEntity.class)).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.save(user)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<String> response = rentService.returnCar(carId, jsonUserData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error", response.getBody());

        verify(objectMapper, times(1)).readValue(jsonUserData, UserEntity.class);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(vehicleEntityRepository, never()).save(any(VehicleEntity.class));
    }
}
