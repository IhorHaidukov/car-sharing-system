package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.dto.DriverLicenseRequestDto;
import carsharing_system.car_sharing.dto.DriverLicenseResponseDto;
import carsharing_system.car_sharing.entity.DriverLicense;
import carsharing_system.car_sharing.entity.DriverLicenseStatus;
import carsharing_system.car_sharing.entity.User;
import carsharing_system.car_sharing.exception.DriverLicenseAlreadyExistsException;
import carsharing_system.car_sharing.exception.DriverLicenseNotFoundException;
import carsharing_system.car_sharing.exception.UserNotFoundException;
import carsharing_system.car_sharing.mapper.DriverLicenseMapper;
import carsharing_system.car_sharing.repository.DriverLicenseRepository;
import carsharing_system.car_sharing.repository.UserRepository;
import carsharing_system.car_sharing.service.DriverLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverLicenseServiceImpl implements DriverLicenseService {

    private final DriverLicenseRepository driverLicenseRepository;
    private final UserRepository userRepository;
    private final DriverLicenseMapper driverLicenseMapper;

    @Override
    public DriverLicenseResponseDto submitLicense(
            String email,
            DriverLicenseRequestDto dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        if (driverLicenseRepository.findByUserId(user.getId()).isPresent()) {
            throw new DriverLicenseAlreadyExistsException(
                    "User already has a driver license"
            );
        }

        if (driverLicenseRepository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new DriverLicenseAlreadyExistsException(
                    "Driver license number already exists"
            );
        }

        DriverLicense driverLicense = DriverLicense.builder()
                .licenseNumber(dto.getLicenseNumber())
                .photoUrl(dto.getPhotoUrl())
                .status(DriverLicenseStatus.PENDING)
                .user(user)
                .build();

        DriverLicense savedLicense =
                driverLicenseRepository.save(driverLicense);

        return driverLicenseMapper.toResponseDto(savedLicense);
    }

    @Override
    public DriverLicenseResponseDto getMyLicense(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        DriverLicense driverLicense =
                driverLicenseRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new DriverLicenseNotFoundException(
                                        "Driver license not found"
                                )
                        );

        return driverLicenseMapper.toResponseDto(driverLicense);
    }
    @Override
    public DriverLicenseResponseDto updateStatus(
            Long id,
            DriverLicenseStatus status) {

        DriverLicense driverLicense =
                driverLicenseRepository.findById(id)
                        .orElseThrow(() ->
                                new DriverLicenseNotFoundException(
                                        "Driver license not found with id: " + id
                                )
                        );

        driverLicense.setStatus(status);

        DriverLicense updatedLicense =
                driverLicenseRepository.save(driverLicense);

        return driverLicenseMapper.toResponseDto(updatedLicense);
    }
}