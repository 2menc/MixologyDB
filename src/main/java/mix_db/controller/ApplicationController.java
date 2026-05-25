package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import mix_db.core.FileExportService;
import mix_db.core.Session;
import mix_db.data.dao.Drink;
import mix_db.data.dbConnection.DAOException;
import mix_db.data.dbConnection.DatabaseConnection;
import mix_db.model.DbModel;
import mix_db.model.Model;
import mix_db.view.LoginView;
import mix_db.view.SignInView;

public class ApplicationController {

    private final LoginView loginView;
    private SignInView signInView;

    private Model model;

    public ApplicationController(LoginView loginView, SignInView signInView) {
        this.loginView = loginView;
        this.signInView = signInView;

        try(final Connection connection = DatabaseConnection.localConnection("MixologyDB", "root", "Password")) {
            
            this.model = new DbModel(connection);

        } catch (Exception e) {
            this.exceptionThrower(e.getMessage());
        }

        loginView.verifyLogin(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manageLoginAttempt();
            }
        });

        loginView.requestedSignIn(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manageSignInAttempt();
            }
            
        });
    }

    /**
     * manages login attempt
     */
    private void manageLoginAttempt() {
        final String email = this.loginView.getEmail();
        final String password = this.loginView.getPassword();

        if(Session.getInstance().login(email, password)) {
            loginView.dispose();
        } else {
            // *login failed
            loginView.showError("Email o password errati");
        }
    }

    /**
     * manages sign in attempt
     */
    private void manageSignInAttempt() {

        final String email = this.loginView.getEmail();
        final String password = this.loginView.getPassword();

        if(Session.getInstance().login(email, password)) {
            this.exceptionThrower("utente già registrato");
        } else {
            this.loginView.dispose();
            this.signInView.setVisible(true);
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
     */
    private void exceptionThrower(String message) {
        this.loginView.showError(message);
    }
}
