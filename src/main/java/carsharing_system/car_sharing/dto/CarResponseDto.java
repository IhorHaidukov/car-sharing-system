package carsharing_system.car_sharing.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponseDto {

    private  Long id;
    private  String brand;
    private  String model;
    private int year;
    private BigDecimal pricePerHour;
}
