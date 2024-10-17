package com.lameute.expedition_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@Table(name = "packets")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Packet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String description;

    private Double weight;

    private Double length;

    private Double width;

    private Double height;

    private String packetImage;
}
