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

public class ApplicationController {

    private final LoginView loginView;

    private Model model;

    public ApplicationController(LoginView loginView) {
        this.loginView = loginView;

        try(final Connection connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password")) {
            
            this.model = new DbModel(connection);

        } catch (Exception e) {
            this.exceptionThrower(e.getMessage(), loginView);
        }

        if(this.loginView.getMainPanel() instanceof LoginPanel loginPanel) {

            loginPanel.verifyLogin(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    manageLoginAttempt();
                }
            });

            loginPanel.requestedSignIn(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    manageSignInAttempt();
                }
                
            });
        }
    }

    /**
     * manages login attempt
     * @throws ExceptionPanel 
     */
    private void manageLoginAttempt() {
        if(this.loginView.getMainPanel() instanceof LoginPanel loginPanel) {
            final String email = loginPanel.getEmail();
            final String password = loginPanel.getPassword();

            if(Session.getInstance().login(email, password)) {
                loginView.dispose();
            } else {
                // *login failed
                this.exceptionThrower("Email o password errati", this.loginView);
            }
        }
    }

    /**
     * manages sign in attempt
     * @throws ExceptionPanel if an error occours
     */
    private void manageSignInAttempt() {

        if(this.loginView.getMainPanel() instanceof LoginPanel loginPanel) {
            final String email = loginPanel.getEmail();
            final String password = loginPanel.getPassword();

            if(Session.getInstance().login(email, password)) {
                this.exceptionThrower("utente già registrato", this.loginView);
            } else {
                this.loginView.setMainPanel(new SignInPanel());
            }
        } else {
            this.exceptionThrower("Errore nella registrazione", this.loginView);
        }
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
    private void exceptionThrower(String message, JFrame frame) {
        new ExceptionPanel(message, frame);
    }
}
