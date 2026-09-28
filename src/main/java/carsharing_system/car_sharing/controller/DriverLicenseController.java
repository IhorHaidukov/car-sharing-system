package carsharing_system.car_sharing.controller;

import carsharing_system.car_sharing.dto.DriverLicenseRequestDto;
import carsharing_system.car_sharing.dto.DriverLicenseResponseDto;
import carsharing_system.car_sharing.entity.DriverLicenseStatus;
import carsharing_system.car_sharing.service.DriverLicenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/driver-licenses")
@RequiredArgsConstructor
public class DriverLicenseController {

    private final DriverLicenseService driverLicenseService;

    @PostMapping
    public DriverLicenseResponseDto submitLicense(
            @Valid @RequestBody DriverLicenseRequestDto dto,
            Principal principal) {

        return driverLicenseService.submitLicense(
                principal.getName(),
                dto
        );
    }

    @GetMapping("/my")
    public DriverLicenseResponseDto getMyLicense(
            Principal principal) {

        return driverLicenseService.getMyLicense(
                principal.getName()
        );
    }

    @PatchMapping("/{id}/status")
    public DriverLicenseResponseDto updateStatus(
            @PathVariable Long id,
            @RequestParam DriverLicenseStatus status) {

        return driverLicenseService.updateStatus(id, status);
    }

    @GetMapping
    public List<DriverLicenseResponseDto> getAllLicenses() {
        return driverLicenseService.getAllLicenses();
    }
}