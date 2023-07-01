package com.example.panda3.service;

import com.example.panda3.entity.user.UserEntity;
import com.example.panda3.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(objectMapper, userRepository);
    }

    @Test
    public void testAddUser_Success() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        when(objectMapper.readValue(anyString(), eq(UserEntity.class))).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        ResponseEntity<String> response = userService.addUser("{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("użytkownik został dodany", response.getBody());

        verify(objectMapper, times(1)).readValue(anyString(), eq(UserEntity.class));
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    public void testAddUser_Exception() throws Exception {
        when(objectMapper.readValue(anyString(), eq(UserEntity.class))).thenThrow(new IllegalStateException());

        ResponseEntity<String> response = userService.addUser("{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("użytkownik nie został dodany", response.getBody());

        verify(objectMapper, times(1)).readValue(anyString(), eq(UserEntity.class));
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        when(objectMapper.readValue(anyString(), eq(UserEntity.class))).thenReturn(userEntity);

        ResponseEntity<String> response = userService.deleteUser("{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("użytkownik został usunięty", response.getBody());

        verify(objectMapper, times(1)).readValue(anyString(), eq(UserEntity.class));
        verify(userRepository, times(1)).deleteByEmail("test@example.com");
    }

    @Test
    public void testDeleteUser_Exception() throws Exception {
        when(objectMapper.readValue(anyString(), eq(UserEntity.class))).thenThrow(new IllegalStateException());

        ResponseEntity<String> response = userService.deleteUser("{\"email\":\"test@example.com\"}");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("użytkownik nie został usunięty", response.getBody());

        verify(objectMapper, times(1)).readValue(anyString(), eq(UserEntity.class));
        verify(userRepository, never()).deleteByEmail(anyString());
    }
}
