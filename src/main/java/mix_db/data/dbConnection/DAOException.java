package mix_db.data.dbConnection;

import javax.swing.JFrame;

import mix_db.view.ExceptionPanel;

/**
 * DAO standard exception
 */
public class DAOException extends RuntimeException{

    /**
     * creates a standard DAO exception
     * @param message the message
     */
    public DAOException(final String message) {
        super(message);
        this.notifyUser(message);
    }

    /**
     * creates a standard DAO exception with a specified cause
     * @param cause the cause of the exception
     */
    public DAOException(Throwable cause) {
        super(cause);
        this.notifyUser(cause.getMessage());
    }

    /**
     * creates a standard DAO exception with a message and specified cause
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
        final JFrame f = new JFrame("ERROR");
        new ExceptionPanel(message, f);
        this.printStackTrace();
    }

}
