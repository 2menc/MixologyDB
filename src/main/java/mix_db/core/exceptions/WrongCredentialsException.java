package mix_db.core.exceptions;

/**
 * exception thrown when wrong credentials are provided during authentication.
 */
public class WrongCredentialsException extends RuntimeException{

    /**
     * constructs a new wrong credentials exception with the specified detail message.
     *
     * @param message the detail message
     */
    public WrongCredentialsException(String message) {
        super(message);
    }

    /**
     * constructs a new wrong credentials exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public WrongCredentialsException(Throwable cause) {
        super(cause);
    }
}