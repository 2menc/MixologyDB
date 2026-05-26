package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;

import mix_db.core.FileExportService;
import mix_db.core.Session;
import mix_db.data.dao.Drink;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.model.Model;
import mix_db.view.ExceptionPanel;
import mix_db.view.login.LoginPanel;
import mix_db.view.login.LoginView;
import mix_db.view.login.SignInPanel;

/**
 * application controller
 */
public class ApplicationController {

    private final JFrame view;

    private Model model;

    /**
     * constructor
     * @param loginView the login view frame
     */
    public ApplicationController() {
        this.view = new LoginView();

        try(final Connection connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password")) {
            
            this.model = new DbModel(connection);

        } catch (Exception e) {
            this.exceptionThrower(e.getMessage());
        }

        if(this.view instanceof LoginView loginView){
            if(loginView.getMainPanel() instanceof LoginPanel loginPanel) {

                loginPanel.verifyLogin(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manageLoginAttempt();
                    }
                });

                loginPanel.requestedSignIn(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginView.setMainPanel(new SignInPanel());                        
                    }
                });
            }
        }
    }

    /**
     * manages login attempt
     * @throws ExceptionPanel 
     */
    private void manageLoginAttempt() {
        if(this.view instanceof LoginView loginView){
            if(loginView.getMainPanel() instanceof LoginPanel loginPanel) {
            final String email = loginPanel.getEmail();
            final String password = loginPanel.getPassword();

            if(Session.getInstance().login(email, password)) {
                loginView.dispose();
            } else {
                // *login failed
                this.exceptionThrower("Email o password errati");
            }
            }
        }
    }

    /**
     * manages sign in attempt
     * @throws ExceptionPanel if an error occours
     */
    private void manageSignInAttempt() {
    }

    /**
     * manages pdf file generation, getting output path
     */
    private void managePdfGeneration(Drink drink, String creator, java.util.List<String> keywords) {
        final String outputPath = "";
        FileExportService.createPdf(drink, creator, keywords, outputPath);
    }

    /**
     * shows the error on the ui
     * @param e .
     * @throws ExceptionPanel .
     */
    private void exceptionThrower(String message) {
        new ExceptionPanel(message, view);
    }
}
