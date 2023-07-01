package com.example.panda3.service;

import com.example.panda3.entity.user.UserEntity;
import com.example.panda3.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    ObjectMapper objectMapper;
    UserRepository userRepository;

    public ResponseEntity<String> addUser(String jsonUserData) {
        try {
            UserEntity userEntity = objectMapper.readValue(jsonUserData, UserEntity.class);
            userRepository.save(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("użytkownik został dodany");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("użytkownik nie został dodany");
        }
    }

    public ResponseEntity<String> deleteUser(String jsonUserData) {
        try {
            UserEntity userEntity = objectMapper.readValue(jsonUserData, UserEntity.class);
            userRepository.deleteByEmail(userEntity.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("użytkownik został usunięty");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("użytkownik nie został usunięty");
        }
    }
}
