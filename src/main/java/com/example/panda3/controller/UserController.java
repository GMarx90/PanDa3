package com.example.panda3.controller;

import com.example.panda3.entity.user.UserEntity;
import com.example.panda3.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class UserController {

    UserService userService;

    @Operation(summary = "Add a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/admin/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody
                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                  content = @Content(schema = @Schema(implementation = UserEntity.class),
                                                          examples = @ExampleObject(value = """
                                                                  {
                                                                    "firstName": "John",
                                                                    "lastName": "Doe",
                                                                    "email": "john.doe@example.com",
                                                                    "password": "Password123",
                                                                    "driveLicenceCategory": "A",
                                                                    "age": 25
                                                                  }"""))) String jsonUserData) {
        return userService.addUser(jsonUserData);
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/admin/deleteUser")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     content = @Content(schema = @Schema(implementation = UserEntity.class),
                                                             examples = @ExampleObject(value = """
                                                                     {
                                                                       "email": "john.doe@example.com"
                                                                     }"""))) String jsonUserData) {
        return userService.deleteUser(jsonUserData);
    }

}
