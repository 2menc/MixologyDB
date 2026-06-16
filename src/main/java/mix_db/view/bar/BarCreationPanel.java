package mix_db.view.bar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * panel used for creating a new bar
 */
public class BarCreationPanel extends JPanel{

    private final JTextField barName;
    private final JTextField city;
    private final JTextField address;
    private final JButton saveButton;

    private final JPanel usersContainer;
    private java.util.List<UserRow> usersList = new ArrayList<>();


    /**
     * creates the bar creation panel elements
     */
    public BarCreationPanel() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        this.barName = new JTextField("nome bar");
        this.city = new JTextField("città");
        row1.add(this.barName);
        row1.add(this.city);

        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, row1.getPreferredSize().height));

        this.address = new JTextField("indirizzo");
        this.address.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.address.getPreferredSize().height));

        topPanel.add(row1);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(this.address);

        this.add(topPanel, BorderLayout.NORTH);

        this.usersContainer = new JPanel();
        this.usersContainer.setLayout(new BoxLayout(usersContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(usersContainer);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("+ Aggiungi dipendente");
        addButton.addActionListener(e -> addUserRow());
        
        this.saveButton = new JButton("Crea bar");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        addUserRow();
    }
 
    /**
     * gets employees registered email list
     * @return list of employee emails
     */
    public List<String> getEmails() {
        final List<String> s = new LinkedList<>();
        for(var ur: this.usersList) {
            s.add(ur.getEmail());
        }
        return s;
    }

    /**
     * adds the current ingredient to the usersList list
     */
    private void addUserRow() {
        UserRow newRow = new UserRow(this);
        this.usersList.add(newRow);
        this.usersContainer.add(newRow);
        
        this.usersContainer.revalidate();
        this.usersContainer.repaint();
    }
    
    /**
     * inserts a save listener on the action element
     * @param al the ActionEvent listener
     */
    public void addSaveListener(ActionListener al) {
        this.saveButton.addActionListener(al);
    }

    /**
     * removes ingredient
     * @param row the ingredient to remove
     */
    public void removeUserRow(UserRow row) {
        if (usersList.size() > 1) {
            this.usersList.remove(row);
            this.usersContainer.remove(row);
            
            this.usersContainer.revalidate();
            this.usersContainer.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "il bar deve avere almeno un dipendente", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * ingredient-qty-measureUnits triplets
     */
    public static class UserRow extends JPanel {
        private final JTextField emailField;

        private final JButton deleteButton;

        /**
         * constructor
         * @param parent the panel to add to
         */
        public UserRow(BarCreationPanel parent) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            this.emailField = new JTextField(15);

            this.deleteButton = new JButton("X");
            this.deleteButton.setForeground(Color.RED);

            this.deleteButton.addActionListener(e -> parent.removeUserRow(this));

            this.add(new JLabel("email:"));
            this.add(this.emailField);

            this.add(this.deleteButton);
            
            this.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
        }

        /**
         * getters
         */
        /**
         * gets employee email from text field element
         * @return the entered email string
         */
        public String getEmail() { return emailField.getText().trim(); }
    }

    /**
     * gets the entered bar name value
     * @return the bar name string
     */
    public String getBarName() {
        return barName.getText();
    }

    /**
     * gets the entered bar city value
     * @return the city string
     */
    public String getCity() {
        return city.getText();
    }

    /**
     * gets the entered bar address value
     * @return the address string
     */
    public String getAddress() {
        return address.getText();
    }
}
