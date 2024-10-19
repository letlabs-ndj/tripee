package com.lameute.expedition_service.model;

import com.lameute.expedition_service.model.Enums.ExpeditionStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Table(name = "expeditions")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Expedition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private LocalDate expeditionDate;

    private LocalTime expeditionTime;

    private Double price;

    private String emailToContact;

    @Enumerated(EnumType.STRING)
    private ExpeditionStatus expeditionStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Packet packet;

    private long userId;

    private long rideId;
}
