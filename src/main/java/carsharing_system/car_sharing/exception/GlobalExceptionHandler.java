package carsharing_system.car_sharing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleUserNotFound(
            UserNotFoundException ex){
        return Map.of("message", ex.getMessage());
    }
    @ExceptionHandler(CarAlreadyRentedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleCarAlreadyRented(
            CarAlreadyRentedException ex) {

        return Map.of(
                "message", ex.getMessage()
        );
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleCarNotFound(CarNotFoundException ex) {
        return Map.of(
                "message", ex.getMessage()
        );
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDenied(
            AccessDeniedException ex) {

        return Map.of(
                "message", ex.getMessage()
        );
    }
    @ExceptionHandler(RentalNotActiveException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleRentalNotActive(
            RentalNotActiveException ex) {

        return Map.of(
                "message", ex.getMessage()
        );
    }
}
