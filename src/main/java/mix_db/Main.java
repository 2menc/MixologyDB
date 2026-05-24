package mix_db;

import mix_db.controller.ApplicationController;
import mix_db.core.FileExportService;
import mix_db.view.LoginView;

/**
 * main class
 */
public class Main {

    public static void main(String[] args) {

        final LoginView loginView = new LoginView();

        new ApplicationController(new FileExportService(), loginView);
    }
}
