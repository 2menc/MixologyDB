package mix_db.view;

import java.awt.Component;

import javax.swing.*;

/**
 * simple dialog for simple user messages
 */
public class MessageDialog extends JOptionPane {

    private final String title;

    /**
     * showing a dialog panel with a message, title, type, and specific parent component frame
     * @param title the dialog title
     * @param message the message content to display
     * @param messageType the type of the option panel message (e.g. error, information, warning)
     * @param parent the parent Component which hosts this dialog
     */
    public MessageDialog(String title, String message, int messageType, Component parent) {
        super(
            message,
            messageType,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[]{"OK"},
            "OK"
        );
        this.title = title;

        JDialog dialog = this.createDialog(parent, this.title);
        
        dialog.setResizable(false);
        dialog.setVisible(true); 
        
        dialog.dispose(); 

    }

}