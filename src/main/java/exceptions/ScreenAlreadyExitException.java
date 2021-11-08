package exceptions;

public class ScreenAlreadyExitException extends RuntimeException{
    public ScreenAlreadyExitException(String message){
        super(message);
    }
}
