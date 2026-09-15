package carsharing_system.car_sharing.exception;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(String  message){
        super(message);
    }
}
