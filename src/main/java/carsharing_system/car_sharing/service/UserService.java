package carsharing_system.car_sharing.service;

import carsharing_system.car_sharing.dto.UserDto;
import carsharing_system.car_sharing.dto.UserRegistrationDto;
import carsharing_system.car_sharing.dto.UserResponseDto;
import carsharing_system.car_sharing.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRegistrationDto dto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    void deleteUser(Long id);

    UserResponseDto updateUser(Long id, UserRegistrationDto dto);
}

