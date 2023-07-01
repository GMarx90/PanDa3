import com.example.panda3.controller.RentController;
import com.example.panda3.service.RentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RentControllerTest {

    @Mock
    private RentService rentService;

    private RentController rentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rentController = new RentController(rentService);
    }

    @Test
    public void testRentCar_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Car rented successfully");

        when(rentService.rentCar(1L, "{\"email\":\"test@example.com\"}")).thenReturn(expectedResponse);

        ResponseEntity<String> response = rentController.rentCar(1L, "{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(rentService, times(1)).rentCar(1L, "{\"email\":\"test@example.com\"}");
    }

    @Test
    public void testReturnCar_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Car returned successfully");

        when(rentService.returnCar(1L, "{\"email\":\"test@example.com\"}")).thenReturn(expectedResponse);

        ResponseEntity<String> response = rentController.returnCar(1L, "{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(rentService, times(1)).returnCar(1L, "{\"email\":\"test@example.com\"}");
    }

    @Test
    public void testGetAllRentCar_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("List of rented cars retrieved successfully");

        when(rentService.getAllRentCars("{\"email\":\"test@example.com\"}")).thenReturn(expectedResponse);

        ResponseEntity<String> response = rentController.getAllRentCar("{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(rentService, times(1)).getAllRentCars("{\"email\":\"test@example.com\"}");
    }
}
