package carsharing_system.car_sharing.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequestDto {



    @NonNull
    private Long carId;

    @NonNull
    private LocalDateTime startTime;

    @NonNull
    private LocalDateTime endTime;
}
