package mix_db.view;

import java.awt.Component;

import javax.swing.*;

/**
 * simple dialog for simple user messages
 */
public class MessageDialog extends JOptionPane {

    private final String title;

    /**
     * Constructor
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