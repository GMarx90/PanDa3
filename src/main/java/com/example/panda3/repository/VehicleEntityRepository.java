package com.example.panda3.repository;

import com.example.panda3.entity.car.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {
}
