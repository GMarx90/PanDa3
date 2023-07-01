import com.example.panda3.controller.VehicleController;
import com.example.panda3.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VehicleControllerTest {

    @Mock
    private CarService carService;

    private VehicleController vehicleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vehicleController = new VehicleController(carService);
    }

    @Test
    public void testAddCar_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("Vehicle added successfully");

        when(carService.addCar("{\"model\":\"Ford Mustang\",\"make\":\"Ford\",\"yearOfProduction\":2022,\"seatCount\":4,\"price\":50000.0,\"isAvailable\":true}"))
                .thenReturn(expectedResponse);

        ResponseEntity<String> response = vehicleController.addCar("{\"model\":\"Ford Mustang\",\"make\":\"Ford\",\"yearOfProduction\":2022,\"seatCount\":4,\"price\":50000.0,\"isAvailable\":true}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(carService, times(1)).addCar("{\"model\":\"Ford Mustang\",\"make\":\"Ford\",\"yearOfProduction\":2022,\"seatCount\":4,\"price\":50000.0,\"isAvailable\":true}");
    }

    @Test
    public void testDeleteCar_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("Car deleted successfully");

        when(carService.deleteCar("{\"carId\": 12345}"))
                .thenReturn(expectedResponse);

        ResponseEntity<String> response = vehicleController.deleteCar("{\"carId\": 12345}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(carService, times(1)).deleteCar("{\"carId\": 12345}");
    }

    @Test
    public void testGetCars_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.OK).body("List of cars");

        when(carService.getCars())
                .thenReturn(expectedResponse);

        ResponseEntity<String> response = vehicleController.getCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(carService, times(1)).getCars();
    }
}
