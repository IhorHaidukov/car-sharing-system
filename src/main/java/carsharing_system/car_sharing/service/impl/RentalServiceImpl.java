package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.dto.RentalRequestDto;
import carsharing_system.car_sharing.dto.RentalResponseDto;
import carsharing_system.car_sharing.entity.Car;
import carsharing_system.car_sharing.entity.Rental;
import carsharing_system.car_sharing.entity.RentalStatus;
import carsharing_system.car_sharing.entity.Role;
import carsharing_system.car_sharing.entity.User;
import carsharing_system.car_sharing.exception.CarAlreadyRentedException;
import carsharing_system.car_sharing.exception.CarNotFoundException;
import carsharing_system.car_sharing.exception.RentalNotFoundException;
import carsharing_system.car_sharing.exception.UserNotFoundException;
import carsharing_system.car_sharing.mapper.RentalMapper;
import carsharing_system.car_sharing.repository.CarRepository;
import carsharing_system.car_sharing.repository.RentalRepository;
import carsharing_system.car_sharing.repository.UserRepository;
import carsharing_system.car_sharing.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RentalMapper rentalMapper;


    @Override
    public RentalResponseDto createRental(
            RentalRequestDto dto,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() ->
                        new CarNotFoundException(
                                "Car not found with id: " + dto.getCarId()
                        )
                );

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        long minutes = ChronoUnit.MINUTES.between(
                dto.getStartTime(),
                dto.getEndTime()
        );
        boolean isBusy = rentalRepository.existsOverlappingRental(
                dto.getCarId(),
                dto.getStartTime(),
                dto.getEndTime()
        );

        if (isBusy) {
            throw new CarAlreadyRentedException(
                    "Car is already rented for this time"
            );
        }

        BigDecimal pricePerMinute = car.getPricePerHour()
                .divide(
                        BigDecimal.valueOf(60),
                        4,
                        RoundingMode.HALF_UP
                );

        BigDecimal totalPrice = pricePerMinute
                .multiply(BigDecimal.valueOf(minutes))
                .setScale(2, RoundingMode.HALF_UP);

        Rental rental = Rental.builder()
                .user(user)
                .car(car)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .totalPrice(totalPrice)
                .status(RentalStatus.ACTIVE)
                .build();

        Rental savedRental = rentalRepository.save(rental);

        return rentalMapper.toResponseDto(savedRental);
    }


    @Override
    public List<RentalResponseDto> getAllRentals() {

        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toResponseDto)
                .toList();
    }


    @Override
    public List<RentalResponseDto> getMyRentals(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        return rentalRepository.findByUserId(user.getId())
                .stream()
                .map(rentalMapper::toResponseDto)
                .toList();
    }


    @Override
    public RentalResponseDto getRentalById(Long id,String email) {

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() ->
                        new RentalNotFoundException(
                                "Rental not found with id: " + id
                        )
                );
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
        if (currentUser.getRole() != Role.ADMIN
            && !rental.getUser().getEmail().equals(email)) {

            throw new RuntimeException(
                    "You cannot view another user's rental"
            );
        }

        return rentalMapper.toResponseDto(rental);
    }


    @Override
    public RentalResponseDto updateRental(
            Long id,
            RentalRequestDto dto,
            String email) {

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() ->
                        new RentalNotFoundException(
                                "Rental not found with id: " + id
                        )
                );

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );

        if (currentUser.getRole() != Role.ADMIN
            && !rental.getUser().getEmail().equals(email)) {

            throw new RuntimeException(
                    "You cannot update another user's rental"
            );
        }

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() ->
                        new CarNotFoundException(
                                "Car not found with id: " + dto.getCarId()
                        )
                );

        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        long minutes = ChronoUnit.MINUTES.between(
                dto.getStartTime(),
                dto.getEndTime()
        );


        BigDecimal pricePerMinute = car.getPricePerHour()
                .divide(
                        BigDecimal.valueOf(60),
                        4,
                        RoundingMode.HALF_UP
                );

        BigDecimal totalPrice = pricePerMinute
                .multiply(BigDecimal.valueOf(minutes))
                .setScale(2, RoundingMode.HALF_UP);

        rental.setCar(car);
        rental.setStartTime(dto.getStartTime());
        rental.setEndTime(dto.getEndTime());
        rental.setTotalPrice(totalPrice);


        Rental updatedRental = rentalRepository.save(rental);

        return rentalMapper.toResponseDto(updatedRental);
    }


    @Override
    public void deleteRental(Long id,String email) {

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() ->
                        new RentalNotFoundException(
                                "Rental not found with id: " + id
                        )
                );
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
        if (currentUser.getRole() != Role.ADMIN
            && !rental.getUser().getEmail().equals(email)) {

            throw new RuntimeException(
                    "You cannot delete another user's rental"
            );
        }

        rentalRepository.delete(rental);
    }
}