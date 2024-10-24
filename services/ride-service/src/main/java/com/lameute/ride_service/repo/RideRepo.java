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

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE rides SET status = :status WHERE id = :idRide")
    void updateRideStatus(@Param("idRide") long idRide, @Param("status") String status);

    @Query(value = "SELECT r FROM Ride r WHERE "+
                "r.status= 'ON_HOLD' "+
                "AND ((r.departurePlace.name LIKE :departurePlace% AND r.arrivalPlace.name LIKE :arrivalPlace%) "+
                "OR (r.departurePlace.town LIKE :departurePlace% AND r.arrivalPlace.name LIKE :arrivalPlace%) "+
                "OR (r.departurePlace.town LIKE :departurePlace% AND r.arrivalPlace.town LIKE :arrivalPlace%) "+
                "OR (r.departurePlace.name LIKE :departurePlace% AND r.arrivalPlace.town LIKE :arrivalPlace%))")
    Optional<List<Ride>> searchRide(@Param("departurePlace") String departurePlace,
                                @Param("arrivalPlace") String arrivalPlace);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE rides SET available_places = available_places + :numberOfPlaces WHERE id = :idRide")
    void restoreAvailablePlaces(@Param("idRide") long idRide,
                                @Param("numberOfPlaces") int numberOfPlaces);

    @Modifying(clearAutomatically = true) @Transactional
    @Query(nativeQuery = true, value = "UPDATE rides SET available_places = available_places - :numberOfPlaces WHERE id = :idRide")
    void removeAvailablePlaces(@Param("idRide") long idRide,
                                @Param("numberOfPlaces") int numberOfPlaces);
}
