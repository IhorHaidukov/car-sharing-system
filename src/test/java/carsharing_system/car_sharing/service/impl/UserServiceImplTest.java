package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.mapper.UserMapper;
import carsharing_system.car_sharing.repository.DriverLicenseRepository;
import carsharing_system.car_sharing.repository.RentalRepository;
import carsharing_system.car_sharing.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import carsharing_system.car_sharing.entity.DriverLicense;
import carsharing_system.car_sharing.entity.Role;
import carsharing_system.car_sharing.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private DriverLicenseRepository driverLicenseRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void deleteUser_shouldDeleteDriverLicenseAndUser() {

        User user = User.builder()
                .id(1L)
                .email("user@test.com")
                .role(Role.USER)
                .build();

        DriverLicense driverLicense = DriverLicense.builder()
                .id(10L)
                .user(user)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.findByEmail("user@test.com"))
                .thenReturn(Optional.of(user));

        when(rentalRepository.existsByUserId(1L))
                .thenReturn(false);

        when(driverLicenseRepository.findByUserId(1L))
                .thenReturn(Optional.of(driverLicense));

        userService.deleteUser(1L, "user@test.com");

        verify(driverLicenseRepository).delete(driverLicense);
        verify(userRepository).delete(user);
    }
}