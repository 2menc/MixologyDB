package mix_db.view.login;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * simple sign in panel
 */
public class LoginPanel extends JPanel{

    private final JTextField emailField;
    private final JTextField passwordField;
    private final JButton confirmButton;
    private final JButton signInButton;
    private final JButton guestButton;

    /**
     * creates the login panel with interactive input components
     */
    public LoginPanel() {

        this.emailField = new JTextField("email");
        this.passwordField = new JTextField("password");
        this.confirmButton = new JButton("conferma");
        this.signInButton = new JButton("iscriviti");
        this.guestButton = new JButton("entra come ospite");


        this.setLayout(new GridLayout(5, 1, 5, 5));
        
        this.add(this.emailField);
        this.add(this.passwordField);
        this.add(this.confirmButton);
        this.add(this.signInButton);
        this.add(this.guestButton);

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
     * @param al the ActionEvent listener managed by controller
     */
    public void requestSignIn(ActionListener al) {
        this.signInButton.addActionListener(al);
    }

    /**
     * enters as a guest
     * @param al the ActionEvent listener managed by controller
     */
    public void requestToEnterAsGuest(ActionListener al) {
        this.guestButton.addActionListener(al);
    }
}