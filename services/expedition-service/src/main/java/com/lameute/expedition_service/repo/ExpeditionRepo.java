package com.lameute.expedition_service.repo;

import com.lameute.expedition_service.model.Expedition;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpeditionRepo extends JpaRepository<Expedition, Long> {
    Optional<List<Expedition>> findByUserId(long userId);

    Optional<List<Expedition>> findByRideId(long rideId);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE expeditions SET expedition_status = :status WHERE id = :idRes")
    void updateExpeditionStatus(@Param("idRes") long reservationId, @Param("status") String status);

    @Query(nativeQuery = true, value = "SELECT * FROM expeditions WHERE expedition_status = 'ACCEPTED' AND ride_id = :rideId")
    Optional<List<Expedition>> findAcceptedExpeditions(@Param("rideId") long rideId);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM expeditions WHERE ride_id = :rideId ")
    void deleteByRideId(@Param("rideId") long rideId);
}
