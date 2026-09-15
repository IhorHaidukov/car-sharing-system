package carsharing_system.car_sharing.repository;

import carsharing_system.car_sharing.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
}
