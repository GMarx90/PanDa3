import com.example.panda3.controller.UserController;
import com.example.panda3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void testAddUser_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");

        when(userService.addUser("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"Password123\",\"driveLicenceCategory\":\"A\",\"age\":25}"))
                .thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.addUser("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"Password123\",\"driveLicenceCategory\":\"A\",\"age\":25}");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(userService, times(1)).addUser("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"password\":\"Password123\",\"driveLicenceCategory\":\"A\",\"age\":25}");
    }

    @Test
    public void testDeleteUser_Success() {
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body("User deleted successfully");

        when(userService.deleteUser("{\"email\":\"john.doe@example.com\"}"))
                .thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.deleteUser("{\"email\":\"john.doe@example.com\"}");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(userService, times(1)).deleteUser("{\"email\":\"john.doe@example.com\"}");
    }
}
