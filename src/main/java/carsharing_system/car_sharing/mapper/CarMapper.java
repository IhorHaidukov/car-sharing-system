package carsharing_system.car_sharing.mapper;

import carsharing_system.car_sharing.dto.CarRequestDto;
import carsharing_system.car_sharing.dto.CarResponseDto;
import carsharing_system.car_sharing.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car toEntity(CarRequestDto dto) {
        return Car.builder()
                .brand(dto.getBrand())
                .model(dto.getModel())
                .year(dto.getYear())
                .pricePerHour(dto.getPricePerHour())
                .build();
    }

    public CarResponseDto toResponseDto(Car car) {
        return CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .pricePerHour(car.getPricePerHour())
                .build();
    }
}