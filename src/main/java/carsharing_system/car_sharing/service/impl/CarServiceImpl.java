package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.dto.CarRequestDto;
import carsharing_system.car_sharing.dto.CarResponseDto;
import carsharing_system.car_sharing.entity.Car;
import carsharing_system.car_sharing.exception.CarNotFoundException;
import carsharing_system.car_sharing.mapper.CarMapper;
import carsharing_system.car_sharing.repository.CarRepository;
import carsharing_system.car_sharing.service.CarService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarResponseDto createCar(CarRequestDto dto){
        Car car = carMapper.toEntity(dto);
        Car savedCar = carRepository.save(car);
        return carMapper.toResponseDto(savedCar);
    }
    @Override
    public List<CarResponseDto> getAllCars(){
        return carRepository.findAll()
                .stream()
                .map(carMapper::toResponseDto)
                .toList();
    }
    @Override
    public CarResponseDto getCarById(Long id){
        Car car = carRepository.findById(id)
                .orElseThrow(() ->
                        new CarNotFoundException("Car not found whit id: "+ id));
        return carMapper.toResponseDto(car);

    }
    @Override
    public CarResponseDto updateCar(Long id,CarRequestDto dto) {
        Car car = carRepository.findById(id)
                .orElseThrow(()->
                        new CarNotFoundException("Car not found with id: "+ id));
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setPricePerHour(dto.getPricePerHour());

        Car updateCar = carRepository.save(car);
        return carMapper.toResponseDto(updateCar);
    }
    @Override
    public void deleteCar(Long id){
        Car car =carRepository.findById(id)
                .orElseThrow(() ->
                        new CarNotFoundException("Car not found with id: "+ id));
                carRepository.delete(car);

    }
}
