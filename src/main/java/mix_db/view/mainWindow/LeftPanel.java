package mix_db.view.mainWindow;

import java.awt.GridLayout;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.regex.Matcher;

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

    public LeftPanel() {
        this.setLayout(new GridLayout(8, 1));

        this.userWithMostPositiveReviews = new JTextArea();

        this.mostUsedIngredients = new JTextArea();
        this.trendingKeywords = new JTextArea();

        final JLabel l1 = new JLabel("utenti con migliori recensioni:");
        final JLabel l2 = new JLabel("ingredienti più utilizzati: ");
        final JLabel l3 = new JLabel("gusti di tendenza (ultimi 30 giorni): ");

        this.add(l1, 0);
        this.add(this.userWithMostPositiveReviews, 1);
        this.add(l2, 2);
        this.add(this.mostUsedIngredients, 3);
        this.add(l3, 4);
        this.add(this.trendingKeywords, 5);
    }

    public JTextArea getUserWithMostPositiveReviewsTa() {
        return userWithMostPositiveReviews;
    }

    public JTextArea getMostUsedIngredientsTa() {
        return mostUsedIngredients;
    }

    public JTextArea getTrendingKeywordsTa() {
        return trendingKeywords;
    }

    public void populateUserWithMostPositiveReviews(List<String> list) {
        this.userWithMostPositiveReviews.setText(String.join("\n", list)); 
        this.userWithMostPositiveReviews.setEditable(false);  
    }

    public void populateMostUsedIngredients(List<String> list) {
        // Semplificato: ora accetta e stampa direttamente le stringhe pulite inviate dal controller! [1]
        this.mostUsedIngredients.setText(String.join("\n", list)); 
        this.mostUsedIngredients.setEditable(false);  
    }

    public void populateTrendingKeywords(List<String> list) {
        this.trendingKeywords.setText(String.join("\n", list)); 
        this.trendingKeywords.setEditable(false);  
    }
}
