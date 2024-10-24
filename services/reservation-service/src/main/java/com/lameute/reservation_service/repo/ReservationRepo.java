package com.lameute.reservation_service.repo;

import com.lameute.reservation_service.model.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByUserId(long userId);

    Optional<List<Reservation>> findByRideId(long rideId);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE reservations SET reservation_status = :status WHERE id = :idRes")
    void updateReservationStatus(@Param("idRes") long reservationId, @Param("status") String status);

    @Query(nativeQuery = true, value = "SELECT * FROM reservations WHERE reservation_status = 'ACCEPTED' AND ride_id = :rideId")
    Optional<List<Reservation>> findAcceptedReservations(@Param("rideId") long rideId);
}
