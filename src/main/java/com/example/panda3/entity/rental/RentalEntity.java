package com.example.panda3.entity.rental;

import com.example.panda3.entity.car.VehicleEntity;
import com.example.panda3.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
public class RentalEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRental;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCar")
    private VehicleEntity vehicleEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private UserEntity userEntity;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startRentalDate;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endRentalDate;
    @Column(nullable = false)
    private boolean isReturned;

    public RentalEntity(Long idRental, VehicleEntity vehicleEntity, UserEntity userEntity, LocalDateTime startRentalDate, LocalDateTime endRentalDate) {
        this.idRental = idRental;
        this.vehicleEntity = vehicleEntity;
        this.userEntity = userEntity;
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
        this.isReturned = false;
        vehicleEntity.addBooking(this);
    }

    public RentalEntity() {

    }

    public void setIdRental(Long id) {
        this.idRental = id;
    }

    public Long getIdRental() {
        return idRental;
    }

    public boolean isReturned() {

        return isReturned;
    }


}