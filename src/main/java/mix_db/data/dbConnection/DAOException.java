package mix_db.data.dbConnection;

/**
 * DAO standard exception
 */
public class DAOException extends RuntimeException{

    /**
     * basic exception 
     * @param message the message
     */
    public DAOException(final String message) {
        super(message);
    }

    /**
     * basic exception 
     * @param cause the cause of the exception
     */

    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * basic exception 
     * @param message the message
     * @param cause the cause of the exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
