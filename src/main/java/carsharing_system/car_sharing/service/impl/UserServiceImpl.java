package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.dto.UserRegistrationDto;
import carsharing_system.car_sharing.dto.UserResponseDto;
import carsharing_system.car_sharing.dto.UserUpdateDto;
import carsharing_system.car_sharing.entity.Role;
import carsharing_system.car_sharing.entity.User;
import carsharing_system.car_sharing.exception.AccessDeniedException;
import carsharing_system.car_sharing.exception.EmailAlreadyExistsException;
import carsharing_system.car_sharing.exception.UserHasRentalsException;
import carsharing_system.car_sharing.exception.UserNotFoundException;
import carsharing_system.car_sharing.mapper.UserMapper;
import carsharing_system.car_sharing.repository.DriverLicenseRepository;
import carsharing_system.car_sharing.repository.UserRepository;
import carsharing_system.car_sharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import carsharing_system.car_sharing.repository.RentalRepository;
import carsharing_system.car_sharing.dto.ChangePasswordDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RentalRepository rentalRepository;
    private final DriverLicenseRepository driverLicenseRepository;

    @Override
    public UserResponseDto createUser(UserRegistrationDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "User with this email already exists"
            );
        }

        User user = userMapper.toEntity(dto);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id, String email) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        checkAccess(user, email);

        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        checkAccess(user, email);

        if (rentalRepository.existsByUserId(id)) {
            throw new UserHasRentalsException(
                    "User cannot be deleted because they have rentals"
            );
        }
        driverLicenseRepository.findByUserId(id)
                .ifPresent(driverLicenseRepository::delete);

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto updateUser(
            Long id,
            UserUpdateDto dto,
            String email) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        checkAccess(user, email);

        if (!user.getEmail().equals(dto.getEmail())
            && userRepository.existsByEmail(dto.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "User with this email already exists"
            );
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDto(updatedUser);
    }
    @Override
    public void changePassword(String email, ChangePasswordDto dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        if (!passwordEncoder.matches(
                dto.getCurrentPassword(),
                user.getPassword())) {

            throw new BadCredentialsException(
                    "Current password is incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );

        userRepository.save(user);
    }

    private void checkAccess(User targetUser, String email) {

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        if (currentUser.getRole() != Role.ADMIN
            && !currentUser.getId().equals(targetUser.getId())) {

            throw new AccessDeniedException(
                    "You cannot access another user's account"
            );
        }
    }
}