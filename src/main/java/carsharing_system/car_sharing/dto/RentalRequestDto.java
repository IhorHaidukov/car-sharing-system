package carsharing_system.car_sharing.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequestDto {


    @NotNull(message = "Car id cannot be null")
    private Long carId;

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endTime;

}