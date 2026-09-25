package carsharing_system.car_sharing.service.impl;

import carsharing_system.car_sharing.dto.RentalResponseDto;
import carsharing_system.car_sharing.entity.*;
import carsharing_system.car_sharing.exception.*;
import carsharing_system.car_sharing.mapper.RentalMapper;
import carsharing_system.car_sharing.repository.CarRepository;
import carsharing_system.car_sharing.repository.RentalRepository;
import carsharing_system.car_sharing.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import carsharing_system.car_sharing.dto.RentalRequestDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private RentalMapper rentalMapper;

    @InjectMocks
    private RentalServiceImpl rentalService;


    @Test
    void createRental_whenCarIsBusy_shouldThrowException() {
        RentalRequestDto dto = new RentalRequestDto();

        dto.setCarId(6L);
        dto.setStartTime(LocalDateTime.of(2026, 9, 20, 10, 0));
        dto.setEndTime(LocalDateTime.of(2026, 9, 20, 12, 0));


        User user = User.builder()
                .id(26L)
                .firstName("Test")
                .lastName("User")
                .email("testuser@gmail.com")
                .password("password")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("testuser@gmail.com"))
                .thenReturn(Optional.of(user));


        Car car = Car.builder()
                .id(6L)
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .pricePerHour(BigDecimal.valueOf(25))
                .build();

        when(carRepository.findById(6L))
                .thenReturn(Optional.of(car));

        when(rentalRepository.existsOverlappingRental(
                dto.getCarId(),
                dto.getStartTime(),
                dto.getEndTime()
        )).thenReturn(true);

        assertThrows(
                CarAlreadyRentedException.class,
                () -> rentalService.createRental(dto, "testuser@gmail.com")
        );
    }

    @Test
    void shouldCreateRentalSuccessfully() {


        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");


        Car car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setYear(2020);
        car.setPricePerHour(new BigDecimal("60.00"));


        RentalRequestDto dto = new RentalRequestDto();

        dto.setCarId(1L);
        dto.setStartTime(LocalDateTime.of(2026, 9, 20, 10, 0));
        dto.setEndTime(LocalDateTime.of(2026, 9, 20, 12, 0));
        when(carRepository.findById(1L))
                .thenReturn(Optional.of(car));

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(rentalRepository.existsOverlappingRental(
                dto.getCarId(),
                dto.getStartTime(),
                dto.getEndTime()
        )).thenReturn(false);
        when(rentalRepository.save(any(Rental.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RentalResponseDto responseDto = new RentalResponseDto();
        responseDto.setTotalPrice(new BigDecimal("120.00"));
        responseDto.setStatus(RentalStatus.ACTIVE);

        when(rentalMapper.toResponseDto(any(Rental.class)))
                .thenReturn(responseDto);
        RentalResponseDto result =
                rentalService.createRental(dto, "test@test.com");

        ArgumentCaptor<Rental> captor =
                ArgumentCaptor.forClass(Rental.class);

        verify(rentalRepository).save(captor.capture());

        Rental savedRental = captor.getValue();

        assertEquals(RentalStatus.ACTIVE, result.getStatus());
        assertEquals(new BigDecimal("120.00"), result.getTotalPrice());
        assertEquals(RentalStatus.ACTIVE, savedRental.getStatus());
        assertEquals(new BigDecimal("120.00"), savedRental.getTotalPrice());
        assertEquals(user, savedRental.getUser());
        assertEquals(car, savedRental.getCar());
    }

    @Test
    void createRental_whenEndTimeBeforeStartTime_shouldThrowException() {


        User user = new User();
        user.setId(1L);
        user.setEmail("tot@test.com");


        Car car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");
        car.setYear(2022);
        car.setPricePerHour(new BigDecimal("70.00"));

        RentalRequestDto dto = new RentalRequestDto();

        dto.setCarId(1L);
        dto.setStartTime(LocalDateTime.of(2026, 9, 20, 12, 0));
        dto.setEndTime(LocalDateTime.of(2026, 9, 20, 10, 0));

        when(carRepository.findById(1L))
                .thenReturn(Optional.of(car));
        when(userRepository.findByEmail("tot@test.com"))
                .thenReturn(Optional.of(user));

        assertThrows(
                IllegalArgumentException.class,
                () -> rentalService.createRental(dto, "tot@test.com")
        );
    }

    @Test
    void notExistMailInData(){


        RentalRequestDto dto = new RentalRequestDto();
        when(userRepository.findByEmail("missing@test.com"))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()-> rentalService.createRental(dto,"missing@test.com"));
    }
    @Test
    void createRental_whenCarNotFound_shouldThrowException() {

        RentalRequestDto dto = new RentalRequestDto();

        User user = new User();
        user.setId(1L);
        user.setEmail("tуt@test.com");


        dto.setCarId(99L);
        dto.setStartTime(LocalDateTime.of(2026, 9, 20, 10, 0));
        dto.setEndTime(LocalDateTime.of(2026, 9, 20, 12, 0));


        when(userRepository.findByEmail("tyt@test.com"))
                .thenReturn(Optional.of(user));

        when(carRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class,()-> rentalService.createRental(dto,"tyt@test.com"));
    }
    @Test
    void returnRental_whenRentalIsActive_shouldReturnSuccessfully() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test6@test.com");

        Rental rental = new Rental();
        rental.setId(10L);
        rental.setUser(user);
        rental.setStatus(RentalStatus.ACTIVE);

        when(rentalRepository.findById(10L))
                .thenReturn(Optional.of(rental));

        when(userRepository.findByEmail("test6@test.com"))
                .thenReturn(Optional.of(user));

        when(rentalRepository.save(any(Rental.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RentalResponseDto responseDto = new RentalResponseDto();
        responseDto.setStatus(RentalStatus.RETURNED);

        when(rentalMapper.toResponseDto(any(Rental.class)))
                .thenReturn(responseDto);

        RentalResponseDto result =
                rentalService.returnRental(10L, "test6@test.com");

        assertEquals(RentalStatus.RETURNED, rental.getStatus());
        assertEquals(RentalStatus.RETURNED, result.getStatus());

        verify(rentalRepository).save(rental);
    }

    @Test
    void returnRental_whenRentalNotActive_shouldThrowException() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test7@test.com");

        Rental rental = new Rental();
        rental.setId(12L);
        rental.setUser(user);
        rental.setStatus(RentalStatus.RETURNED);


        RentalResponseDto responseDto = new RentalResponseDto();
        responseDto.setStatus(RentalStatus.RETURNED);

        when(rentalRepository.findById(12L))
                .thenReturn(Optional.of(rental));

        when(userRepository.findByEmail("test7@test.com"))
                .thenReturn(Optional.of(user));


        assertThrows(RentalNotActiveException.class,()-> rentalService.returnRental(12L,"test7@test.com"));
    }
    @Test
    void returnRental_whenUserIsNotOwner_shouldThrowAccessDeniedException() {

        User owner = new User();
        owner.setId(1L);
        owner.setEmail("owner@test.com");
        owner.setRole(Role.USER);

        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setEmail("another@test.com");
        anotherUser.setRole(Role.USER);

        Rental rental = new Rental();
        rental.setId(15L);
        rental.setUser(owner);
        rental.setStatus(RentalStatus.ACTIVE);

        when(rentalRepository.findById(15L))
                .thenReturn(Optional.of(rental));
        when(userRepository.findByEmail("another@test.com"))
                .thenReturn(Optional.of(anotherUser));
        assertThrows(
                AccessDeniedException.class,
                () -> rentalService.returnRental(15L, "another@test.com")
        );
    }

    @Test
    void updateRental_shouldRecalculatePrice() {

        User user = new User();
        user.setId(1L);
        user.setEmail("update@test.com");
        user.setRole(Role.USER);

        Car car = new Car();
        car.setId(5L);
        car.setPricePerHour(new BigDecimal("60.00"));

        Rental rental = new Rental();
        rental.setId(20L);
        rental.setUser(user);
        rental.setCar(car);
        rental.setStatus(RentalStatus.ACTIVE);

        RentalRequestDto dto = new RentalRequestDto();
        dto.setCarId(5L);
        dto.setStartTime(LocalDateTime.of(2026, 9, 22, 10, 0));
        dto.setEndTime(LocalDateTime.of(2026, 9, 22, 13, 0));

        when(rentalRepository.findById(20L))
                .thenReturn(Optional.of(rental));

        when(userRepository.findByEmail("update@test.com"))
                .thenReturn(Optional.of(user));

        when(carRepository.findById(5L))
                .thenReturn(Optional.of(car));

        when(rentalRepository.existsOverlappingRentalExceptCurrent(
                dto.getCarId(),
                20L,
                dto.getStartTime(),
                dto.getEndTime()
        )).thenReturn(false);

        when(rentalRepository.save(any(Rental.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RentalResponseDto responseDto = new RentalResponseDto();
        responseDto.setTotalPrice(new BigDecimal("180.00"));

        when(rentalMapper.toResponseDto(any(Rental.class)))
                .thenReturn(responseDto);
        RentalResponseDto result =
                rentalService.updateRental(20L, dto, "update@test.com");

        ArgumentCaptor<Rental> captor =
                ArgumentCaptor.forClass(Rental.class);

        verify(rentalRepository).save(captor.capture());

        Rental savedRental = captor.getValue();

        assertEquals(
                new BigDecimal("180.00"),
                savedRental.getTotalPrice()
        );
    }


}
