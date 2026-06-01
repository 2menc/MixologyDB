package mix_db.view.mainWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mix_db.core.Session;
import mix_db.data.dao.User;

/**
 * right main panel: user informations, search bar, create drink button 
 */
public class RightPanel extends JPanel{

    private final JTextField searchBar;
    private final JButton createDrinkButton;
    private final JTextArea userInformations;

    private final JButton logoutButton;

    public RightPanel() {
        this.setLayout(new GridLayout(20, 1));

        this.searchBar = new JTextField("cerca drink");
        final var dim = this.getSize();
        this.searchBar.setPreferredSize(new Dimension(dim.width, dim.height/10));
        this.searchBar.setAlignmentX(LEFT_ALIGNMENT);

        this.createDrinkButton = new JButton("crea un drink");
        this.createDrinkButton.setAlignmentX(LEFT_ALIGNMENT);

        this.logoutButton = new JButton("log out");
        this.logoutButton.setForeground(Color.RED);

        this.userInformations = new JTextArea();
        this.userInformations.setEditable(false);
        this.userInformations.setAlignmentX(LEFT_ALIGNMENT);

        final User user = Session.getInstance().getLoggedUser();
        if(user != null) {
            this.userInformations.setText(user.getName() + " " + user.getSurname() + "\n" + user.getEmail());
        } else {
            this.userInformations.setEnabled(false);
        }

        if(! Session.getInstance().isLoggedIn()) {
            this.disableButtonsForGuests();
        }

        this.add(this.createDrinkButton, 0);
        this.add(this.userInformations, 1);
        this.add(this.searchBar, 2);
        this.add(this.logoutButton, 3);
    }

    /**
     * sends a drink creation request
     * @param al .
     */
    public void requestedToCreateDrink(ActionListener al) {
        this.createDrinkButton.addActionListener(al);
    }

    /**
     * requests to log out
     * @param al .
     */
    public void requestedToLogOut(ActionListener al) {
        this.logoutButton.addActionListener(al);
    }

    /**
     * if the user is not logged in disables logged-in user's butotns
     */
    private void disableButtonsForGuests() {
        this.createDrinkButton.setEnabled(false);

        this.logoutButton.setText("iscriviti");
        this.logoutButton.setForeground(Color.GREEN);
    }

}
