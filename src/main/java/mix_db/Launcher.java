package mix_db;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import mix_db.controller.LoginController;

/**
 * main class
 */
public class Launcher {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1.1");

        FlatMacDarkLaf.setup();

        new LoginController();
    }
}
