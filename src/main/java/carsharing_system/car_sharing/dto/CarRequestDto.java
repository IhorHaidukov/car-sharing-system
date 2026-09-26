package carsharing_system.car_sharing.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRequestDto {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Min(1900)
    private int year;

    @NotNull(message = "Price per hour cannot be null")
    @Positive(message = "Price per hour must be positive")
    private BigDecimal pricePerHour;
}
