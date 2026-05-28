package mix_db.view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * main frame
 */
public class MainView extends JFrame {

    private JPanel mainPanel;

    private CentralPanel centralPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    /**
     * constructor
     * @param centralPanel main scroll panel
     * @param leftPanel panel with informations and leaderboards
     * @param rightPanel panel with user informations, search bar and create drink
     */
    public MainView(CentralPanel centralPanel, JPanel leftPanel, JPanel rightPanel) {
        this.mainPanel = new JPanel(new BorderLayout());

        this.centralPanel = centralPanel;
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;

        this.mainPanel.add(centralPanel, BorderLayout.CENTER);
        this.mainPanel.add(leftPanel, BorderLayout.WEST);
        this.mainPanel.add(rightPanel, BorderLayout.EAST);
        
        this.add(this.mainPanel);

        this.setResizable(false);
        this.setTitle("MixologyDB");
        super.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.3));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * gets the current main panel
     * @return the panel
     */
    public CentralPanel getMainPanel() {
        return this.centralPanel;
    }

    /**
     * gets the left panel
     * @return
     */
    public JPanel getLeftPanel() {
        return this.leftPanel;
    }

    /**
     * changes the main mainPanel
     * @param mainPanel the new mainPanel
     */
    public void setMainPanel(JPanel mainPanel) {

        if (this.mainPanel != null) {
            this.remove(this.mainPanel);
        }
        
        this.mainPanel = mainPanel;
        
        this.add(this.mainPanel);
        
        this.revalidate();
        this.repaint();
    }
}
