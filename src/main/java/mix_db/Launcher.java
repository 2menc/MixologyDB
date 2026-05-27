package mix_db;

import com.formdev.flatlaf.*;

import mix_db.controller.LoginController;

/**
 * main class
 */
public class Launcher {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        new LoginController();
    }
}
