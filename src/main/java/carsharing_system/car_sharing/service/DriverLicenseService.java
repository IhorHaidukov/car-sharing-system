package carsharing_system.car_sharing.service;

import carsharing_system.car_sharing.dto.DriverLicenseRequestDto;
import carsharing_system.car_sharing.dto.DriverLicenseResponseDto;
import carsharing_system.car_sharing.entity.DriverLicenseStatus;
import java.util.List;

public interface DriverLicenseService {

    DriverLicenseResponseDto submitLicense(
            String email,
            DriverLicenseRequestDto dto
    );

    DriverLicenseResponseDto getMyLicense(String email);

    DriverLicenseResponseDto updateStatus(
            Long id,
            DriverLicenseStatus status
    );

    List<DriverLicenseResponseDto> getAllLicenses();
}