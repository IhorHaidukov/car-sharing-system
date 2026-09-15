package carsharing_system.car_sharing.service;

import carsharing_system.car_sharing.dto.CarRequestDto;
import carsharing_system.car_sharing.dto.CarResponseDto;

import java.util.List;


public interface CarService {
    CarResponseDto createCar(CarRequestDto dto);

    List<CarResponseDto> getAllCars();

    CarResponseDto getCarById(Long id);

    CarResponseDto updateCar(Long id, CarRequestDto dto);

        void  deleteCar(Long id);

}
