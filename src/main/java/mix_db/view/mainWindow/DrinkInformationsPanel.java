package mix_db.view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mix_db.core.GeneralSettings;
import mix_db.core.Session;
import mix_db.data.dao.Composition;
import mix_db.data.dao.Drink;
import mix_db.data.dao.Review;
import mix_db.data.dao.Tag;
import mix_db.data.dao.User;
import mix_db.view.ExceptionPanel;

public class DrinkInformationsPanel extends JPanel{

    private final ButtonsPanel buttonsPanel;

    private final Drink drink;

    private final JTextArea description;
    private final JTextField name;
    private final JTextField category;
    private final JTextArea ingredients;
    private final JTextField keywords;

    private final JPanel reviews;

    private final JFrame reviewFrame;
    private final JPanel reviewPanel;    
    private final JTextField score;
    private final JTextArea reviewDescription;
    private final JButton sendReview;


    public DrinkInformationsPanel(Drink drink, boolean isDrinkAlreaySaved) {
        this.setLayout(new BorderLayout());

        this.drink = drink;

        this.buttonsPanel = new ButtonsPanel();
        final var subPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        final var imagePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        // *image
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        final ImageIcon image = new ImageIcon(GeneralSettings.fotoPath + drink.getImagePath());
        final Image scaledImage = image.getImage().getScaledInstance(dim.width/4, dim.height/3, Image.SCALE_SMOOTH);
        final ImageIcon scaledIcon = new ImageIcon(scaledImage);

        final JLabel imageLabel = new JLabel(scaledIcon);
        imagePanel.add(imageLabel);

        // *reviews
        this.reviews = new JPanel();
        final JScrollPane reviewsPane = new JScrollPane(reviews);

        imagePanel.add(reviewsPane);              
        subPanel.add(imagePanel);

        // *description
        final JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));

        this.name = new JTextField("nome: " + drink.getName());
        this.name.setEditable(false);
        descriptionPanel.add(name);

        this.description = new JTextArea("Descrizione:\n" + drink.getDescription());
        this.description.setEditable(false);
        descriptionPanel.add(description);

        this.category = new JTextField("Categoria: " + drink.getCategoryName());
        this.category.setEditable(false);
        descriptionPanel.add(category);
        
        this.ingredients = new JTextArea();
        this.ingredients.setEditable(false);
        descriptionPanel.add(this.ingredients);

        this.keywords = new JTextField();
        this.keywords.setEditable(false);
        descriptionPanel.add(this.keywords);

        subPanel.add(descriptionPanel);

        this.setFavouriteButtonState(isDrinkAlreaySaved);

        this.add(subPanel, BorderLayout.CENTER);
        this.add(this.buttonsPanel, BorderLayout.NORTH);

        this.reviewFrame = new JFrame("Aggiungi una recensione");
        this.reviewPanel = new JPanel();    
        this.score = new JTextField("voto");
        this.reviewDescription = new JTextArea("descrizione");
        this.sendReview = new JButton("manda recensione");
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

        this.updateView();;
    }

    public void requestedToAddToFavs(ActionListener e) {
        this.buttonsPanel.addFavouriteButton.addActionListener(e);
        this.updateView();
    }

    public void requestedToRemoveToFavs(ActionListener e) {
        this.buttonsPanel.removeFavouriteButton.addActionListener(e);
        this.updateView();;
    }

    public void requestedToAddReview(ActionListener e) {
        this.buttonsPanel.addReviewButton.addActionListener(e);
    }

    public void requestedToGoBack(ActionListener e) {
        this.buttonsPanel.backButton.addActionListener(e);
    }

    public void setUpReviewFrame() {
        this.reviewPanel.add(this.score);
        this.reviewPanel.add(this.reviewDescription);
        this.reviewPanel.add(this.sendReview);

        this.reviewFrame.add(this.reviewPanel);
        this.reviewFrame.setVisible(true);
        this.reviewFrame.pack();
    }

    public void reviewFinished(ActionListener e) {
        this.sendReview.addActionListener(e);
    }

    public Review getReviewInformation() {
        try {
            final var r =  new Review(drink.getDrinkID(), Session.getInstance().getLoggedUser().getUserID(), 
                    this.reviewDescription.getText(), null, Integer.parseInt(this.score.getText()));

            this.reviewFrame.dispose();

            this.updateView();
            return r;
        } catch (NumberFormatException e) {
            throw new ExceptionPanel(e, reviewFrame);
        }
    }

    public void populateIngredients(List<Composition> ingredients) {
        final StringBuilder sb = new StringBuilder();
        for(var i: ingredients) {
            sb.append(i.getIngredientName() + ", ");
            sb.append(i.getQuantity() + " ");
            sb.append(i.getMeasureUnit() + "\n");
        }
        this.ingredients.setText("Ingredienti:\n" + sb.toString());
    }

    public void populateReviewsScrollPane(Map<Review, User> revs) {
        this.reviews.removeAll(); 

        if (revs == null || revs.isEmpty()) {
            this.reviews.add(new JLabel("Nessuna recensione per questo drink."));
        } else {
            for(var r: revs.keySet()) {
                final StringBuilder sb = new StringBuilder();
                sb.append(r .getReviewDate()).append(" - ");
                sb.append(revs.get(r).getName() + " " + revs.get(r).getSurname());
                sb.append(" - Punteggio: ").append(r.getScore()).append("\n");
                sb.append(r.getDescription());

                final JTextArea ta = new JTextArea(sb.toString());
                ta.setEditable(false);
                ta.setLineWrap(true);       
                ta.setWrapStyleWord(true);  
                
                ta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY),
                    javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5) 
                ));

                this.reviews.add(ta);
                
                this.reviews.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
            }
        }
        this.updateView(); 
    }   

    public void populateKeywords(List<Tag> kws) {
        final StringBuilder sb = new StringBuilder();
        for(Tag k: kws) {
            final String s = k.getKeyword();
            sb.append(s + ", ");
        }
        this.keywords.setText(sb.toString());
    }

    /**
     * updates all components of this panel
     */
    private void updateView() {
        this.validate();
        this.repaint();
    }

    /**
     * if the user is a guest, disables buttons that requires login
     */
    public void disableButtonsForGuests() {
        if(! Session.getInstance().isLoggedIn()) {
            this.buttonsPanel.addFavouriteButton.setEnabled(false);
            this.buttonsPanel.removeFavouriteButton.setEnabled(false);
            this.buttonsPanel.addReviewButton.setEnabled(false);
        }
    }

    private static class ButtonsPanel extends JPanel{
        
        private final JButton addFavouriteButton;
        private final JButton removeFavouriteButton;
        private final JButton addReviewButton;
        private final JButton backButton;

        private ButtonsPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            this.addFavouriteButton = new JButton("aggiungi ai preferiti");
            this.addFavouriteButton.setForeground(Color.YELLOW);
            this.removeFavouriteButton = new JButton("rimuovi dai preferiti");
            this.removeFavouriteButton.setForeground(Color.RED);
            this.addReviewButton = new JButton("Aggiungi una recensione");        
            
            this.backButton = new JButton("torna alla lista dei drink");

            this.add(this.addFavouriteButton);
            this.add(this.removeFavouriteButton);
            this.add(this.addReviewButton);
            this.add(this.backButton);
        }
    }
}
