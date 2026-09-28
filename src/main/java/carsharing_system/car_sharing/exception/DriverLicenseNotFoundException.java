package carsharing_system.car_sharing.exception;

public class DriverLicenseNotFoundException extends RuntimeException {

    public DriverLicenseNotFoundException(String message) {
        super(message);
    }
}