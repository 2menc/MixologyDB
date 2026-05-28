package mix_db.view.drinkCreationView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;

import mix_db.model.IngredientData;
import mix_db.view.ExceptionPanel;

/**
 * main drink creation panel
 */
public class CreateDrinkPanel extends JPanel {

    private final JPanel ingredientsContainer;
    private java.util.List<IngredientRow> rowsList = new ArrayList<>();
    private final JButton saveButton;

    // *normal text fields
    private final JTextField name;
    private final JTextField categoryName;
    private final JTextArea description;
    private final JTextArea keywords;

    private java.io.File drinkImage;

    /**
     * constructor
     */
    public CreateDrinkPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.rowsList = new ArrayList<>();

        // *top panel 

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        
        this.name = new JTextField("nome del drink");
        this.categoryName = new JTextField("categoria");
        
        row1.add(this.name);
        row1.add(this.categoryName);
        
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, row1.getPreferredSize().height));

        final JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0)); 
        
        // right component
        this.description = new JTextArea("descrizione", 4, 20);
        final JScrollPane descScroll = new JScrollPane(this.description);

        // left componenr
        this.keywords = new JTextArea("parole chiave separate da virgola", 4, 20);
        final JScrollPane otherScroll = new JScrollPane(keywords);

        row2.add(descScroll);
        row2.add(otherScroll);
        
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        topPanel.add(row1);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        topPanel.add(row2); 

        // *photo chooser
        final JButton chooseFile = new JButton("carica la foto del drink");
        chooseFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final var photoChooser = new JFileChooser();

                int result = photoChooser.showOpenDialog(chooseFile);

                if (result == JFileChooser.APPROVE_OPTION) {
                    final java.io.File file = photoChooser.getSelectedFile();

                    if(! (file.getName().endsWith(".jpg") ||
                        file.getName().endsWith(".jpeg") ||
                        file.getName().endsWith(".png"))
                    ) {
                        new ExceptionPanel("formato immagine non valido", new JFrame());
                    } else {
                        drinkImage = file;
                        chooseFile.setText(drinkImage.getName());
                    }
                    
                }
            }
        });
        topPanel.add(chooseFile);

        this.add(topPanel, BorderLayout.NORTH);


        // *ingredients panel
        this.ingredientsContainer = new JPanel();
        this.ingredientsContainer.setLayout(new BoxLayout(ingredientsContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(ingredientsContainer);
        this.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("+ Aggiungi Ingrediente");
        addButton.addActionListener(e -> addIngredientRow());
        
        this.saveButton = new JButton("Crea Drink");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(addButton);
        bottomPanel.add(saveButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        addIngredientRow();
    }

    /**
     * adds the current ingredient to the rowsList list
     */
    private void addIngredientRow() {
        IngredientRow newRow = new IngredientRow(this);
        this.rowsList.add(newRow);
        this.ingredientsContainer.add(newRow);
        
        this.ingredientsContainer.revalidate();
        this.ingredientsContainer.repaint();
    }
    
    public void addSaveListener(ActionListener al) {
        this.saveButton.addActionListener(al);
    }

    /**
     * Estrae i dati grafici e li converte in una lista di oggetti modello puri.
     * Lancia un'eccezione se i dati inseriti dall'utente non sono validi.
     */
    public java.util.List<IngredientData> getIngredientsData() throws IllegalArgumentException {
        java.util.List<IngredientData> dataList = new ArrayList<>();
        
        for (IngredientRow row : rowsList) {
            String name = row.getIngredientName();
            String qtyStr = row.getQuantity();
            String unit = row.getUnitOfMeasure();

            if (name.isEmpty() || qtyStr.isEmpty() || unit.isEmpty()) {
                throw new IllegalArgumentException("Tutti i campi degli ingredienti devono essere compilati");
            }

            try {
                float quantity = Float.parseFloat(qtyStr);
                dataList.add(new IngredientData(name, quantity, unit));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("La quantità di '" + name + "' deve essere un numero valido");
            }
        }
        return dataList;
    }

    /**
     * removes ingredient
     * @param row the ingredient to remove
     */
    public void removeIngredientRow(IngredientRow row) {
        if (rowsList.size() > 1) {
            this.rowsList.remove(row);
            this.ingredientsContainer.remove(row);
            
            this.ingredientsContainer.revalidate();
            this.ingredientsContainer.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Il drink deve avere almeno un ingrediente!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * ingredient-qty-measureUnits triplets
     */
    public static class IngredientRow extends JPanel {
        private final JTextField ingredientField;
        private final JTextField quantityField;
        private final JTextField unitField;
        private final JButton deleteButton;

        /**
         * constructor
         * @param parent the panel to add to
         */
        public IngredientRow(CreateDrinkPanel parent) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

            this.ingredientField = new JTextField(15);
            this.quantityField = new JTextField(5);
            this.unitField = new JTextField(8);
            this.deleteButton = new JButton("X");
            this.deleteButton.setForeground(Color.RED);

            this.deleteButton.addActionListener(e -> parent.removeIngredientRow(this));

            this.add(new JLabel("Ingrediente:"));
            this.add(this.ingredientField);
            this.add(new JLabel("Q.tà:"));
            this.add(this.quantityField);
            this.add(new JLabel("Unità:"));
            this.add(this.unitField);
            this.add(this.deleteButton);
            
            this.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.getPreferredSize().height));
        }

        /**
         * getters
         */
        public String getIngredientName() { return ingredientField.getText().trim(); }
        public String getQuantity() { return quantityField.getText().trim(); }
        public String getUnitOfMeasure() { return unitField.getText().trim(); }
    }

    public JPanel getIngredientsContainer() {
        return ingredientsContainer;
    }

    public java.util.List<IngredientRow> getRowsList() {
        return rowsList;
    }

    public String getDrinkName() {
        return this.name.getText();
    }

    public String getCategoryName() {
        return this.categoryName.getText();
    }

    public String getDescription() {
        return this.description.getText();
    }

    public java.util.List<String> getKeywords() {
        final java.util.List<String> l = new LinkedList<>();

        for(var k: this.keywords.getText().split(",")) {
            l.add(k);
        }
        return l;
    }

    public java.io.File getDrinkImage() {
        return drinkImage;
    }

}
