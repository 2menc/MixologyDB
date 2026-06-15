package mix_db.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mix_db.core.FileExportService;
import mix_db.core.GeneralSettings;
import mix_db.core.Session;
import mix_db.data.dao.Bar;
import mix_db.data.dao.Drink;
import mix_db.data.dao.Review;
import mix_db.data.dao.User;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.view.ExceptionPanel;
import mix_db.view.MessageDialog;
import mix_db.view.bar.BarCreationPanel;
import mix_db.view.bar.CreateBarView;
import mix_db.view.drink.DrinkCreationView;
import mix_db.view.drink.DrinkInformationsPanel;
import mix_db.view.mainWindow.*;

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

        this.populatePanels();

    }

    /**
     * decides if the createBar button should be enabled
     * @return true if it should
     */
    private boolean enableCreateBarButton() {
        return Session.getInstance().getLoggedUser() != null &&
            ! this.model.isUserInABar(Session.getInstance().getLoggedUser().getUserID());
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

        final CentralPanel centralPanel = (CentralPanel) mv.getCentralPanel();
        final JPanel innerPanel = centralPanel;

        innerPanel.removeAll();
        for(var d: drinkList) {
            final JPanel drinkCard = this.createDrinkCard(d);
            innerPanel.add(drinkCard);
        }

        this.populateUsersWithMostPositiveReviewsLeaderboard();
        this.populateMostUsedIngredients();
        this.populateTrendingKeywords();

        innerPanel.revalidate();
        innerPanel.repaint();
    }

    /**
     * populates the grid with the drink searched from the databse
     * @param searchKey the string the user searched 
     */
    private void populateDrinkGridWithSearches(String searchKey) {
        List<Drink> drinkList = new ArrayList<>();

        if (!(this.view instanceof MainView mv)) {
            return; 
        }
 
        drinkList = this.model.searchByKeywords(searchKey);

        if(drinkList.isEmpty()) {
            new ExceptionPanel("nessun drink trovato", view);
            return;
        }

        for(var d: drinkList) {
            if(! Files.exists(Paths.get(GeneralSettings.fotoPath + d.getImagePath()))) {
                new ExceptionPanel("Problema nel caricamento dei drink: alcuni drink non hanno foto", this.view);
                break;
            }
        }

        final CentralPanel centralPanel = (CentralPanel) mv.getCentralPanel();
        final JPanel innerPanel = centralPanel;

        innerPanel.removeAll();
        for(var d: drinkList) {
            final JPanel drinkCard = this.createDrinkCard(d);
            innerPanel.add(drinkCard);
        }

        this.populateUsersWithMostPositiveReviewsLeaderboard();
        this.populateMostUsedIngredients();
        this.populateTrendingKeywords();

        innerPanel.revalidate();
        innerPanel.repaint();       
    }

    /**
     * populates central and right panel
     */
    private void populatePanels() {

        // *components population
        try {
            this.populateDrinkGrid();
        } catch (Exception e) {
            view.dispose();
            throw new ExceptionPanel(e, view);
        }

        if(this.view instanceof MainView mv) {

            mv.getRightPanel().setupCreateBarButton(this.enableCreateBarButton());

            mv.getRightPanel().requestedToCreateDrink(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final var v = new DrinkCreationView();
                    new DrinkController(v, model);
                }
            });
            mv.getRightPanel().requestedToLogOut(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mv.dispose();
                    Session.getInstance().setLoggedUser(null);
                    new LoginController();
                }     
            });
            mv.getRightPanel().requestedToSearchDrink(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    populateDrinkGridWithSearches(mv.getRightPanel().getSearchBarContent());
                }
            });
            mv.getRightPanel().requestedReload(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    populateDrinkGrid();
                }
            });
            mv.getRightPanel().requestedToShowFavs(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final JFrame frame = new JFrame();
                    frame.setTitle("preferiti");
                    
                    final JPanel panel = new JPanel();

                    final StringBuilder sb = new StringBuilder();
                    for(var d: model.getFavourites(Session.getInstance().getLoggedUser().getUserID())) {
                        sb.append("----\n").append(d.getName() + "\n").append(d.getDescription() + "\n");
                    }

                    final var ta = new JTextArea(sb.toString());
                    ta.setEditable(false);
                    panel.add(ta); 
                    frame.add(panel);
                    frame.pack();
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }      
            });
            mv.getRightPanel().requestedToCreateBar(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final CreateBarView barFrame = new CreateBarView();
                    
                    if(barFrame.getMainPanel() instanceof BarCreationPanel panel) {
                        panel.addSaveListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                final var emails = panel.getEmails();

                                for(var email: emails) {
                                    if(! model.userExists(email)) {
                                        new ExceptionPanel("hai inserito utenti che non esistono", barFrame);
                                        break;
                                    }
                                }

                                final Bar barToCreate = new Bar(
                                    -1, 
                                    panel.getBarName(),
                                    panel.getCity(), 
                                    panel.getAddress()
                                );

                                try {
                                    final var bar = model.createBar(barToCreate);
                                    for(var email: emails) {
                                        model.addUserToBar(bar.get().getBarID(), model.getUserFromEmail(email).get().getUserID());
                                    }
                                    model.addUserToBar(bar.get().getBarID(), Session.getInstance().getLoggedUser().getUserID());
                                    barFrame.dispose();
                                    mv.getRightPanel().setupCreateBarButton(enableCreateBarButton());
                                } catch (Exception ex) {
                                    throw new ExceptionPanel(ex, barFrame);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    /**
     * creates a drink card
     * @param d the drink
     * @return the card
     */
    public JPanel createDrinkCard(Drink d) {
        final JPanel card = new JPanel(new BorderLayout());
        
        // *Button

        final JButton infos = new JButton("info");
        card.add(infos, BorderLayout.SOUTH);
        this.drinkInfoPanelCreationHelper(infos, d);

        // *card
        final Dimension dim = new Dimension(this.view.getSize().width/6, this.view.getSize().height/3);
        card.setPreferredSize(dim);

        int targetWidth = dim.width;
        int targetHeight = dim.height; 

        final ImageIcon image = new ImageIcon(GeneralSettings.fotoPath + d.getImagePath());

        int originalWidth = image.getIconWidth();
        int originalHeight = image.getIconHeight();

        // *image size/stretch check
        if (originalWidth > 0 && originalHeight > 0 && targetWidth > 0 && targetHeight > 0) {
            double widthRatio = (double) targetWidth / originalWidth;
            double heightRatio = (double) targetHeight / originalHeight;
            double ratio = Math.min(widthRatio, heightRatio); // Mantiene le proporzioni originali [1]

            targetWidth = (int) (originalWidth * ratio);
            targetHeight = (int) (originalHeight * ratio);
        }

        final Image scaledImage = image.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        final ImageIcon scaledIcon = new ImageIcon(scaledImage);

        final JLabel imageLabel = new JLabel(scaledIcon);
        card.add(imageLabel, BorderLayout.CENTER);

        final JLabel nameLabel = new JLabel(d.getName() + " | " + d.getCategoryName());
        card.add(nameLabel, BorderLayout.NORTH);

        card.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        return card;
    }

    private void drinkInfoPanelCreationHelper(JButton button, Drink d) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(view instanceof MainView mv) {

                    final RightPanel rp = mv.getRightPanel();
                    rp.disableSearch();

                    rp.toggleAllButtons();

                    final JPanel mainPanel = mv.getMainPanel();
                    final BorderLayout layout = (BorderLayout) mainPanel.getLayout();
                    final Component oldCenter = layout.getLayoutComponent(BorderLayout.CENTER);
                    if (oldCenter != null) {
                        mainPanel.remove(oldCenter);
                    }

                    final DrinkInformationsPanel dp = new DrinkInformationsPanel(d, isDrinkSaved(d));
                    mainPanel.add(dp, BorderLayout.CENTER);

                    mainPanel.revalidate();
                    mainPanel.repaint();

                    dp.disableButtonsForGuests();
                    dp.populateIngredients(model.getComposition(d.getDrinkID()));
                    dp.populateKeywords(model.getKeywords(d.getDrinkID()));

                    final var reviewsMap = new HashMap<Review, User>();
                    for(var k: model.getDrinkReviews(d.getDrinkID())) {
                        final var u = model.getFullUserFromID(k.getUserID());
                        
                        if(u.isPresent()) {
                            reviewsMap.put(k, u.get());
                        }
                    }
                    dp.populateReviewsScrollPane(reviewsMap);
                    
                    // *listeners
                    dp.requestedToAddToFavs(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            model.saveAsFavourite(d.getDrinkID(), Session.getInstance().getLoggedUser().getUserID());
                            dp.setFavouriteButtonState(true);
                        }
                    });                    
                    dp.requestedToRemoveToFavs(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            model.removeFromFavourites(d.getDrinkID(), Session.getInstance().getLoggedUser().getUserID());
                            dp.setFavouriteButtonState(false);
                        }
                    });
                    dp.requestedToAddReview(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dp.setUpReviewFrame();
                        }
                    });
                    dp.reviewFinished(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                final var review = dp.getReviewInformation();

                                model.addReview(d.getDrinkID(), Session.getInstance().getLoggedUser().getUserID(), review.getDescription(), review.getScore());

                                final var reviewsMap = new HashMap<Review, User>();
                                for(var k: model.getDrinkReviews(d.getDrinkID())) {
                                    final var u = model.getFullUserFromID(k.getUserID());
                                    
                                    if(u.isPresent()) {
                                        reviewsMap.put(k, u.get());
                                    }
                                }
                                dp.populateReviewsScrollPane(reviewsMap);

                            } catch (Exception ex) {
                                new ExceptionPanel(ex, view);
                                ex.printStackTrace();
                            }
                        }
                    });
                    dp.requestedToGoBack(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mv.dispose();
                            view = new MainView();
                            view.setLocationRelativeTo(mv);
                            populatePanels();
                        }
                    });
                    dp.requestedToSavePdf(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final Optional<User> creatorOpt = model.getDrinkCreator(d.getDrinkID());
                            final List<String> keywords = model.getKeywords(d.getDrinkID());

                            final User creator;
                            creator = creatorOpt.isEmpty() ?  model.getAnonymUser() : creatorOpt.get(); 
                            managePdfGeneration(d, creator, keywords);
                        }
                    });
                }
            }            
        });
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
            final var ingredient = l.get(n);

            list.add((n + 1) + "- " + ingredient.getIngredientName() + " (" + ingredient.getNumUsed() + " volte)");
        }
        if(this.view instanceof MainView mv) {
            if(mv.getLeftPanel() instanceof LeftPanel lp) {
                lp.populateMostUsedIngredients(list);
            }
        }
    }

    private void populateTrendingKeywords() {
        final List<String> list = new LinkedList<>();
        final var l = model.getTrendingKeywords(30, 10);
        for(int n = 0; n < l.size(); n++) {
            final var tag = l.get(n);

            list.add((n + 1) + "- " + tag.getKeyword()); 
        }
        if(this.view instanceof MainView mv) {
            if(mv.getLeftPanel() instanceof LeftPanel lp) {
                lp.populateTrendingKeywords(list);
            }
        }
    }

    /**
     * checks if the drink is already saved
     * @param drinkID .
     * @return true if it is
     */
    private boolean isDrinkSaved(Drink d) {
        if(Session.getInstance().getLoggedUser() == null) {
            return false;
        }
        return model.getFavourites(Session.getInstance().getLoggedUser().getUserID()).contains(d);
    }

    /**
     * manages pdf file generation, getting output path
     */
    private void managePdfGeneration(Drink drink, User creator, java.util.List<String> keywords) {

        final File directory = new File("shares");
        if(!directory.exists()) {
            directory.mkdirs();
        }

        final String outputPath = directory.getPath() + File.separator + drink.getName() + ".pdf";
        try {
            FileExportService.createPdf(drink, creator, keywords, outputPath);
            new MessageDialog("Successo", "salvataggio effettuato", JOptionPane.INFORMATION_MESSAGE, this.view);
        } catch (Exception ex) {
            new ExceptionPanel("Errore durante la creazione del PDF: ", this.view);
            ex.printStackTrace();
        }
    }

}
