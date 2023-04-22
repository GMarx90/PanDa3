package com.example.panda3.entity.rental;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class RentalFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name ="idRental" )
    @NotNull
    @Column(nullable = false)
    private RentalEntity rentalEntity;
    @Column(nullable = false)
    private int amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Future
    private LocalDateTime paymentDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(nullable = false)
    private boolean isPaymentCompleted;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
