package mix_db.view.drink;

import java.awt.Toolkit;

import javax.swing.JFrame;

import mix_db.view.FrameIcon;

/**
 * drink creation separate window
 */
public class DrinkCreationView extends JFrame{

    private final CreateDrinkPanel mainPanel;
    
    /**
     * constructor
     */
    public DrinkCreationView() {

        this.mainPanel = new CreateDrinkPanel();

        this.add(this.mainPanel);

        this.setTitle("Creazione drink");
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width/2.6), (int) (Toolkit.getDefaultToolkit().getScreenSize().height/2.2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        new FrameIcon(this);
        this.setVisible(true);
    }

    public CreateDrinkPanel getMainPanel() {
        return this.mainPanel;
    } 
}
