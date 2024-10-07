package com.lameute.reservation_service.model;

import com.lameute.reservation_service.model.Enums.ReservationStatus;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Table(name = "reservations")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private LocalDate reservationDate;

    private LocalTime reservationTime;

    private Integer reservedPlaces;

    private Double price;

    private Boolean hasLuggage;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private long userId;

    private long rideId;
}
