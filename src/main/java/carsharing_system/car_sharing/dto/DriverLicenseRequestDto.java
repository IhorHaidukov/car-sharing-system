package carsharing_system.car_sharing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverLicenseRequestDto {

    @NotBlank(message = "License number cannot be empty")
    private String licenseNumber;

    @NotBlank(message = "Photo URL cannot be empty")
    private String photoUrl;
}
