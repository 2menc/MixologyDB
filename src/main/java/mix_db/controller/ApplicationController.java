package mix_db.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mix_db.core.Session;
import mix_db.data.dao.Drink;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.view.ExceptionPanel;
import mix_db.view.mainWindow.CentralPanel;
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

        final CentralPanel centralPanel = new CentralPanel();
        final JPanel leftPanel = new JPanel();   
        final JPanel rightPanel = new JPanel();  

        this.view = new MainView(centralPanel, leftPanel, rightPanel);

        try {
            this.populateDrinkGrid();
        } catch (Exception e) {
            view.dispose();
            throw new ExceptionPanel(e, view);
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

        if(Session.getInstance().getLoggedUser() != null) {
            
            if(this.view instanceof MainView mv) {
                drinkList = this.model.getSuggestions(Session.getInstance().getLoggedUser().getUserID(), 100);

                final CentralPanel centralPanel = mv.getMainPanel();
                final JPanel innerPanel = centralPanel.getContentPanel();

                innerPanel.removeAll();

                for(var d: drinkList) {
                    final JPanel drinkCard = this.createDrinkCard(d);

                    innerPanel.add(drinkCard);
                }
            }

        } 
        if (Session.getInstance().getLoggedUser() == null 
                    || drinkList.size() < 9
            ) {
            
            if(this.view instanceof MainView mv) {
                drinkList = this.model.getRandomDrinkList(100);

                final CentralPanel centralPanel = mv.getMainPanel();
                final JPanel innerPanel = centralPanel.getContentPanel();

                innerPanel.removeAll();

                for(var d: drinkList) {
                    final JPanel drinkCard = this.createDrinkCard(d);

                    innerPanel.add(drinkCard);
                }
            }


        }

        if(drinkList.isEmpty()) {
            throw new IllegalStateException("Problema nella ricerca dei drink");
        }
    }

    private JPanel createDrinkCard(Drink d) {
        final JPanel card = new JPanel(new BorderLayout());
        final Dimension dim = new Dimension(this.view.getSize().width/6, this.view.getSize().height/3);
        card.setPreferredSize(dim);

        //TODO: foto
        final JLabel deleteThis = new JLabel("DELETE THIS");
        card.add(deleteThis, BorderLayout.CENTER);

        final JLabel nameLabel = new JLabel(d.getName() + " | " + d.getCategoryName());
        card.add(nameLabel, BorderLayout.SOUTH);

        card.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        return card;
    }

}
