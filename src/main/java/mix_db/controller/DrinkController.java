package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import mix_db.model.Model;
import mix_db.model.IngredientData;
import mix_db.view.drinkCreationView.DrinkCreationView;

public class DrinkController {

    private final DrinkCreationView view;
    private final Model model;

    public DrinkController(DrinkCreationView view, Model model) {
        this.view = view;
        this.model = model;

        this.view.getMainPanel().addSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageDrinkCreation();
            }
        });
    }

    private void manageDrinkCreation() {
        try {
            List<IngredientData> ingredients = this.view.getMainPanel().getIngredientsData();
            

            JOptionPane.showMessageDialog(view, "Drink creato con successo", "SUCCESSO", JOptionPane.INFORMATION_MESSAGE);
            this.view.dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Errore di compilazione", JOptionPane.ERROR_MESSAGE);
        }
    }
}