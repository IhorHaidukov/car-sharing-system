package carsharing_system.car_sharing.mapper;


import carsharing_system.car_sharing.dto.RentalResponseDto;
import carsharing_system.car_sharing.entity.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    public RentalResponseDto toResponseDto(Rental rental){
        return RentalResponseDto.builder()
                .id(rental.getId())
                .userId(rental.getUser().getId())
                .carId(rental.getCar().getId())
                .startTime(rental.getStartTime())
                .endTime(rental.getEndTime())
                .totalPrice(rental.getTotalPrice())
                .status(rental.getStatus())
                .build();

    }
}
