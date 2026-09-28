package carsharing_system.car_sharing.dto;

import carsharing_system.car_sharing.entity.DriverLicenseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DriverLicenseResponseDto {

    private Long id;
    private String licenseNumber;
    private String photoUrl;
    private DriverLicenseStatus status;
}