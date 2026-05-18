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
        this.notifyUser(message);
    }

    /**
     * basic exception 
     * @param cause the cause of the exception
     */

    public DAOException(Throwable cause) {
        super(cause);
        this.notifyUser(cause.getMessage());
    }

    /**
     * basic exception 
     * @param message the message
     * @param cause the cause of the exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
        this.notifyUser(message);
    }

    /**
     * prints the error on the ui
     * @param message
     */
    private void notifyUser(String message) {
        //TODO 
    }

}
