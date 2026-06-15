package mix_db.view.bar;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreateBarView extends JFrame{

    private final JPanel mainPanel;

    /**
     * constructor
     */
    public CreateBarView() {
        this.mainPanel = new BarCreationPanel();

        this.add(this.mainPanel);

        this.setTitle("Creazione bar");
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width/2.6), (int) (Toolkit.getDefaultToolkit().getScreenSize().height/2.2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * gets the main bar creation panel
     * @return the panel
     */
    public JPanel getMainPanel() {
        return this.mainPanel;
    }
}
