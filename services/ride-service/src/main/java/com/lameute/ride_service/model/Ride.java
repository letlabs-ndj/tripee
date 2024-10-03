package com.lameute.ride_service.model;

import com.lameute.ride_service.model.Enum.RideStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Table(name = "rides")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ride {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private LocalDate departureDate;

    private LocalTime departureTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dep_id")
    private Place departurePlace;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arr_id")
    private Place arrivalPlace;

    private double price;

    private int availablePlaces;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private long userId;
}
