package carsharing_system.car_sharing.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationDto {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank( message = "Last name cannot be empty")
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}