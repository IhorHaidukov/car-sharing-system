package carsharing_system.car_sharing.exception;

public class CarAlreadyRentedException extends RuntimeException {

    public CarAlreadyRentedException(String message) {
        super(message);
    }
}