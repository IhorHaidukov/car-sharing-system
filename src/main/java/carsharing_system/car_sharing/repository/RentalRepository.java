package carsharing_system.car_sharing.repository;

import carsharing_system.car_sharing.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental,Long> {
    List<Rental> findByUserId(Long userId);
    @Query("""
       SELECT COUNT(r) > 0
       FROM Rental r
       WHERE r.car.id = :carId
       AND r.status = 'ACTIVE'
       AND r.startTime < :endTime
       AND r.endTime > :startTime
       """)
    boolean existsOverlappingRental(
            @Param("carId") Long carId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
    @Query("""
       SELECT COUNT(r) > 0
       FROM Rental r
       WHERE r.car.id = :carId
       AND r.id <> :rentalId
       AND r.status = 'ACTIVE'
       AND r.startTime < :endTime
       AND r.endTime > :startTime
       """)
    boolean existsOverlappingRentalExceptCurrent(
            @Param("carId") Long carId,
            @Param("rentalId") Long rentalId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}
