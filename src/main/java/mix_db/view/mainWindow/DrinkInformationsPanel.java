package mix_db.view.mainWindow;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DrinkInformationsPanel extends JPanel{

    private final JButton addFavouriteButton;
    private final JButton removeFavouriteButton;
    private final JTextArea description;
    private final JTextField name;
    private final JLabel imageLabel;

    public DrinkInformationsPanel() {
        this.addFavouriteButton = new JButton("aggiungi ai preferiti");
        this.removeFavouriteButton = new JButton("rimuovi dai preferiti");
        this.description = new JTextArea();
        this.name = new JTextField();
        this.imageLabel = new JLabel();
    }

    public void setFavouriteButtonState(boolean isSaved) {
        if(isSaved) {
            this.addFavouriteButton.setEnabled(false);
            this.removeFavouriteButton.setEnabled(true);
        } else {
            this.addFavouriteButton.setEnabled(true);
            this.removeFavouriteButton.setEnabled(false);
        }
    }

}
