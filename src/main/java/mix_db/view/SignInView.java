package mix_db.view;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.sql.Date;
import java.util.Optional;

/**
 * simple login window
 */
public class SignInView extends JFrame{

    private final JPanel panel = new JPanel();

    private final JTextField emailField;
    private final JTextField passwordField;
    private final JTextField nameField;
    private final JTextField surnameField;

    // *birth date fields
    private final JTextField dayField;
    private final JTextField monthField;
    private final JTextField yearField;

    private final JButton confirmButton;

    public SignInView() {
        this.emailField = new JTextField("email");
        this.passwordField = new JTextField("password");
        this.nameField = new JTextField("nome");
        this.surnameField = new JTextField("cognome");

        this.confirmButton = new JButton("conferma");

        this.dayField = new JTextField("day");
        this.monthField = new JTextField("month");
        this.yearField = new JTextField("year");

        super.setTitle("MixologyDB_login");
        super.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/6, Toolkit.getDefaultToolkit().getScreenSize().height/4);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel.setLayout(new GridLayout(5, 1, 5, 1));
        
        this.panel.add(this.emailField);
        this.panel.add(this.passwordField);
        this.panel.add(this.nameField);
        this.panel.add(this.surnameField);

        final var p = new Panel(new GridLayout(1, 3, 10, 5));
        p.add(this.dayField);
        p.add(this.monthField);
        p.add(this.yearField);
        this.panel.add(p);

        this.panel.add(this.confirmButton);

        super.add(panel);
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
     * gets user name
     */
    public String getName() {
        return this.nameField.getText();
    }

    /**
     * gets user surname
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
            this.showError(e.getMessage());
        }
        return Optional.empty();
        
    }

    /**
     * shows the error via message dialog
     * @param message
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }
}
