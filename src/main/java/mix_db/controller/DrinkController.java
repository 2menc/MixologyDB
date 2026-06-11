package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import mix_db.model.Model;
import mix_db.core.GeneralSettings;
import mix_db.core.Session;
import mix_db.data.dao.Composition;
import mix_db.data.dao.Drink;
import mix_db.model.IngredientData;
import mix_db.view.ExceptionPanel;
import mix_db.view.drinkCreationView.DrinkCreationView;

public class DrinkController {

    private final DrinkCreationView view;
    private final Model model;

    public DrinkController(DrinkCreationView view, Model model) {
        this.view = view;
        this.model = model;

        this.view.getMainPanel().populateComboBox(this.model.getAllCategories());

        this.view.getMainPanel().setCheckBoxStatus(this.model.isUserInABar(Session.getInstance().getLoggedUser().getUserID()));

        this.view.getMainPanel().addSaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageDrinkCreation();
            }
        });
    }

    private void manageDrinkCreation() {
        try {
            final var panel = this.view.getMainPanel();

            final List<IngredientData> ingredients = this.view.getMainPanel().getIngredientsData();
            final String name = panel.getDrinkName();
            final String description = panel.getDescription();
            final String categoryName = panel.getCategoryName();
            
            final List<String> keywords = panel.getKeywords();
            if(keywords.isEmpty()) {
                throw new IllegalStateException("keywords list cannot be empty");
            }

            final File photo = panel.getDrinkImage();

            // *actual drink creation
            final Drink d = new Drink(
                -1, 
                name, 
                description, 
                this.createSecureFileName(photo, name), 
                categoryName, 
          false
            );

            if(! this.saveImage(photo, name)) {
                throw new IllegalArgumentException("file not valid");
            }

            final List<Composition> comp = new LinkedList<>();
            for(var c: ingredients) {
                final var i = new Composition(
                    c.name(), 
                    d.getDrinkID(), 
                    c.quantity(),
                    c.unit()
                );
                comp.add(i);
            }

            final Optional<Drink> newDrink;
            
            // *checks if it has to be a bar drink or a private user drink
            if(panel.getCheckBoxStatus()) {
                //bar
                newDrink = this.model.createDrink(
                    d, 
                    Session.getInstance().getLoggedUser().getUserID(), 
                    Optional.of(this.model.checkIfEmployed(Session.getInstance().getLoggedUser().getUserID()).get().getBarID()), 
                    comp, 
                    keywords);
            } else {
                //single user
                newDrink = this.model.createDrink(
                    d, 
                    Session.getInstance().getLoggedUser().getUserID(), 
                    Optional.empty(), 
                    comp, 
                    keywords);
            }

            if(newDrink.isEmpty()) {
                throw new IllegalStateException("errore nella creazione del drink");
            } else if (newDrink.get().equals(this.model.getDrink(newDrink.get().getDrinkID()).get())) {
                JOptionPane.showMessageDialog(view, "Drink creato con successo", "SUCCESSO", JOptionPane.INFORMATION_MESSAGE);
                this.view.dispose();
            }

        } catch (Exception ex) {
            throw new ExceptionPanel(ex.getMessage(), this.view);
        }
    }

    /**
     * saves the drink image
     * @param uploadedFile .
     * @param drinkName .
     * @return true if can save the image
     * @throws IOException if an error occours
     */
    private boolean saveImage(File uploadedFile, String drinkName) throws IOException {

        if (uploadedFile == null || !uploadedFile.exists() || drinkName == null || drinkName.isBlank()) {
            return false;
        }

        try {
            Path destFolder = Paths.get(GeneralSettings.fotoPath);
            if (!Files.exists(destFolder)) {
                Files.createDirectories(destFolder);
            }

            Path targetPath = destFolder.resolve(createSecureFileName(uploadedFile, drinkName));

            Files.copy(uploadedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return true; 

        } catch (IOException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this.view, "problema durante il salvataggio dell'immagine", "ERRORE", JOptionPane.WARNING_MESSAGE);
            return false; 
        }
    }

    /**
     * generates a secure file name
     * @param uploadedFile .
     * @param drinkName .
     * @return the secure name
     */
    private String createSecureFileName(File uploadedFile, String drinkName) {
        String safeDrinkName = drinkName.trim().replaceAll("[^a-zA-Z0-9.-]", "_");

        return safeDrinkName;
    }    
}