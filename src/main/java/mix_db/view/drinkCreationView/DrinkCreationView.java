package mix_db.view.drinkCreationView;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * drink creation separate window
 */
public class DrinkCreationView extends JFrame{

    private final CreateDrinkPanel mainPanel;
    private final JPanel previewPanel;
    
    /**
     * constructor
     */
    public DrinkCreationView() {

        this.mainPanel = new CreateDrinkPanel();
        this.previewPanel = new JPanel();

        this.add(this.mainPanel);

        this.setTitle("Creazione drink");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/3, (int) (Toolkit.getDefaultToolkit().getScreenSize().height/1.2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public CreateDrinkPanel getMainPanel() {
        return this.mainPanel;
    } 
}
