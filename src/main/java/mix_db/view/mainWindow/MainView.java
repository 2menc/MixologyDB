package mix_db.view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mix_db.view.FrameIcon;

/**
 * main frame
 */
public class MainView extends JFrame {

    private JPanel mainPanel;

    private CentralPanel centralPanel;
    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    /**
     * creates the central frame and coordinates left, right, and central panels
     */
    public MainView() {
        this.mainPanel = new JPanel(new BorderLayout());

        this.centralPanel = new CentralPanel();
        this.leftPanel = new LeftPanel();
        this.rightPanel = new RightPanel();

        this.mainPanel.add(this.centralPanel.getContentPane(), BorderLayout.CENTER); 
        this.mainPanel.add(leftPanel, BorderLayout.WEST);
        this.mainPanel.add(rightPanel, BorderLayout.EAST);
        
        this.add(this.mainPanel);

        this.setResizable(false);
        this.setTitle("MixologyDB");
        super.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().width/1.3), (int)(Toolkit.getDefaultToolkit().getScreenSize().height/1.3));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        FrameIcon.setIcon(this);
        this.setVisible(true);
    }

    /**
     * gets the current main panel
     * @return the panel
     */
    public JPanel getCentralPanel() {
        return this.centralPanel;
    }

    /**
     * gets the left panel
     * @return the LeftPanel container component reference
     */
    public LeftPanel getLeftPanel() {
        return this.leftPanel;
    }

    /**
     * gets the right panel
     * @return the RightPanel container component reference
     */
    public RightPanel getRightPanel() {
        return this.rightPanel;
    }
    
    /**
     * gets the current dynamic main panel
     * @return the main JPanel container
     */
    public JPanel getMainPanel() {
        return mainPanel;
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

    /**
     * changes the central Panel
     * @param centralPanel the new centralPanel component
     */
    public void setCentralPanel(JPanel centralPanel) {
        
        final BorderLayout layout = (BorderLayout) this.getContentPane().getLayout();
        final Component oldCenter = layout.getLayoutComponent(BorderLayout.CENTER);
        
        if (oldCenter != null) {
            this.getContentPane().remove(oldCenter);
        }

        if (this.centralPanel != null) {
            this.remove(this.centralPanel);
        }
        
        this.centralPanel = (CentralPanel) centralPanel;
        
        this.add(this.centralPanel, BorderLayout.CENTER);
        
        this.revalidate();
        this.repaint();
    }
}
