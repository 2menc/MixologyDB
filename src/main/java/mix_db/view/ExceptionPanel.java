package mix_db.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * dynamic exception warning dialog panel content
 */
public class ExceptionPanel extends RuntimeException{

    /**
     * shows a dialog containing the message
     * @param message the message to show
     * @param frame the frame which will host the panel
     */
    public ExceptionPanel(String message, JFrame frame) {
        super(message);
        JOptionPane.showMessageDialog(frame, message, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * shows a dialog containing the error message
     * @param cause the cause of the error
     * @param frame the frame which will host the panel
     */
    public ExceptionPanel(Throwable cause, JFrame frame) {
        super(cause);
        JOptionPane.showMessageDialog(frame, cause.getMessage(), "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

}
