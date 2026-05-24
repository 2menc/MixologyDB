package mix_db.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mix_db.core.FileExportService;
import mix_db.core.Session;
import mix_db.data.dao.Drink;
import mix_db.view.LoginView;

public class ApplicationController {

    private final LoginView loginView;

    public ApplicationController(LoginView loginView) {
        this.loginView = loginView;

        loginView.verifyLogin(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                manageLoginAttempt();
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
     * manages pdf file generation, getting output path
     */
    private void managePdfGeneration(Drink drink, String creator, java.util.List<String> keywords) {
        final String outputPath = "";
        FileExportService.createPdf(drink, creator, keywords, outputPath);
    }

}
