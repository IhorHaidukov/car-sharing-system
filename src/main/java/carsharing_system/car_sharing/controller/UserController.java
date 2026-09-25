package carsharing_system.car_sharing.controller;

import carsharing_system.car_sharing.dto.UserRegistrationDto;
import carsharing_system.car_sharing.dto.UserResponseDto;
import carsharing_system.car_sharing.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(
            @Valid @RequestBody UserRegistrationDto userRegistrationDto) {

        return userService.createUser(userRegistrationDto);
    }

    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(
            @PathVariable Long id,
            Principal principal) {

        return userService.getUserById(id, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id,
            Principal principal) {

        userService.deleteUser(id, principal.getName());
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRegistrationDto dto,
            Principal principal) {

        return userService.updateUser(
                id,
                dto,
                principal.getName()
        );
    }
}