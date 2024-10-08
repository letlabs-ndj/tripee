package com.lameute.ride_service.repo;

import com.lameute.ride_service.model.Ride;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {
    Optional<List<Ride>> findByUserId(Long userId);

//    @Query() //TODO Search ride query
//    Optional<List<Ride>> searchRideByRoute(@Param("departurePlace") String departurePlace,
//                                           @Param("arrivalPlace") String arrivalPlace);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE rides SET status = 'IN_PROGRESS' WHERE id = :idRide")
    void startRide(@Param("idRide") long idRide);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE rides SET status = 'TERMINATED' WHERE id = :idRide")
    void terminateRide(@Param("idRide") long idRide);
}
