package mix_db.view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mix_db.core.GeneralSettings;
import mix_db.core.Session;
import mix_db.data.dao.Composition;
import mix_db.data.dao.Drink;

public class DrinkInformationsPanel extends JPanel{

    private final ButtonsPanel buttonsPanel;

    private final JTextArea description;
    private final JTextField name;
    private final JTextField category;
    private final JTextArea ingredients;
    //TODO private final JTextField keywords;

    public DrinkInformationsPanel(Drink drink, boolean isDrinkAlreaySaved) {
        this.setLayout(new BorderLayout());

        this.buttonsPanel = new ButtonsPanel();
        final var subPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // *image
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        final ImageIcon image = new ImageIcon(GeneralSettings.fotoPath + drink.getImagePath());
        final Image scaledImage = image.getImage().getScaledInstance(dim.width/4, dim.height/3, Image.SCALE_SMOOTH);
        final ImageIcon scaledIcon = new ImageIcon(scaledImage);

        final JLabel imageLabel = new JLabel(scaledIcon);
        subPanel.add(imageLabel);

        // *description
        final JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));

        this.name = new JTextField(drink.getName());
        this.name.setEditable(false);
        descriptionPanel.add(name);
        this.description = new JTextArea(drink.getDescription());
        this.description.setEditable(false);
        descriptionPanel.add(description);
        this.category = new JTextField(drink.getCategoryName());
        this.category.setEditable(false);
        descriptionPanel.add(category);
        this.ingredients = new JTextArea();
        this.ingredients.setEditable(false);
        descriptionPanel.add(this.ingredients);

        subPanel.add(descriptionPanel);

        this.setFavouriteButtonState(isDrinkAlreaySaved);

        this.add(subPanel, BorderLayout.CENTER);
        this.add(this.buttonsPanel, BorderLayout.NORTH);
    }

    /**
     * sets add/remove to favourite buttons state, if the drink is already saved or not
     * @param drinkIsSaved
     */
    public void setFavouriteButtonState(boolean drinkIsSaved) {
        if(drinkIsSaved) {
            buttonsPanel.addFavouriteButton.setEnabled(false);
            buttonsPanel.removeFavouriteButton.setEnabled(true);
        } else {
            buttonsPanel.addFavouriteButton.setEnabled(true);
            buttonsPanel.removeFavouriteButton.setEnabled(false);
        }

        this.revalidate();
        this.repaint();
    }

    public void requestedToAddToFavs(ActionListener e) {
        this.buttonsPanel.addFavouriteButton.addActionListener(e);
        this.revalidate();
        this.repaint();
    }

    public void requestedToRemoveToFavs(ActionListener e) {
        this.buttonsPanel.removeFavouriteButton.addActionListener(e);
        this.revalidate();
        this.repaint();
    }

    public void populateIngredients(List<Composition> ingredients) {
        final StringBuilder sb = new StringBuilder();
        for(var i: ingredients) {
            sb.append(i.getIngredientName() + ", ");
            sb.append(i.getQuantity() + " ");
            sb.append(i.getMeasureUnit() + "\n");
        }
        this.ingredients.setText(sb.toString());
    }

    private static class ButtonsPanel extends JPanel{
        
        private final JButton addFavouriteButton;
        private final JButton removeFavouriteButton;
        private final JButton addReviewButton;


        private ButtonsPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            this.addFavouriteButton = new JButton("aggiungi ai preferiti");
            this.addFavouriteButton.setForeground(Color.YELLOW);
            this.removeFavouriteButton = new JButton("rimuovi dai preferiti");
            this.removeFavouriteButton.setForeground(Color.RED);
            this.addReviewButton = new JButton("Aggiungi una recensione");            

            this.add(this.addFavouriteButton);
            this.add(this.removeFavouriteButton);
            this.add(this.addReviewButton);
        }
            
        /**
         * if the user is a guest, disables buttons that requires login
         */
        private void disableButtonsForGuests() {
            if(! Session.getInstance().isLoggedIn()) {
                this.addFavouriteButton.setEnabled(false);
                this.removeFavouriteButton.setEnabled(false);
                this.addReviewButton.setEnabled(false);
            }
        }

    }
}
