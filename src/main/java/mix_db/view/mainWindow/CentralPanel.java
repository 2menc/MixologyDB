package mix_db.view.mainWindow;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * central panel of {@link MainView}
 */
public class CentralPanel extends JScrollPane{

    private final static int COLUMNS = 4;
    private final static int ROWS = 30;

    private final JPanel contentPanel;

    /**
     * constructor
     */
    public CentralPanel() {
        this.contentPanel = new JPanel(new GridLayout(0, COLUMNS));

        this.setViewportView(this.contentPanel);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * gets che content grid panel
     * @return the panel
     */
    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    /**
     * gets the grid columns
     * @return .
     */
    public static int getColumns() {
        return COLUMNS;
    }

    /**
     * gets the grid rows
     * @return .
     */
    public static int getRows() {
        return ROWS;
    }
    
}
