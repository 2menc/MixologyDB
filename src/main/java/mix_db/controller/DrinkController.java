package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

import mix_db.model.Model;
import mix_db.model.IngredientData;
import mix_db.view.drinkCreationView.CreateDrinkPanel;

public class DrinkController {

    private final CreateDrinkPanel view;
    private final Model model;

    public DrinkController(CreateDrinkPanel view, Model model) {
        this.view = view;
        this.model = model;

        this.view.addSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageDrinkCreation();
            }
        });
    }

    private void manageDrinkCreation() {
        try {
            List<IngredientData> ingredients = this.view.getIngredientsData();
            

            JOptionPane.showMessageDialog(view, "Drink creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Errore di compilazione", JOptionPane.ERROR_MESSAGE);
        }
    }
}