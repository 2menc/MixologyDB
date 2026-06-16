package mix_db.view.mainWindow;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * left main panel, for leaderboards
 */
public class LeftPanel extends JPanel{

    private final JTextArea userWithMostPositiveReviews;
    private final JTextArea mostUsedIngredients;
    private final JTextArea trendingKeywords;

    /**
     * constructs a new {@code LeftPanel} instance, initializing its layout, borders, and internal components for displaying leaderboards.
     */
    public LeftPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false); 
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.userWithMostPositiveReviews = this.createStyledTextArea();
        this.mostUsedIngredients = this.createStyledTextArea();
        this.trendingKeywords = this.createStyledTextArea();

        final JLabel l1 = this.createStyledHeader("UTENTI CON MIGLIORI RECENSIONI");
        final JLabel l2 = this.createStyledHeader("INGREDIENTI PIÙ UTILIZZATI");
        final JLabel l3 = this.createStyledHeader("GUSTI DI TENDENZA (ULTIMI 30 GIORNI)");

        this.add(l1);
        this.add(Box.createRigidArea(new java.awt.Dimension(0, 8)));
        this.add(this.userWithMostPositiveReviews);
        this.add(Box.createRigidArea(new java.awt.Dimension(0, 30))); 

        this.add(l2);
        this.add(Box.createRigidArea(new java.awt.Dimension(0, 8)));
        this.add(this.mostUsedIngredients);
        this.add(Box.createRigidArea(new java.awt.Dimension(0, 30)));

        this.add(l3);
        this.add(Box.createRigidArea(new java.awt.Dimension(0, 8)));
        this.add(this.trendingKeywords);

        this.add(Box.createVerticalGlue());
    }

    /**
     * creates a text area with custom stylr
     * @return teh text area
     */
    private JTextArea createStyledTextArea() {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setOpaque(false); 
        ta.setBorder(null); 
        ta.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        ta.setForeground(new java.awt.Color(220, 220, 220)); 
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        ta.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT); 

        return ta;
    }

    /**
     * creates a text area header with custom style
     * @return the header
     */
    private JLabel createStyledHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        label.setForeground(java.awt.Color.GRAY); 

        label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT); 

        return label;
    }

    /**
     * gets the text area displaying users with the most positive reviews.
     * @return the {@code JTextArea} for user reviews.
     */
    public JTextArea getUserWithMostPositiveReviewsTa() {
        return userWithMostPositiveReviews;
    }

    /**
     * gets the text area displaying the most used ingredients.
     * @return the {@code JTextArea} for most used ingredients.
     */
    public JTextArea getMostUsedIngredientsTa() {
        return mostUsedIngredients;
    }

    /**
     * gets the text area displaying trending keywords.
     * @return the {@code JTextArea} for trending keywords.
     */
    public JTextArea getTrendingKeywordsTa() {
        return trendingKeywords;
    }

    /**
     * populates the text area for users with most positive reviews with the provided list of strings.
     * @param list a list of strings, each representing a user with positive reviews.
     */
    public void populateUserWithMostPositiveReviews(List<String> list) {
        this.userWithMostPositiveReviews.setText(String.join("\n", list)); 
        this.userWithMostPositiveReviews.setEditable(false);  
    }

    /**
     * populates the text area for most used ingredients with the provided list of strings.
     * @param list a list of strings, each representing a most used ingredient.
     */
    public void populateMostUsedIngredients(List<String> list) {
        // Semplificato: ora accetta e stampa direttamente le stringhe pulite inviate dal controller! [1]
        this.mostUsedIngredients.setText(String.join("\n", list)); 
        this.mostUsedIngredients.setEditable(false);  
    }

    /**
     * populates the text area for trending keywords with the provided list of strings.
     * @param list a list of strings, each representing a trending keyword.
     */
    public void populateTrendingKeywords(List<String> list) {
        this.trendingKeywords.setText(String.join("\n", list)); 
        this.trendingKeywords.setEditable(false);  
    }
}