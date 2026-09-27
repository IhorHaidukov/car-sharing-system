package carsharing_system.car_sharing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {

    @NotBlank(message = "Current password cannot be empty")
    private String currentPassword;

    @NotBlank(message = "New password cannot be empty")
    @Size(min = 6, message = "New password must contain at least 6 characters")
    private String newPassword;
}
