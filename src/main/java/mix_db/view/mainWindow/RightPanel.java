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

    private boolean isSearching = false;
    private final JButton changePopulationButton;

    private final JTextField searchBar;
    private final JButton searchButton;
    private final JButton createDrinkButton;
    private final JTextArea userInformations;

    private final JButton showFavouritesButton;
    private final JButton createBarButton;

    private final JButton logoutButton;

    public RightPanel() {
        this.setLayout(new GridLayout(20, 1));

        this.searchBar = new JTextField("cerca drink");
        final var dim = this.getSize();
        this.searchBar.setPreferredSize(new Dimension(dim.width, dim.height/10));
        this.searchBar.setAlignmentX(LEFT_ALIGNMENT);

        this.searchButton = new JButton("cerca");
        this.searchButton.setPreferredSize(new Dimension(this.searchBar.getWidth()/2, this.searchBar.getHeight()));
        this.searchButton.setAlignmentX(LEFT_ALIGNMENT);

        this.createDrinkButton = new JButton("crea un drink");
        this.createDrinkButton.setAlignmentX(LEFT_ALIGNMENT);

        this.logoutButton = new JButton("log out");
        this.logoutButton.setForeground(Color.RED);

        this.userInformations = new JTextArea();
        this.userInformations.setEditable(false);
        this.userInformations.setAlignmentX(LEFT_ALIGNMENT);

        this.createBarButton = new JButton("registra il tuo bar");
        this.createBarButton.setAlignmentX(LEFT_ALIGNMENT);

        final User user = Session.getInstance().getLoggedUser();
        if(user != null) {
            this.userInformations.setText(user.getName() + " " + user.getSurname() + "\n" + user.getEmail());
        } else {
            this.userInformations.setEnabled(false);
        }

        if(! Session.getInstance().isLoggedIn()) {
            this.disableButtonsForGuests();
        }

        this.changePopulationButton = new JButton("torna indietro");
        this.changePopulationButton.setVisible(false);

        this.showFavouritesButton = new JButton("mostra preferiti");

        this.add(this.createDrinkButton);
        this.add(this.userInformations);
        this.add(this.searchBar);
        this.add(this.searchButton);
        this.add(this.changePopulationButton);
        this.add(this.showFavouritesButton);
        this.add(this.createBarButton);
        this.add(this.logoutButton);
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
     * requested to search the drink with informations in the search bar
     * @param al .
     */
    public void requestedToSearchDrink(ActionListener al) {
        this.searchButton.addActionListener(al);
    }

    /**
     * requests to show favourites
     * @param al .
     */
    public void requestedToShowFavs(ActionListener al) {
        this.showFavouritesButton.addActionListener(al);
    }

    /**
     * requests to create a new bar
     * @param al .
     */
    public void requestedToCreateBar(ActionListener al) {
        this.createBarButton.addActionListener(al);
    }

    /**
     * if the user is not logged in disables logged-in user's butotns
     */
    private void disableButtonsForGuests() {
        this.createDrinkButton.setEnabled(false);

        this.logoutButton.setText("iscriviti");
        this.logoutButton.setForeground(Color.GREEN);

        this.showFavouritesButton.setEnabled(false);
        this.createBarButton.setEnabled(false);
    }

    public void setupCreateBarButton(boolean setActive) {
        this.createBarButton.setEnabled(setActive);
    }

    /**
     * gets the search bar text
     * @return the String searched
     */
    public String getSearchBarContent() {
        return this.searchBar.getText();
    }

    /**
     * returns the state of CentralPanel population
     * @return true if the CentralPanel is populated by searched drinks, false otherwise
     */
    public boolean isSearching() {
        return this.isSearching;
    }

    /**
     * toggles the CentralPanel population state
     * ! IMPORTANT: just a flag
     */
    public void toggleSearching(ActionListener al) {
        this.changePopulationButton.addActionListener(al);
        this.isSearching = ! this.isSearching;
        this.toggleBackButton();
    }

    /**
     * toggles the {@link changePopulationButton} button
     */
    private void toggleBackButton() {
        this.changePopulationButton.setVisible(!this.changePopulationButton.isVisible());
    }

    public void disableSearch() {
        this.searchButton.setEnabled(false);

        this.searchBar.setText("ricerca disattivata");
        this.searchBar.setEditable(false);
    }
}
