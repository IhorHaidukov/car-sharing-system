package carsharing_system.car_sharing.repository;

import carsharing_system.car_sharing.entity.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverLicenseRepository
        extends JpaRepository<DriverLicense, Long> {

    Optional<DriverLicense> findByUserId(Long userId);

    boolean existsByLicenseNumber(String licenseNumber);
}