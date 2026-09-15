package carsharing_system.car_sharing.controller;

import carsharing_system.car_sharing.dto.RentalRequestDto;
import carsharing_system.car_sharing.dto.RentalResponseDto;
import carsharing_system.car_sharing.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;


    @PostMapping
    public RentalResponseDto createRental(
            @RequestBody RentalRequestDto dto,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return rentalService.createRental(dto, email);
    }

    @GetMapping("/all")
    public List<RentalResponseDto> getAllRentals() {

        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(@PathVariable Long id,Authentication authentication) {
        String email = authentication.getName();
        return rentalService.getRentalById(id,email);
    }
    @GetMapping("/my")
    public List<RentalResponseDto> getMyRentals(Authentication authentication) {

        String email = authentication.getName();

        return rentalService.getMyRentals(email);
    }
    @PutMapping("/{id}")
    public RentalResponseDto updateRental(
            @PathVariable Long id,
            @Valid @RequestBody RentalRequestDto dto,
            Authentication authentication) {
        String email = authentication.getName();

        return rentalService.updateRental(id, dto,email);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRental(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        rentalService.deleteRental(id, email);

        return ResponseEntity.ok("Rental deleted successfully");
    }
}


