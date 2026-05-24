package mix_db.view;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

/**
 * simple login window
 */
public class LoginView extends JFrame{

    private final JPanel panel = new JPanel();

    private final JTextField emailField;
    private final JTextField passwordField;
    private final JButton confirmButton;

    public LoginView() {
        this.emailField = new JTextField("email");
        this.passwordField = new JTextField("password");
        this.confirmButton = new JButton("conferma");

        super.setTitle("MixologyDB_login");
        super.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/8, Toolkit.getDefaultToolkit().getScreenSize().width/6);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel.setLayout(new GridLayout(3, 2, 5, 5));
        
        this.panel.add(this.emailField);
        this.panel.add(this.passwordField);
        this.panel.add(this.confirmButton);

        super.add(panel);
        this.setVisible(true);
    }
    
    /**
     * gets the email written in the textfield
     * @return the email
     */
    public String getEmail() {
        return this.emailField.getText();
    }

    /**
     * gets the password written in the textfield
     * @return the password
     */
    public String getPassword() {
        return this.passwordField.getText();
    }

    /**
     * verifies the informations with the controller
     * @param al managed by the controller
     */
    public void verifyLogin(ActionListener al) {
        this.confirmButton.addActionListener(al);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

}
