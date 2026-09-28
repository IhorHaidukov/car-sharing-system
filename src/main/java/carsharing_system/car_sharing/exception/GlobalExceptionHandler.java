package carsharing_system.car_sharing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleUserNotFound(
            UserNotFoundException ex){
      return Map.of(MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(CarAlreadyRentedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleCarAlreadyRented(
            CarAlreadyRentedException ex) {

        return Map.of(MESSAGE, ex.getMessage());

    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleCarNotFound(CarNotFoundException ex) {
        return Map.of(MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDenied(
            AccessDeniedException ex) {

        return Map.of(MESSAGE, ex.getMessage());

    }
    @ExceptionHandler(RentalNotActiveException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleRentalNotActive(
            RentalNotActiveException ex) {

        return Map.of(MESSAGE, ex.getMessage());

    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgument(
            IllegalArgumentException ex) {

        return Map.of(MESSAGE, ex.getMessage());


    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex) {

        return Map.of(MESSAGE, ex.getMessage());

    }
    @ExceptionHandler(RentalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRentalNotFound(
            RentalNotFoundException ex) {

        return Map.of(MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation failed");

        return Map.of(MESSAGE, message);
    }
    @ExceptionHandler(CarHasRentalsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleCarHasRentals(
            CarHasRentalsException ex) {

        return Map.of(MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(UserHasRentalsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserHasRentals(
            UserHasRentalsException ex) {

        return Map.of(MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleBadCredentials(
            BadCredentialsException ex) {

        return Map.of(
                MESSAGE,
                "Invalid email or password"
        );
    }
    @ExceptionHandler(DriverLicenseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleDriverLicenseNotFound(
            DriverLicenseNotFoundException ex) {

        return Map.of(MESSAGE, ex.getMessage());
    }
    @ExceptionHandler(DriverLicenseAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDriverLicenseAlreadyExists(
            DriverLicenseAlreadyExistsException ex) {

        return Map.of(MESSAGE, ex.getMessage());
    }
}
