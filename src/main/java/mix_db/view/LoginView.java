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
    private final JButton signInButton;
    private final JButton guestButton;

    public LoginView() {
        this.emailField = new JTextField("email");
        this.passwordField = new JTextField("password");
        this.confirmButton = new JButton("conferma");
        this.signInButton = new JButton("iscriviti");
        this.guestButton = new JButton("entra come ospite");

        super.setTitle("MixologyDB_login");
        super.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/7, Toolkit.getDefaultToolkit().getScreenSize().height/4);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel.setLayout(new GridLayout(5, 1, 5, 5));
        
        this.panel.add(this.emailField);
        this.panel.add(this.passwordField);
        this.panel.add(this.confirmButton);
        this.panel.add(this.signInButton);
        this.panel.add(this.guestButton);

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

    /**
     * signs in as new user
     * @param al .
     */
    public void requestedSignIn(ActionListener al) {
        this.signInButton.addActionListener(al);
    }

    /**
     * enters as a guest
     * @param al .
     */
    public void enterAsGuest(ActionListener al) {
        this.guestButton.addActionListener(al);
    }

    /**
     * shows the error via message dialog
     * @param message
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }

}
