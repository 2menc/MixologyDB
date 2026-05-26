package mix_db;

import com.formdev.flatlaf.*;

import mix_db.controller.ApplicationController;

/**
 * main class
 */
public class Launcher {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        new ApplicationController();
    }
}
