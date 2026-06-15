package mix_db.view.drink;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import mix_db.core.Session;
import mix_db.data.dao.Composition;
import mix_db.data.dao.Drink;
import mix_db.data.dao.Review;
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
        this.setOpaque(false);

        this.drink = drink;

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

        this.name = new JTextField(drink.getName().toUpperCase());
        this.name.setEditable(false);
        this.name.setOpaque(false);
        this.name.setBorder(null);
        this.name.setFont(new Font("Segoe UI", Font.BOLD, 26));
        this.name.setForeground(Color.WHITE);        
        descriptionPanel.add(this.name);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 2)));

        this.category = new JTextField(drink.getCategoryName());
        this.category.setEditable(false);
        this.category.setOpaque(false);
        this.category.setBorder(null);
        this.category.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        this.category.setForeground(Color.LIGHT_GRAY);
        descriptionPanel.add(this.category);
        descriptionPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
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
        
        this.reviewPanel = new JPanel();    
        this.score = new JTextField("voto");
        this.reviewDescription = new JTextArea("descrizione");
        this.sendReview = new JButton("manda recensione");
    }

    /**
     * checks if the image has to be scaled. If it has to, this methos automatically does it
     * @param image
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

    public void requestedToSavePdf(ActionListener al) {
        this.buttonsPanel.saveAsPdfButton.addActionListener(al);
    }

    public void setUpReviewFrame() {
        this.reviewPanel.add(this.score);
        this.reviewPanel.add(this.reviewDescription);
        this.reviewPanel.add(this.sendReview);

        final Dimension  dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        this.reviewFrame.setSize(dim.width/7, dim.height/7);
        this.reviewFrame.add(this.reviewPanel);
        this.reviewFrame.setVisible(true);
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
            new ExceptionPanel(e, reviewFrame);
            return null;
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
        private final JButton saveAsPdfButton;

        private ButtonsPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            this.addFavouriteButton = new JButton("aggiungi ai preferiti");
            this.addFavouriteButton.setForeground(Color.YELLOW);
            this.removeFavouriteButton = new JButton("rimuovi dai preferiti");
            this.removeFavouriteButton.setForeground(Color.RED);
            this.addReviewButton = new JButton("Aggiungi una recensione");        
            this.backButton = new JButton("torna indietro");
            this.saveAsPdfButton = new JButton("condividi come pdf");

            this.add(this.addFavouriteButton);
            this.add(this.removeFavouriteButton);
            this.add(this.addReviewButton);
            this.add(this.backButton);
            this.add(this.saveAsPdfButton);
        }
    }
}
