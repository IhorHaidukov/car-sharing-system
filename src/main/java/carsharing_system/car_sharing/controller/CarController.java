package carsharing_system.car_sharing.controller;

import carsharing_system.car_sharing.dto.CarRequestDto;
import carsharing_system.car_sharing.dto.CarResponseDto;
import carsharing_system.car_sharing.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public CarResponseDto createCar(
            @Valid @RequestBody CarRequestDto dto) {

        return carService.createCar(dto);
    }

    @GetMapping("/all")
    public List<CarResponseDto> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public CarResponseDto getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PutMapping("/{id}")
    public CarResponseDto updateCar(
            @PathVariable Long id,
            @Valid @RequestBody CarRequestDto dto) {

        return carService.updateCar(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return "Car deleted successfully";
    }
}