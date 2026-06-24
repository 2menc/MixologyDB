package mix_db.view.drink;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import mix_db.data.dao.Bar;
import mix_db.data.dao.Composition;
import mix_db.data.dao.Drink;
import mix_db.data.dao.Review;
import mix_db.data.dao.User;
import mix_db.model.ReviewHelp;
import mix_db.view.ExceptionPanel;
import mix_db.view.FrameIcon;

/**
 * panel that displays detailed information about a specific drink, including its image, reviews, ingredients, and creator.
 */
public class DrinkInformationsPanel extends JPanel{

    private final ButtonsPanel buttonsPanel;

    private boolean isDrinkAlreaySaved;
    private boolean reviewActionsEnabled;

    private final Drink drink;

    private final JTextArea description;
    private final JTextField name;
    private final JTextField category;
    private final JTextArea ingredients;
    private final JTextField keywords;

    private final JTextField creator;
    private final JTextField bar;

    private final JPanel reviews;

    private final JFrame reviewFrame;
    private final JPanel reviewPanel;    
    private final JTextField score;
    private final JTextArea reviewDescription;
    private final JButton sendReview;

    private JLabel creatorHeader;
    private JLabel barCreatorHeader;

    private ActionListener removeReviewListener;

    /**
     * constructs a new panel to display drink information.
     * @param drink the drink to display
     * @param isDrinkAlreaySaved true if the drink is already saved in the user's favorites, false otherwise
     */
    public DrinkInformationsPanel(Drink drink, boolean isDrinkAlreaySaved) {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        this.drink = drink;
    this.isDrinkAlreaySaved = isDrinkAlreaySaved;

        this.buttonsPanel = new ButtonsPanel();
        this.buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        final var subPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        subPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        subPanel.setOpaque(false);

        final var imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setOpaque(false);

        // *image (scaled)
        final ImageIcon image = new ImageIcon(GeneralSettings.fotoPath + drink.getImagePath());
        final var imageLabel = this.getScaledImage(image);

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        imagePanel.add(imageLabel);

        // *reviews
        this.reviews = new JPanel();
        this.reviews.setLayout(new BoxLayout(this.reviews, BoxLayout.Y_AXIS));
        this.reviews.setOpaque(false);

        final JScrollPane reviewsPane = new JScrollPane(reviews);
        reviewsPane.setOpaque(false);
        reviewsPane.getViewport().setOpaque(false);
        reviewsPane.getVerticalScrollBar().setUnitIncrement(5);
        
        reviewsPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY), "Recensioni",
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), Color.GRAY
        ));

        imagePanel.add(reviewsPane);              
        subPanel.add(imagePanel);

        // *description
        final JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setOpaque(false);
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // *name
        this.name = new JTextField(drink.getName().toUpperCase());
        this.name.setEditable(false);
        this.name.setOpaque(false);
        this.name.setBorder(null);
        this.name.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.name.setForeground(Color.WHITE);        
        descriptionPanel.add(this.name);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 2)));

        // *category
        JLabel categoryHeader = new JLabel("CATEGORIA:");
        categoryHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        categoryHeader.setForeground(Color.GRAY);
        descriptionPanel.add(categoryHeader);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        this.category = new JTextField(drink.getCategoryName());
        this.category.setEditable(false);
        this.category.setOpaque(false);
        this.category.setBorder(null);
        this.category.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        this.category.setForeground(Color.LIGHT_GRAY);
        descriptionPanel.add(this.category);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // *creator
        this.creatorHeader = new JLabel("CREATO DA");
        creatorHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        creatorHeader.setForeground(Color.GRAY);
        descriptionPanel.add(creatorHeader);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        this.creator = new JTextField();
        this.creator.setEditable(false);
        this.creator.setOpaque(false);
        this.creator.setBorder(null);
        this.creator.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        this.creator.setForeground(new Color(180, 180, 180));
        descriptionPanel.add(this.creator);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        // *bar
        barCreatorHeader = new JLabel("IDEATO PRESSO");
        barCreatorHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        barCreatorHeader.setForeground(Color.GRAY);
        descriptionPanel.add(barCreatorHeader);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        this.bar = new JTextField();
        this.bar.setEditable(false);
        this.bar.setOpaque(false);
        this.bar.setBorder(null);
        this.bar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        this.bar.setForeground(new Color(180, 180, 180));
        descriptionPanel.add(this.bar);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 25))); 

        // *description
        JLabel descriptionHeader = new JLabel("DESCRIZIONE");
        descriptionHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        descriptionHeader.setForeground(Color.GRAY);
        descriptionPanel.add(descriptionHeader);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        this.description = new JTextArea(drink.getDescription());
        this.description.setEditable(false);
        this.description.setOpaque(false);
        this.description.setBorder(null);
        this.description.setLineWrap(true);
        this.description.setWrapStyleWord(true);
        this.description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        this.description.setForeground(new Color(210, 210, 210));
        descriptionPanel.add(this.description);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // *infredients
        JLabel ingredientsHeader = new JLabel("INGREDIENTI");
        ingredientsHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        ingredientsHeader.setForeground(Color.GRAY);
        descriptionPanel.add(ingredientsHeader);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        this.ingredients = new JTextArea();
        this.ingredients.setEditable(false);
        this.ingredients.setOpaque(false);
        this.ingredients.setBorder(null);
        this.ingredients.setLineWrap(true);
        this.ingredients.setWrapStyleWord(true);
        this.ingredients.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        this.ingredients.setForeground(new Color(210, 210, 210));
        descriptionPanel.add(this.ingredients);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // *tags
        JLabel tagHeader = new JLabel("TAG / PAROLE CHIAVE");
        tagHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        tagHeader.setForeground(Color.GRAY);
        descriptionPanel.add(tagHeader);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        this.keywords = new JTextField();
        this.keywords.setEditable(false);
        this.keywords.setOpaque(false);
        this.keywords.setBorder(null);
        this.keywords.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        this.keywords.setForeground(Color.LIGHT_GRAY);
        descriptionPanel.add(this.keywords);

        subPanel.add(descriptionPanel);

        this.setFavouriteButtonState(isDrinkAlreaySaved);

        this.add(subPanel, BorderLayout.CENTER);
        this.add(this.buttonsPanel, BorderLayout.NORTH);

        this.reviewFrame = new JFrame("Aggiungi una recensione");
        FrameIcon.setIcon(this.reviewFrame);
        
        this.reviewPanel = new JPanel();    
        this.score = new JTextField("voto");
        this.reviewDescription = new JTextArea("descrizione");
        this.sendReview = new JButton("manda recensione");
    }

    /**
     * checks if the image has to be scaled. If it has to, this methos automatically does it
     * @param image the image icon to be scaled
     * @return a new JLabel containing the correct-sized image
     */
    private JLabel getScaledImage(ImageIcon image) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int originalWidth = image.getIconWidth();
        int originalHeight = image.getIconHeight();

        final int targetWidth = dim.width / 4;
        final int targetHeight = dim.height / 3;

        int newWidth = targetWidth;
        int newHeight = targetHeight;

        if (originalWidth > 0 && originalHeight > 0) {

            double widthRatio = (double) targetWidth / originalWidth;
            double heightRatio = (double) targetHeight / originalHeight;
            
            double ratio = Math.min(widthRatio, heightRatio);

            newWidth = (int) (originalWidth * ratio);
            newHeight = (int) (originalHeight * ratio);
        }

        final Image scaledImage = image.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        final ImageIcon scaledIcon = new ImageIcon(scaledImage);

        return new JLabel(scaledIcon);

    }


    /**
     * populates the creator and bar fields with the specified user and bar information.
     * @param creatorUser the user who created the drink
     * @param bar the bar where the drink was created
     */
    public void populateCreatorAndBar(User creatorUser, Bar bar) {
        if (drink.isIBA()) {
            this.creator.setText("Ricetta IBA");
            this.creator.setVisible(true);
            this.barCreatorHeader.setVisible(false);
        } else {
            this.creator.setText(creatorUser.getName().toUpperCase() + " " + creatorUser.getSurname().toUpperCase());
            this.creator.setVisible(true);
            
            if (bar != null) {
                this.bar.setText(bar.getBarName().toUpperCase() + " (" + bar.getCity().toUpperCase() + ")");
                this.bar.setVisible(true);
            } else {
                this.bar.setText("");
                this.barCreatorHeader.setVisible(false);
            }
        }
        this.updateView();
    }

    /**
     * sets add/remove to favourite buttons state, if the drink is already saved or not
     * @param drinkIsSaved true if the drink is already saved, false otherwise
     */
    public void setFavouriteButtonState(boolean drinkIsSaved) {
        this.isDrinkAlreaySaved = drinkIsSaved;
        this.updateButtonsState();
    }

    /**
     * configures the visibility and enabled state of buttons based on the user's login status and role.
     * @param enabled .
     */
    public void setReviewActionsEnabled(boolean enabled) {
        this.reviewActionsEnabled = enabled;
        this.updateButtonsState();
    }

    /**
     * manager for action buttons.
     * Resolves conflicts between session status and favorite database state.
     */
    private void updateButtonsState() {
        if (!this.reviewActionsEnabled) {

            this.buttonsPanel.addFavouriteButton.setEnabled(false);
            this.buttonsPanel.removeFavouriteButton.setEnabled(false);
            this.buttonsPanel.addReviewButton.setEnabled(false);
        } else {

            this.buttonsPanel.addReviewButton.setEnabled(true);
            
            if (this.isDrinkAlreaySaved) {
                this.buttonsPanel.addFavouriteButton.setEnabled(false);
                this.buttonsPanel.removeFavouriteButton.setEnabled(true);
            } else {
                this.buttonsPanel.addFavouriteButton.setEnabled(true);
                this.buttonsPanel.removeFavouriteButton.setEnabled(false);
            }
        }
        this.updateView();
    }

    /**
     * registers an action listener for adding the drink to favorites.
     * @param e the action listener to register
     */
    public void requestedToAddToFavs(ActionListener e) {
        this.buttonsPanel.addFavouriteButton.addActionListener(e);
        this.updateView();
    }

    /**
     * registers an action listener for removing the drink from favorites.
     * @param e the action listener to register
     */
    public void requestedToRemoveToFavs(ActionListener e) {
        this.buttonsPanel.removeFavouriteButton.addActionListener(e);
        this.updateView();;
    }

    /**
     * registers an action listener for opening the review dialog.
     * @param e the action listener to register
     */
    public void requestedToAddReview(ActionListener e) {
        this.buttonsPanel.addReviewButton.addActionListener(e);
    }

    /**
     * registers an action listener for going back to the previous view.
     * @param e the action listener to register
     */
    public void requestedToGoBack(ActionListener e) {
        this.buttonsPanel.backButton.addActionListener(e);
    }

    /**
     * registers an action listener for saving the drink details as a PDF.
     * @param al the action listener to register
     */
    public void requestedToSavePdf(ActionListener al) {
        this.buttonsPanel.saveAsPdfButton.addActionListener(al);
    }

    /**
     * registers an action listener for removing the drink (admin only).
     * @param al the action listener to register
     */
    public void adminRequestedToRemoveDrink(ActionListener al) {
        this.buttonsPanel.removeDrink.addActionListener(al);
    }

    /**
     * registers an action listener for removing a review (admin only).
     * @param al the action listener to register
     */
    public void adminRequestedToRemoveReview(ActionListener al) {
        this.removeReviewListener = al;
    }

    /**
     * sets up and displays the frame for adding a new review.
     */
    public void setUpReviewFrame() {
        this.reviewPanel.add(this.score);
        this.reviewPanel.add(this.reviewDescription);
        this.reviewPanel.add(this.sendReview);

        final Dimension  dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        this.reviewFrame.setSize(dim.width/7, dim.height/7);
        this.reviewFrame.add(this.reviewPanel);
        this.reviewFrame.setVisible(true);
    }

    /**
     * registers an action listener for when the review submission is completed.
     * @param e the action listener to register
     */
    public void reviewFinished(ActionListener e) {
        this.sendReview.addActionListener(e);
    }

    /**
     * retrieves the review information entered by the user.
     * @return the created review, or null if the input is invalid
     */
        public ReviewHelp getReviewInput() {
            try {
                int scoreValue = Integer.parseInt(this.score.getText());
                String desc = this.reviewDescription.getText();
                this.reviewFrame.dispose();
                return new ReviewHelp(desc, scoreValue);
            } catch (NumberFormatException e) {
                new ExceptionPanel(e, reviewFrame);
                return null;
            }
        }

    /**
     * populates the ingredients text area with the list of ingredients.
     * @param ingredients the list of ingredients and their quantities
     */
    public void populateIngredients(List<Composition> ingredients) {
        final StringBuilder sb = new StringBuilder();
        for(var i: ingredients) {
            sb.append(i.getIngredientName() + ", ");
            sb.append(i.getQuantity() + " ");
            sb.append(i.getMeasureUnit() + "\n");
        }
        this.ingredients.setText("Ingredienti:\n" + sb.toString());
    }

    /**
     * populates the reviews scroll pane with the given reviews and their authors.
     * @param revs a map of reviews and the users who wrote them
     */
    public void populateReviewsScrollPane(Map<Review, User> revs, boolean showDeleteButtons) {
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

                final JPanel reviewRow = new JPanel(new BorderLayout(5, 5));
                reviewRow.setOpaque(false);
                reviewRow.add(ta, BorderLayout.CENTER);
                
                if (showDeleteButtons && this.removeReviewListener != null) {
                    final JButton deleteButton = new JButton("elimina");
                    deleteButton.setForeground(Color.ORANGE);
                    deleteButton.putClientProperty("review", r);
                    deleteButton.addActionListener(this.removeReviewListener);

                    final JPanel buttonContainer = new JPanel(new GridBagLayout());
                    buttonContainer.setOpaque(false);
                    buttonContainer.add(deleteButton);
                    
                    reviewRow.add(buttonContainer, BorderLayout.EAST);
                }

                this.reviews.add(reviewRow);

                this.reviews.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
            }
        }
        this.updateView(); 
    }   

    /**
     * populates the keywords text field with the list of keywords.
     * @param kws the list of keywords
     */
    public void populateKeywords(List<String> kws) {
        final StringBuilder sb = new StringBuilder();
        for(String s: kws) {
            sb.append(s + ", ");
        }
        this.keywords.setText(sb.toString());
    }

    /**
     * updates all components of this panel
     */
    private void updateView() {
        this.revalidate();
        this.repaint();
    }

    /**
     * Sets admin controls visibility
     * @param visible .
     */
    public void setAdminControlsVisible(boolean visible) {
        this.buttonsPanel.removeDrink.setVisible(visible);
    }

    private static class ButtonsPanel extends JPanel{
        
        private final JButton addFavouriteButton;
        private final JButton removeFavouriteButton;
        private final JButton addReviewButton;
        private final JButton backButton;
        private final JButton saveAsPdfButton;

        private final JButton removeDrink;

        private ButtonsPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            this.addFavouriteButton = new JButton("aggiungi ai preferiti");
            this.addFavouriteButton.setForeground(Color.YELLOW);
            this.removeFavouriteButton = new JButton("rimuovi dai preferiti");
            this.removeFavouriteButton.setForeground(Color.RED);
            this.addReviewButton = new JButton("Aggiungi una recensione");        
            this.backButton = new JButton("torna indietro");
            this.saveAsPdfButton = new JButton("condividi come pdf");
            
            this.removeDrink = new JButton("elimina ricetta");
            this.removeDrink.setVisible(false);
            this.removeDrink.setForeground(Color.ORANGE);

            this.add(this.addFavouriteButton);
            this.add(this.removeFavouriteButton);
            this.add(this.addReviewButton);
            this.add(this.backButton);
            this.add(this.saveAsPdfButton);
            this.add(this.removeDrink);
        }
    }
}