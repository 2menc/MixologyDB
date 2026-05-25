package mix_db;

import com.formdev.flatlaf.*;

import mix_db.controller.ApplicationController;
import mix_db.view.LoginView;
import mix_db.view.SignInView;

/**
 * main class
 */
public class Main {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        final LoginView loginView = new LoginView();
        final SignInView signInView = new SignInView();

        new ApplicationController(loginView, signInView);
    }
}
