package carsharing_system.car_sharing.exception;

public class RentalNotFoundException extends RuntimeException{
    public  RentalNotFoundException(String message){
        super(message);
    }
}
