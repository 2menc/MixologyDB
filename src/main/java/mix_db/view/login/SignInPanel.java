package mix_db.view.login;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Optional;

/**
 * simple sign in panel
 */
public class SignInPanel extends JPanel{

    private final JTextField emailField;
    private final JTextField passwordField;
    private final JTextField nameField;
    private final JTextField surnameField;

    // *birth date fields
    private final JTextField dayField;
    private final JTextField monthField;
    private final JTextField yearField;

    private final JButton confirmButton;

    /**
     * initializes the sign-in panel, setting up text fields for user input (email, password, name, surname, birth date)
     * and a confirmation button, then arranges them in a grid layout.
     */
    public SignInPanel() {
        this.emailField = new JTextField("email");
        this.passwordField = new JTextField("password");
        this.nameField = new JTextField("nome");
        this.surnameField = new JTextField("cognome");

        this.confirmButton = new JButton("conferma");

        this.dayField = new JTextField("giorno");
        this.monthField = new JTextField("mese");
        this.yearField = new JTextField("anno");

        this.setLayout(new GridLayout(4, 1, 5, 5));
        
        // *panel 1
        final var p1 = new JPanel(new GridLayout(1, 2, 5, 5));
        p1.add(this.emailField);
        p1.add(this.passwordField);

        // *panel 2
        final var p2 = new JPanel(new GridLayout(1, 2, 5, 5));
        p2.add(this.nameField);
        p2.add(this.surnameField);

        // *panel 3
        final var p3 = new JPanel(new GridLayout(1, 3, 10, 5));
        p3.add(this.dayField);
        p3.add(this.monthField);
        p3.add(this.yearField);

        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(this.confirmButton);
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
     * gets the name entered by the user.
     * @return the user's name.
     */
    public String getName() {
        return this.nameField.getText();
    }

    /**
     * gets the surname entered by the user.
     * @return the user's surname.
     */
    public String getSurname() {
        return this.surnameField.getText();
    }
    
    /**
     * gets the birth date as {@link sql.date}
     * @return the date
     */
    @SuppressWarnings("deprecation")
    public Optional<Date> getBirthDate() {
        try {
            final int day = Integer.parseInt(this.dayField.getText());
            final int month = Integer.parseInt(this.monthField.getText());
            final int year = Integer.parseInt(this.yearField.getText());

            return Optional.of(new Date(year, month, day));

        } catch (NumberFormatException e) {
            throw e;
        }        
    }

    /**
     * registers an {@link ActionListener} to be notified when the sign-in confirmation button is clicked.
     * @param al the {@link ActionListener} to be added.
     */
    public void requestSignIn(ActionListener al) {
        this.confirmButton.addActionListener(al);
    }
}