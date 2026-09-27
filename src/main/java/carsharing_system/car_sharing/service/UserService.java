package carsharing_system.car_sharing.service;

import carsharing_system.car_sharing.dto.ChangePasswordDto;
import carsharing_system.car_sharing.dto.UserRegistrationDto;
import carsharing_system.car_sharing.dto.UserResponseDto;
import carsharing_system.car_sharing.dto.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRegistrationDto dto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id, String email);

    void deleteUser(Long id, String email);

    UserResponseDto updateUser(Long id, UserUpdateDto dto, String email);

    void changePassword(String email, ChangePasswordDto dto);
}