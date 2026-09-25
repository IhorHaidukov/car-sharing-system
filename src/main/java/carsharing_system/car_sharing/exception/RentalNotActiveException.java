package carsharing_system.car_sharing.exception;

public class RentalNotActiveException extends RuntimeException {

    public RentalNotActiveException(String message) {
        super(message);
    }
}
