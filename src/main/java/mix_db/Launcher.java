package mix_db;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import mix_db.controller.LoginController;

/**
 * main class
 */
public class Launcher {

    /**
     * launches the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1.0");

        FlatMacDarkLaf.setup();

        new LoginController();
    }
}