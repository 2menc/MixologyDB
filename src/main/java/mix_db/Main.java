package mix_db;

import com.formdev.flatlaf.*;

import mix_db.controller.ApplicationController;
import mix_db.view.LoginView;

/**
 * main class
 */
public class Main {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        final LoginView loginView = new LoginView();

        new ApplicationController(loginView);
    }
}
