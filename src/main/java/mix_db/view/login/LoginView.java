package mix_db.view.login;

import java.awt.Toolkit;

import javax.swing.*;

import mix_db.view.FrameIcon;

/**
 * simple login frame
 */
public class LoginView extends JFrame{

    private JPanel panel;

    /**
     * constructor
     */
    public LoginView() {
        super.setTitle("MixologyDB_login");
        super.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/7, Toolkit.getDefaultToolkit().getScreenSize().height/4);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel = new LoginPanel();
        this.add(this.panel);
        var f = new FrameIcon(this);
        super.setVisible(true);
        System.out.println(f.getHeight(rootPane) + ", " + f.getWidth(rootPane));

    }
    
    /**
     * gets the current main panel
     * @return the panel
     */
    public JPanel getMainPanel() {
        return this.panel;
    }

    /**
     * changes the main panel
     * @param panel the new panel
     */
    public void setMainPanel(JPanel panel) {

        if (this.panel != null) {
            this.remove(this.panel);
        }
        
        this.panel = panel;
        
        this.add(this.panel);
        
        this.revalidate();
        this.repaint();
    }
}
