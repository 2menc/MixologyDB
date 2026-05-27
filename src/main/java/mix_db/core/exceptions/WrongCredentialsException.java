package mix_db.core.exceptions;

public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException(String message) {
        super(message);
    }

    public WrongCredentialsException(Throwable cause) {
        super(cause);
    }
}
