package com.example.panda3.repository;

import com.example.panda3.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Transactional
    void deleteByEmail(String email);

    UserEntity findByEmail(String email);

}
