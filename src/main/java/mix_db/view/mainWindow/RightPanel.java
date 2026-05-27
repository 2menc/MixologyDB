package mix_db.view.mainWindow;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
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

    public RightPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.searchBar = new JTextField("cerca drink");
        final var dim = this.getSize();
        this.searchBar.setPreferredSize(new Dimension(dim.width, dim.height/10));
        this.searchBar.setAlignmentX(LEFT_ALIGNMENT);

        this.createDrinkButton = new JButton("crea un drink");
        this.createDrinkButton.setAlignmentX(LEFT_ALIGNMENT);

        this.userInformations = new JTextArea();
        this.userInformations.setEditable(false);
        this.userInformations.setAlignmentX(LEFT_ALIGNMENT);

        final User user = Session.getInstance().getLoggedUser();
        if(user != null) {
            this.userInformations.setText(user.getName() + ", " + user.getSurname() + "\n" + user.getEmail());
        } else {
            this.userInformations.setEnabled(false);
        }

        this.add(this.createDrinkButton);
        this.add(this.userInformations);
        this.add(this.searchBar);
    }

}
