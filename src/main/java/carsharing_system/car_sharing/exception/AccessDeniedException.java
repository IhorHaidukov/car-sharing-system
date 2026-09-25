package carsharing_system.car_sharing.exception;

public class AccessDeniedException  extends RuntimeException{

    public AccessDeniedException(String message){
        super(message);
    }
}
