package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.exception.DriverLicenseAlreadyExistsException;
import carsharing_system.car_sharing.mapper.DriverLicenseMapper;
import carsharing_system.car_sharing.repository.DriverLicenseRepository;
import carsharing_system.car_sharing.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import carsharing_system.car_sharing.dto.DriverLicenseRequestDto;
import carsharing_system.car_sharing.dto.DriverLicenseResponseDto;
import carsharing_system.car_sharing.entity.DriverLicense;
import carsharing_system.car_sharing.entity.DriverLicenseStatus;
import carsharing_system.car_sharing.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverLicenseServiceImplTest {

    @Mock
    private DriverLicenseRepository driverLicenseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DriverLicenseMapper driverLicenseMapper;

    @InjectMocks
    private DriverLicenseServiceImpl driverLicenseService;

    @Test
    void submitLicense_shouldCreatePendingLicense() {

        User user = User.builder()
                .id(1L)
                .email("user@test.com")
                .build();

        DriverLicenseRequestDto dto = new DriverLicenseRequestDto();
        dto.setLicenseNumber("DL123456");
        dto.setPhotoUrl("photo.jpg");

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(driverLicenseRepository.findByUserId(1L))
                .thenReturn(Optional.empty());

        when(driverLicenseRepository.existsByLicenseNumber("DL123456"))
                .thenReturn(false);

        when(driverLicenseRepository.save(any(DriverLicense.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DriverLicenseResponseDto responseDto =
                DriverLicenseResponseDto.builder()
                        .licenseNumber("DL123456")
                        .photoUrl("photo.jpg")
                        .status(DriverLicenseStatus.PENDING)
                        .build();

        when(driverLicenseMapper.toResponseDto(any(DriverLicense.class)))
                .thenReturn(responseDto);

        DriverLicenseResponseDto result =
                driverLicenseService.submitLicense(
                        "user@test.com",
                        dto
                );

        assertEquals("DL123456", result.getLicenseNumber());
        assertEquals(DriverLicenseStatus.PENDING, result.getStatus());
    }

    @Test
    void submitLicense_whenUserAlreadyHasLicense_shouldThrowException() {

        User user = User.builder()
                .id(1L)
                .email("user@test.com")
                .build();

        DriverLicenseRequestDto dto = new DriverLicenseRequestDto();
        dto.setLicenseNumber("DL123456");
        dto.setPhotoUrl("photo.jpg");

        DriverLicense existingLicense = DriverLicense.builder()
                .id(10L)
                .licenseNumber("OLD123")
                .status(DriverLicenseStatus.PENDING)
                .user(user)
                .build();

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(driverLicenseRepository.findByUserId(1L))
                .thenReturn(Optional.of(existingLicense));

        assertThrows(
                DriverLicenseAlreadyExistsException.class,
                () -> driverLicenseService.submitLicense(
                        "user@test.com",
                        dto
                )
        );
    }

    @Test
    void submitLicense_whenLicenseNumberAlreadyExists_shouldThrowException() {

        User user = User.builder()
                .id(1L)
                .email("user@test.com")
                .build();

        DriverLicenseRequestDto dto = new DriverLicenseRequestDto();
        dto.setLicenseNumber("DL123456");
        dto.setPhotoUrl("photo.jpg");

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(driverLicenseRepository.findByUserId(1L))
                .thenReturn(Optional.empty());

        when(driverLicenseRepository.existsByLicenseNumber("DL123456"))
                .thenReturn(true);

        assertThrows(
                DriverLicenseAlreadyExistsException.class,
                () -> driverLicenseService.submitLicense(
                        "user@test.com",
                        dto
                )
        );
    }

    @Test
    void getMyLicense_shouldReturnLicense() {

        User user = User.builder()
                .id(1L)
                .email("user@test.com")
                .build();

        DriverLicense driverLicense = DriverLicense.builder()
                .id(10L)
                .licenseNumber("DL123456")
                .photoUrl("photo.jpg")
                .status(DriverLicenseStatus.VERIFIED)
                .user(user)
                .build();

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(driverLicenseRepository.findByUserId(1L))
                .thenReturn(Optional.of(driverLicense));

        DriverLicenseResponseDto responseDto =
                DriverLicenseResponseDto.builder()
                        .id(10L)
                        .licenseNumber("DL123456")
                        .photoUrl("photo.jpg")
                        .status(DriverLicenseStatus.VERIFIED)
                        .build();

        when(driverLicenseMapper.toResponseDto(driverLicense))
                .thenReturn(responseDto);

        DriverLicenseResponseDto result =
                driverLicenseService.getMyLicense("user@test.com");

        assertEquals("DL123456", result.getLicenseNumber());
        assertEquals(DriverLicenseStatus.VERIFIED, result.getStatus());
    }

    @Test
    void updateStatus_shouldChangeLicenseStatus() {

        DriverLicense driverLicense = DriverLicense.builder()
                .id(10L)
                .licenseNumber("DL123456")
                .status(DriverLicenseStatus.PENDING)
                .build();

        when(driverLicenseRepository.findById(10L))
                .thenReturn(Optional.of(driverLicense));

        when(driverLicenseRepository.save(any(DriverLicense.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DriverLicenseResponseDto responseDto =
                DriverLicenseResponseDto.builder()
                        .id(10L)
                        .licenseNumber("DL123456")
                        .status(DriverLicenseStatus.VERIFIED)
                        .build();

        when(driverLicenseMapper.toResponseDto(any(DriverLicense.class)))
                .thenReturn(responseDto);

        DriverLicenseResponseDto result =
                driverLicenseService.updateStatus(
                        10L,
                        DriverLicenseStatus.VERIFIED
                );

        assertEquals(
                DriverLicenseStatus.VERIFIED,
                driverLicense.getStatus()
        );

        assertEquals(
                DriverLicenseStatus.VERIFIED,
                result.getStatus()
        );
    }
}