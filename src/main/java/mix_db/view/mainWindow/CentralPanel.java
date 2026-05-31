package mix_db.view.mainWindow;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * central panel of {@link MainView}
 */
public class CentralPanel extends JPanel{

    private final static int COLUMNS = 4;
    private final static int ROWS = 30;

    private final JScrollPane mainPane = new  JScrollPane();

    /**
     * constructor
     */
    public CentralPanel() {
        this.setLayout(new GridLayout(0, COLUMNS));

        this.mainPane.setViewportView(this);

        this.mainPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * gets che content grid panel
     * @return the panel
     */
    public JScrollPane getContentPane() {
        return this.mainPane;
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
