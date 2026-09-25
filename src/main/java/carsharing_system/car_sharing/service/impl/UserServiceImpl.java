package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.dto.UserRegistrationDto;
import carsharing_system.car_sharing.dto.UserResponseDto;
import carsharing_system.car_sharing.entity.Role;
import carsharing_system.car_sharing.entity.User;
import carsharing_system.car_sharing.exception.AccessDeniedException;
import carsharing_system.car_sharing.exception.UserNotFoundException;
import carsharing_system.car_sharing.mapper.UserMapper;
import carsharing_system.car_sharing.repository.UserRepository;
import carsharing_system.car_sharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRegistrationDto dto) {

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
    public void deleteUser(Long id, String email) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        checkAccess(user, email);

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto updateUser(
            Long id,
            UserRegistrationDto dto,
            String email) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        checkAccess(user, email);

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDto(updatedUser);
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