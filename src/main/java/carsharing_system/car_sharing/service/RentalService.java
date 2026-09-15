package carsharing_system.car_sharing.service;

import carsharing_system.car_sharing.dto.RentalRequestDto;
import carsharing_system.car_sharing.dto.RentalResponseDto;

import java.util.List;

public interface RentalService {

    RentalResponseDto createRental(RentalRequestDto dto, String email);

    List<RentalResponseDto> getAllRentals();

    List<RentalResponseDto> getMyRentals(String email);

    RentalResponseDto getRentalById(Long id,String email);

    RentalResponseDto updateRental(Long id,RentalRequestDto dto,String email);

     void deleteRental(Long id,String email);

}
