package carsharing_system.car_sharing.mapper;

import carsharing_system.car_sharing.dto.DriverLicenseResponseDto;
import carsharing_system.car_sharing.entity.DriverLicense;
import org.springframework.stereotype.Component;

@Component
public class DriverLicenseMapper {

    public DriverLicenseResponseDto toResponseDto(
            DriverLicense driverLicense) {

        return DriverLicenseResponseDto.builder()
                .id(driverLicense.getId())
                .userId(driverLicense.getUser().getId())
                .userEmail(driverLicense.getUser().getEmail())
                .licenseNumber(driverLicense.getLicenseNumber())
                .photoUrl(driverLicense.getPhotoUrl())
                .status(driverLicense.getStatus())
                .build();
    }
}