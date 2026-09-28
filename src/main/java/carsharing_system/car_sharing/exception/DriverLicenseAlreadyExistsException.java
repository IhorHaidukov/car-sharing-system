package carsharing_system.car_sharing.exception;

public class DriverLicenseAlreadyExistsException extends RuntimeException {

    public DriverLicenseAlreadyExistsException(String message) {
        super(message);
    }
}