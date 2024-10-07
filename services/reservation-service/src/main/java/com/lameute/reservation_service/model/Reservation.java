package com.lameute.reservation_service.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "reservations")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    
}
