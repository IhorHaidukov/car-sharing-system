package carsharing_system.car_sharing.exception;

public class UserHasRentalsException extends RuntimeException {

    public UserHasRentalsException(String message) {
        super(message);
    }
}