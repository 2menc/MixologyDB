package mix_db.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mix_db.core.GeneralSettings;
import mix_db.core.Session;
import mix_db.data.dao.Drink;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.view.ExceptionPanel;
import mix_db.view.drinkCreationView.DrinkCreationView;
import mix_db.view.mainWindow.CentralPanel;
import mix_db.view.mainWindow.LeftPanel;
import mix_db.view.mainWindow.MainView;

/**
 * main application controller
 */
public class ApplicationController {

    private JFrame view;

    private DbModel model;

    /**
     * constructor
     */
    public ApplicationController() {
        try {
            final Connection connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password");
            this.model = new DbModel(connection);
        } catch (Exception e) {
            e.printStackTrace();
            new ExceptionPanel("Problema connessione con database SQL", view);
        }

        this.view = new MainView();

        // *components population
        try {
            this.populateDrinkGrid();
        } catch (Exception e) {
            view.dispose();
            throw new ExceptionPanel(e, view);
        }

        this.populateUsersWithMostPositiveReviewsLeaderboard();
        this.populateMostUsedIngredients();
        this.populateTrendingKeywords();

        if(this.view instanceof MainView mv) {
            mv.getRightPanel().requestedToCreateDrink(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    final var v = new DrinkCreationView();
                    new DrinkController(v, model);
                }
            });
        }
    }

    /**
     * fills the central panel grid.
     * ! if the user is logged in, shows suggestions
     * ! if the user is a guest OR the logged in user doesn't have any favourites, 
     * ! shows a generic list of drinks
     */
    public void populateDrinkGrid() {
        List<Drink> drinkList = new ArrayList<>();

        if (!(this.view instanceof MainView mv)) {
            return; 
        }
 
        final var loggedUser = Session.getInstance().getLoggedUser();

        if(loggedUser != null) {
            drinkList = this.model.getFavourites(loggedUser.getUserID());
        }

        if(loggedUser == null || drinkList.size() < 6) {
            drinkList = this.model.getRandomDrinkList(100);
        }

        for(var d: drinkList) {
            if(! Files.exists(Paths.get(GeneralSettings.fotoPath + d.getImagePath()))) {
                new ExceptionPanel("Problema nel caricamento dei drink: alcuni drink non hanno foto", this.view);
                break;
            }
        }

        final CentralPanel centralPanel = mv.getMainPanel();
        final JPanel innerPanel = centralPanel.getContentPanel();

        innerPanel.removeAll();
        for(var d: drinkList) {
            final JPanel drinkCard = this.createDrinkCard(d);
            innerPanel.add(drinkCard);
        }

        innerPanel.revalidate();
        innerPanel.repaint();
    }

    /**
     * creates a drink card
     * @param d the drink
     * @return the card
     */
    public JPanel createDrinkCard(Drink d) {
        final JPanel card = new JPanel(new BorderLayout());
        final Dimension dim = new Dimension(this.view.getSize().width/6, this.view.getSize().height/3);
        card.setPreferredSize(dim);

        final int targetWidth = dim.width;
        final int targetHeight = dim.height; 

        final ImageIcon image = new ImageIcon(GeneralSettings.fotoPath + d.getImagePath());

        final Image scaledImage = image.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        final ImageIcon scaledIcon = new ImageIcon(scaledImage);

        final JLabel imageLabel = new JLabel(scaledIcon);
        card.add(imageLabel, BorderLayout.CENTER);

        final JLabel nameLabel = new JLabel(d.getName() + " | " + d.getCategoryName());
        card.add(nameLabel, BorderLayout.SOUTH);

        card.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        return card;
    }

    private void populateUsersWithMostPositiveReviewsLeaderboard() {
        final List<String> list = new LinkedList<>();
        final var l = model.calculateUsersWithMostPositiveReviewsLeaderboard(10);
        for(int n = 0; n < l.size(); n++) {
            list.add(Integer.toString(n+1) + "-" + l.get(n).getName() + " " + l.get(n).getSurname());
        }
        if(this.view instanceof MainView mv) {
            if(mv.getLeftPanel() instanceof LeftPanel lp) {
                lp.populateUserWithMostPositiveReviews(list);;
            }
        }
    }

    private void populateMostUsedIngredients() {
        final List<String> list = new LinkedList<>();
        final var l = model.getMostUsedIngredients(10);
        for(int n = 0; n < l.size(); n++) {
            list.add(Integer.toString(n+1) + "-" + l.get(n));
        }
        if(this.view instanceof MainView mv) {
            if(mv.getLeftPanel() instanceof LeftPanel lp) {
                lp.populateMostUsedIngredients(list);;
            }
        }
    }

    private void populateTrendingKeywords() {
        final List<String> list = new LinkedList<>();
        final var l = model.getTrendingKeywords(30, 10);
        for(int n = 0; n < l.size(); n++) {
            list.add(Integer.toString(n+1) + "-" + l.get(n));
        }
        if(this.view instanceof MainView mv) {
            if(mv.getLeftPanel() instanceof LeftPanel lp) {
                lp.populateTrendingKeywords(list);;
            }
        }
    }
}
